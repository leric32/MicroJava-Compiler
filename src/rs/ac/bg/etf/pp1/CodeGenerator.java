package rs.ac.bg.etf.pp1;

import java.util.*;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
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
	List<Obj> reverseList = new ArrayList<Obj>();
	public void visit(ArrayDesig ArrayDesig) {
		
		Code.load(ArrayDesig.getDesignator().obj);
		
	}
	
	boolean ind = false;
	
	public void visit(DesignArrAssignment designArrAssignment) {
		
		if(designArrAssignment.getDesignator().obj.getType().getKind() == Struct.Array) {
			//Code.load(designArrAssignment.getDesignator().obj);
			//Code.put(Code.pop);
		}
		//dodaj za klase
		aaList.add(designArrAssignment.getDesignator().obj);
		if(designArrAssignment.getDesignator() instanceof ArrayDesignator) {
			ind = true;
		}
	}
	
	public void visit(Epsilon epsilon) {
		aaList.add(Tab.noObj);
		
	}
	
	public void visit(DesignatorArrayAssignment designatorArrayAssignment) {
		//Code.load(designatorArrayAssignment.getDesignator().obj);
		//Code.put(Code.arraylength);
		
		int i = 0;
		
		if(!ind) {
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
		}else {
			i = aaList.size() - 1;
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
				
				i--;
			}
		}
		
		aaList.clear();
		ind = false;
	}
	
	// FUNCTIONS
	public void visit(FuncCallFactor funcCallFactor) {
		
		if(funcCallFactor.getFuncCallFactorDes().obj.getName().equals("ord")) {
			return;
		}
		
		if(funcCallFactor.getFuncCallFactorDes().obj.getName().equals("chr")) {
			return;
		}
		
		if(funcCallFactor.getFuncCallFactorDes().obj.getName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		
		int offset = funcCallFactor.getFuncCallFactorDes().obj.getAdr() - Code.pc;
		
		Code.put(Code.call); 
		Code.put2(offset);
		
	}
	
	public void visit(DesignatorFuncCall designatorFuncCall) {
		
		if(designatorFuncCall.getFuncCallFactorDes().obj.getName().equals("ord")) {
			return;
		}
		
		if(designatorFuncCall.getFuncCallFactorDes().obj.getName().equals("chr")) {
			return;
		}
		
		if(designatorFuncCall.getFuncCallFactorDes().obj.getName().equals("len")) {
			Code.put(Code.arraylength);
			return;
		}
		
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
	
	
	// IF, WHILE AND FOREACH
	
	List<List<Integer>> listAnd = new ArrayList<List<Integer>>();
	List<List<Integer>> listOr = new ArrayList<List<Integer>>();
	List<List<Integer>> listElse = new ArrayList<List<Integer>>();
	List<List<Integer>> listBreak = new ArrayList<List<Integer>>();
	int ifLevel = 0;
	int whileLevel = 0;
	int pcCont = 0;
	
	public void visit(RelopCondition relopCondition) {
		
		if(relopCondition.getRelop() instanceof EqualOperator) {
			Code.putFalseJump(Code.eq, 0);
		}else if(relopCondition.getRelop() instanceof NotEqualOperator) {
			Code.putFalseJump(Code.ne, 0); 			
		} else if(relopCondition.getRelop() instanceof LessOperator) {
			Code.putFalseJump(Code.lt, 0); 			
		} else if(relopCondition.getRelop() instanceof GreaterOperator) {
			Code.putFalseJump(Code.gt, 0); 			
		} else if(relopCondition.getRelop() instanceof GreaterEqualOperator) {
			Code.putFalseJump(Code.ge, 0); 			
		} else if(relopCondition.getRelop() instanceof LessEqualOperator) {
			Code.putFalseJump(Code.le, 0); 			
		}
		
		
		listAnd.get(listAnd.size() - 1).add(Code.pc - 2);
	}
	
	
	public void visit(SingleCondition singleCondition) {
		
		//provera da li je na steku tacan izraz
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		
		listAnd.get(listAnd.size() - 1).add(Code.pc - 2);
		
	}
	
	public void visit(OnlyIf onlyIf) {
		ifLevel++;
		listAnd.add(new ArrayList<Integer>());
		listElse.add(new ArrayList<Integer>());
		listOr.add(new ArrayList<Integer>());
	}
	
	public void visit(FirstFixupForIf firstFixupForIf) {
		//FOR OR
		List<Integer> tmpL = listOr.get(listOr.size() - 1);
		
		for(int i = 0; i < tmpL.size(); i++) {
			Code.fixup(tmpL.get(i));
		}
		
		listOr.get(listOr.size() - 1).clear();
	}
	
	public void visit(SecondFixupForIf secondFixupForIf) {
		
		List<Integer> tmpL = listAnd.get(listAnd.size() - 1);
		
		//if it has else we must save address for fixup
		if(secondFixupForIf.getParent() instanceof StatementIfElse) {
			Code.putJump(-1);
			listElse.get(listElse.size() - 1).add(Code.pc - 2);
		}
		
		for(int i = 0; i < tmpL.size(); i++) {
			//System.out.println(tmpL.get(i));
			Code.fixup(tmpL.get(i));
		}
		
		
		
		listAnd.get(listAnd.size() - 1).clear();
	}
	
	public void visit(StatementIf statementIf) {
		
		//patch for else part
		listOr.remove(listOr.size() - 1);
		listAnd.remove(listAnd.size() - 1);
		listElse.remove(listElse.size() - 1);
		
		ifLevel--;
	}
	
	public void visit(StatementIfElse statementIfElse) {
		
		//patch for else part
		List<Integer> tmpL = listElse.get(listElse.size() - 1);
		
		for(int i = 0; i < tmpL.size(); i++) {
			Code.fixup(tmpL.get(i));
		}
		
		listOr.remove(listOr.size() - 1);
		listAnd.remove(listAnd.size() - 1);
		listElse.remove(listElse.size() - 1);
		
		ifLevel--;
	}
	
	public void visit(ConditionalJump conditionalJump){
		
		Code.putJump(-1);
		listOr.get(listOr.size() - 1).add(Code.pc - 2);
		
		List<Integer> tmpL = listAnd.get(listAnd.size() - 1);
		
		for(int i = 0; i < tmpL.size(); i++) {
			//System.out.println(tmpL.get(i));
			Code.fixup(tmpL.get(i));
		}
		
		listAnd.get(listAnd.size() - 1).clear();
		
	}
	
	public void visit(WhileLoopParen whileLoopParen) {
		whileLevel++;
		
		listAnd.add(new ArrayList<Integer>());
		listElse.add(new ArrayList<Integer>());
		listOr.add(new ArrayList<Integer>());
		listBreak.add(new ArrayList<Integer>());
		
		pcCont = Code.pc;
	} 
	
	public void visit(StatementWhile statementWhile) {
		
		List<Integer> tmpL = listAnd.get(listAnd.size() - 1);
		
		Code.putJump(pcCont);
		
		for(int i = 0; i < tmpL.size(); i++) {
			//System.out.println(tmpL.get(i));
			Code.fixup(tmpL.get(i));
		}
		
		tmpL = listBreak.get(listBreak.size() - 1);
		
		for(int i = 0; i < tmpL.size(); i++) {
			//System.out.println(tmpL.get(i));
			Code.fixup(tmpL.get(i));
		}
		
		listAnd.get(listAnd.size() - 1).clear();
		
		listOr.remove(listOr.size() - 1);
		listAnd.remove(listAnd.size() - 1);
		listElse.remove(listElse.size() - 1);
		listBreak.remove(listBreak.size() - 1);
		
		whileLevel--;
	}
	
	public void visit(StatementBreak statementBreak) {
		
		Code.putJump(0);
		listBreak.get(listBreak.size() - 1).add(Code.pc - 2);
	}
	
	public void visit(StatementContinue statementContinue) {
		
		Code.putJump(pcCont);
	}
	
	public void visit(FirstFixupForWhile firstFixupForWhile) {
		
		
//		List<Integer> tmpL = listContinue.get(listContinue.size() - 1);
//		
//		System.out.println(tmpL.size());
//		for(int i = 0; i < tmpL.size(); i++) {
//			//System.out.println(tmpL.get(i));
//			System.out.println("usao");
//			Code.fixup(tmpL.get(i));
//		}
		
	}
	
	Obj desObjForeach = null;
	int pcForeach;
	
	public void visit(ForEachLoop forEachLoop) {

		//stack put array
		desObjForeach = forEachLoop.getDesignator().obj;
		Code.load(forEachLoop.getDesignator().obj);
		Code.put(Code.arraylength);
		Code.loadConst(0);
	}
	
	public void visit(CheckForeachTwo checkForeachTwo) {
		
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.dup2);
		Code.putFalseJump(Code.eq, pcForeach);
		Code.put(Code.pop);
		Code.put(Code.pop);
		
	}
	
	public void visit(ForEachIdent forEachIdent) {
		
		pcForeach = Code.pc;
		
		Code.put(Code.dup);
		Code.load(desObjForeach);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		Code.put(Code.aload);
		
		Code.store(forEachIdent.obj);
		
		
	}
	
	// CLASS
	Obj currClass = null;
	
	public void visit(NameOfClass nameOfClass) {
		
		currClass = nameOfClass.obj;
		
	}
	
	public void visit(ClassDeclaration classDeclaration) {
		
		currClass = null;
		
	}
	
	public void visit(ConstructorDeclaration constructorDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	
	
	
}
