package rs.ac.bg.etf.pp1;

import java.util.*;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

	public class CodeGenerator extends VisitorAdaptor {
	
		private int mainPc;
		private List<Obj> aaList = new ArrayList<>(); 
		
		public int getMainPc() {
			return mainPc;
		}
	
		public void setMainPc(int mainPc) {
			this.mainPc = mainPc;
		}
		
	// PRINT
		
	public void visit(StatementPrintWithoutWidth statementPrintWithoutWidth) {
		
		if(statementPrintWithoutWidth.getExpr().struct == Tab.intType
				|| statementPrintWithoutWidth.getExpr().struct == TabExtension.boolType) {
			Code.loadConst(5);
			Code.put(Code.print);
		}else {
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
		
	}
	
	public void visit(StatementPrintWithWidth statementPrintWithWidth) {
		
		Code.loadConst(statementPrintWithWidth.getWidth());
		
		if(statementPrintWithWidth.getExpr().struct == Tab.intType
				|| statementPrintWithWidth.getExpr().struct == TabExtension.boolType) {
			Code.put(Code.print);
		}else {
			Code.put(Code.bprint);
		}
	}
	
	
	//READ
	
	public void visit(StatementRead statementRead) {
		
		if(statementRead.getDesignator().obj.getType() == Tab.charType) {
			Code.put(Code.bread);			
		} else {
			Code.put(Code.read);
		}
		
		Code.store(statementRead.getDesignator().obj);
	}
	
	
	// CONSTANTS
	
	public void visit(NumberConstFactor numberConstFactor) {
		
		Obj cons = Tab.insert(Obj.Con, "$const", Tab.intType);
		cons.setLevel(0);
		cons.setAdr(numberConstFactor.getNumValue());
		Code.load(cons);
		
	}
	
	public void visit(CharConstFactor charConstFactor) {
		
		Obj cons = Tab.insert(Obj.Con, "$const", Tab.charType);
		cons.setLevel(0);
		cons.setAdr(charConstFactor.getCharValue());
		
		Code.load(cons);
		
	}

	public void visit(BooleanConstFactor booleanConstFactor) {
	
		Obj cons = Tab.insert(Obj.Con, "$const", TabExtension.boolType);
		cons.setLevel(0);
		if(booleanConstFactor.getBooleanValue())
			cons.setAdr(1);
		else
			cons.setAdr(0);
		
		Code.load(cons);
		
	}
	
	// Method type name
	public void visit(ReturnMethodTypeName returnMethodTypeName) {
		if ("main".equalsIgnoreCase(returnMethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		returnMethodTypeName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = returnMethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry 
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	public void visit(VoidMethodTypeName voidMethodTypeName) {
		if ("main".equalsIgnoreCase(voidMethodTypeName.getMethName())) {
			mainPc = Code.pc;
		}
		voidMethodTypeName.obj.setAdr(Code.pc);
		
		// Collect arguments and local variables.
		SyntaxNode methodNode = voidMethodTypeName.getParent();
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry 
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(varCnt.getCount() + fpCnt.getCount());
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	// DESIGNATOR
	
	public void visit(DesignatorAssignment designatorAssignment) {
		
		Code.store(designatorAssignment.getDesignator().obj);
	}
	
	public void visit(DesignatorFactor designatorFactor) {
		//uradiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii bolje
		Code.load(designatorFactor.getDesignator().obj);
		
	}
	
	public void visit(ClassFieldDesignator classFieldDesignator) {
		
		Code.load(classFieldDesignator.getDesignator().obj);
		
	}
	
	public void visit(ArrayDesig ArrayDesig) {
		
		Code.load(ArrayDesig.getDesignator().obj);
		
	}
	
	public void visit(DesignArrAssignment designArrAssignment) {
		
		if(designArrAssignment.getDesignator().obj.getType().getKind() == Struct.Array) {
			//Code.load(designArrAssignment.getDesignator().obj);
			//Code.put(Code.pop);
		}
		//dodaj za klase
		aaList.add(designArrAssignment.getDesignator().obj);
		
	}
	
	public void visit(Epsilon epsilon) {
		aaList.add(Tab.noObj);
		
	}
	
	public void visit(DesignatorArrayAssignment designatorArrayAssignment) {
		//Code.load(designatorArrayAssignment.getDesignator().obj);
		//Code.put(Code.arraylength);
		
		int i = 0;
		
		//Collections.reverse(aaList);
		
		for(Obj o: aaList) {
			if(o != Tab.noObj) {
				//STACK: adr_niza
				Code.load(designatorArrayAssignment.getDesignator().obj);
				//Code.put(Code.pop);
				//Code.put(Code.pop);
				// adr_niza, index
				Code.put (Code.const_); Code.put4(i);
				
				//val_niza
				Code.put(Code.aload);
				
				Code.store(o);
			}
			
			i++;
		}
		
		aaList.clear();
	}
	
	// FUNCTIONS
	public void visit(FuncCallFactor funcCallFactor) {
		
		int offset = funcCallFactor.getFuncCallFactorDes().obj.getAdr() - Code.pc;
		
		Code.put(Code.call); 
		Code.put2(offset);
		
	}
	
	public void visit(DesignatorFuncCall designatorFuncCall) {
		
		int offset = designatorFuncCall.getFuncCallFactorDes().obj.getAdr() - Code.pc;
		
		Code.put(Code.call);
		Code.put2(offset);
	
		if(designatorFuncCall.getFuncCallFactorDes().obj.getType() != Tab.noType) {
			Code.put(Code.pop);
		}
		
	}
	
	public void visit(StatementReturnEmpty statementReturnEmpty) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	public void visit(StatementReturnExpr statementReturnExpr) {
		
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	
	// OPERATIONS
	
	public void visit(TermNegativeExpr termNegativeExpr) {
		
		Code.put(Code.neg);
	}
	
	public void visit(AddExpr addExpr) {
		
		if(addExpr.getAddop() instanceof PlusOperator) {
			Code.put(Code.add);
		}else {
			Code.put(Code.sub);
		}
	}

	public void visit(MultipleFactors multipleFactors) {
		
		if(multipleFactors.getMulop() instanceof MultiplyOperator) {
			Code.put(Code.mul);
		}else if(multipleFactors.getMulop() instanceof DivideOperator){
			Code.put(Code.div);
		}else{
			Code.put(Code.rem);
		}
	}
	
	// ARRAY
	
	public void visit(NewArrayOperatorFactor newArrayOperatorFactor) {
		
		Code.put(Code.newarray);
		
		if(newArrayOperatorFactor.getExpr().struct.getElemType() == Tab.charType) {
			Code.put(0);
		}else {
			Code.put(1);
		}
		
	}

	
	// INCREMENT AND DECREMENT
	public void visit(DesignatorInc designatorInc) {
		
		if(designatorInc.getDesignator().obj.getKind() == Obj.Fld) {
			Code.put(Code.dup);
		}else if(designatorInc.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(designatorInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		
		Code.store(designatorInc.getDesignator().obj);
		
	}


	public void visit(DesignatorDec designatorDec) {
	
		if(designatorDec.getDesignator().obj.getKind() == Obj.Fld) {
			Code.put(Code.dup);
		}else if(designatorDec.getDesignator().obj.getKind() == Obj.Elem) {
			Code.put(Code.dup2);
		}
		
		Code.load(designatorDec.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		
		Code.store(designatorDec.getDesignator().obj);
	}

	
}
