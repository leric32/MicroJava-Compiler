package rs.ac.bg.etf.pp1;

import java.util.*;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

	public class CodeGenerator extends VisitorAdaptor {
	
		private int mainPc;
		private List<Obj> aaList = new ArrayList<>(); 
		private List<Obj> allClass = new ArrayList<>();
		private List<Obj> allVirtualMethods = new ArrayList<>();
		Obj currClass = null;
		Obj makingClass = null;
		boolean isConst = false;
		boolean isFunc = false;
	
		
		List<Obj> callingMeth = new ArrayList<>();
		
		public class MapClassVF{
			public int adr;
			public String name;
		}
		
		public class Constr{
			public String name;
			public List<Struct> types;
			public int adr;
		}
		
		List<MapClassVF> allClassVF = new ArrayList<>();
		List<Constr> allConstrs = new ArrayList<Constr>();
		
		List<Struct> actParsList = null;
		List<Obj> actParsListObj = null;
 		
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
		
		Obj cons = Tab.insert(Obj.Con, "$const" + numberConstFactor.getNumValue(), Tab.intType);
		cons.setLevel(0);
		cons.setAdr(numberConstFactor.getNumValue());
		
		if(makingClass == null) {
			Code.load(cons);
		}else {
			actParsListObj.add(cons);
		}
		
	}
	
	public void visit(CharConstFactor charConstFactor) {
		
		Obj cons = Tab.insert(Obj.Con, "$const" + charConstFactor.getCharValue(), Tab.charType);
		cons.setLevel(0);
		cons.setAdr(charConstFactor.getCharValue());
		
		if(makingClass == null) {
			Code.load(cons);
		}else {
			actParsListObj.add(cons);
		}
		
	}

	public void visit(BooleanConstFactor booleanConstFactor) {
		
		Obj cons = Tab.insert(Obj.Con, "$const" + booleanConstFactor.getBooleanValue(), TabExtension.boolType);
		cons.setLevel(0);
		if(booleanConstFactor.getBooleanValue())
			cons.setAdr(1);
		else
			cons.setAdr(0);
		
		if(makingClass == null) {
			Code.load(cons);
		}else {
			actParsListObj.add(cons);
		}
		
	}
	
	void tableVirtualFunction() {
		
		int staticTop = SemanticAnalyzer.nVars;
		
		for(Obj oc: allClass) {
			
			//put maybe in some map
			MapClassVF tmpMCVF = new MapClassVF();
			tmpMCVF.adr = staticTop;
			tmpMCVF.name = oc.getName();
			allClassVF.add(tmpMCVF);
			
			for(Obj om: oc.getType().getMembers()) {
				
				if(om.getKind() == Obj.Meth && !om.getName().contains("#") && !om.getName().contains("~")) {
					
					int nameLen = om.getName().length();
					
					//System.out.println(oc.getName() + ":" +om.getName());
					
					//allVirtualMethods.add(om);
					
					for(int i = 0; i < nameLen; i++) {
						
						char c = om.getName().charAt(i);
						Code.loadConst(c);
						Code.put(Code.putstatic);
						Code.put2(staticTop);
						staticTop++;
					}
					
					//end of methods
					Code.put(Code.const_m1);
					Code.put(Code.putstatic);
					Code.put2(staticTop);
					staticTop++;
					
					//add address
					Code.loadConst(om.getAdr());
					Code.put(Code.putstatic);
					Code.put2(staticTop);
					staticTop++;
				}
				
			}
			
			//end of table of virtual functions
			Code.loadConst(-2);
			Code.put(Code.putstatic);
			Code.put2(staticTop);
			staticTop++;
			
		}
		
		SemanticAnalyzer.nVars = staticTop;
		
	}
	
	// Method type name
	public void visit(ReturnMethodTypeName returnMethodTypeName) {
		
		if(currClass != null) {
			allVirtualMethods.add(returnMethodTypeName.obj);
		}
		
		isFunc = true;
		
		if ("main".equalsIgnoreCase(returnMethodTypeName.getMethName())) {
			mainPc = Code.pc;
			isFunc = false;
			tableVirtualFunction();
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
		Code.put(returnMethodTypeName.obj.getLevel());
		Code.put(returnMethodTypeName.obj.getLocalSymbols().size());
	}
	
	public void visit(VoidMethodTypeName voidMethodTypeName) {
		
		if(currClass != null) {
			allVirtualMethods.add(voidMethodTypeName.obj);
		}
		
		isFunc = true;
		
		if ("main".equalsIgnoreCase(voidMethodTypeName.getMethName())) {
			mainPc = Code.pc;
			isFunc = false;
			tableVirtualFunction();
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
		Code.put(voidMethodTypeName.obj.getLevel());
		Code.put(voidMethodTypeName.obj.getLocalSymbols().size());
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
			
			isFunc = false;
		
			Code.put(Code.exit);
			Code.put(Code.return_);
		
	}
	
	public void visit(FuncCallFactorDesignator funcCallFactorDesignator) {
		
		boolean indik = false;
		
		callingMeth.add(funcCallFactorDesignator.getDesignator().obj);
		
		//System.out.println("all virtual methods" + allVirtualMethods.size());
		for(Obj o: allVirtualMethods) {
			if(funcCallFactorDesignator.getDesignator().obj.getName().equals(o.getName())) {
				indik = true;
				break;
			}
		}
		
		if(indik) {
			if(currClass != null || isFunc) {
				Code.put(Code.load_n + 0);
			}
			Code.put(Code.dup);
		}
	}
	
	// DESIGNATOR
	
	public void visit(DesignatorAssignment designatorAssignment) {
		
		Code.store(designatorAssignment.getDesignator().obj);
	}
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public void visit(DesignatorFactor designatorFactor) {
		//uradiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii bolje
//		if(isConst) {
//			report_info(designatorFactor.getDesignator().obj.getName(), designatorFactor);
//			report_info(designatorFactor.getDesignator().obj.getKind() + "", designatorFactor);
//		}
		if(makingClass == null) {
			Code.load(designatorFactor.getDesignator().obj);
		}else {
			actParsListObj.add(designatorFactor.getDesignator().obj);
		}
		
		
	}
	
	public void visit(ClassFieldDesignator classFieldDesignator) {

		if(classFieldDesignator.obj.getName().equals("ispisi")) {
			//report_info("uso " + classFieldDesignator.getDesignator().obj.getKind(), classFieldDesignator);
		}
		
//		if(classFieldDesignator.getDesignator().obj.getKind() == Obj.Elem) {
//			report_info("ClassFieldDesignator NAME" + classFieldDesignator.obj.getName(), classFieldDesignator);
//			//Code.load(classFieldDesignator.getDesignator().obj);
//		}
		
		if(classFieldDesignator.obj.getKind() != Obj.Meth || classFieldDesignator.getDesignator().obj.getKind() == Obj.Elem) {
			Code.load(classFieldDesignator.getDesignator().obj);
		}
		
	}
	List<Obj> reverseList = new ArrayList<Obj>();
	public void visit(ArrayDesig ArrayDesig) {
		
		Code.load(ArrayDesig.getDesignator().obj);
		
	}
	
	public void visit(SimpleDesignator simpleDesignator) {
		
		
		if(simpleDesignator.obj.getKind() == Obj.Fld ) {
			if(simpleDesignator.getParent() instanceof DesignatorStatement || simpleDesignator.getParent() instanceof DesignatorFactor) {
				Code.put(Code.load_n + 0);
			}
			
		}
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
		
		boolean ind = false;
		
		for(Obj o: allVirtualMethods) {
			if(funcCallFactor.getFuncCallFactorDes().obj.getName().equals(o.getName())) {
				ind = true;
				break;
			}
		}
		
		if(!ind) {
			int offset = funcCallFactor.getFuncCallFactorDes().obj.getAdr() - Code.pc;
			
			Code.put(Code.call); 
			Code.put2(offset);
		}else {
			
			//Code.put(Code.load_n + 0);
			//Code.put(Code.dup);
			Code.put(Code.getfield);
			Code.put2(0);
			
			Code.put(Code.invokevirtual);
			
			for(int i = 0; i < funcCallFactor.getFuncCallFactorDes().obj.getName().length(); i++) {
				Code.put4(funcCallFactor.getFuncCallFactorDes().obj.getName().charAt(i));
			}
			
			Code.put4(-1);
			
		}
		
		callingMeth.remove(callingMeth.size() - 1);
		
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
		
		boolean ind = false;
		
		
		//System.out.println(allVirtualMethods.size());
		for(Obj o: allVirtualMethods) {
			if(designatorFuncCall.getFuncCallFactorDes().obj.getName().equals(o.getName())) {
				ind = true;
				break;
			}
		}
		if(!ind) {
			int offset = designatorFuncCall.getFuncCallFactorDes().obj.getAdr() - Code.pc;
			
			Code.put(Code.call);
			Code.put2(offset);
		
			if(designatorFuncCall.getFuncCallFactorDes().obj.getType() != Tab.noType) {
				Code.put(Code.pop);
			}
		}else {
			
			//Code.put(Code.load_n + 0);
			//Code.put(Code.dup);
			Code.put(Code.getfield);
			Code.put2(0);
			
			Code.put(Code.invokevirtual);
			
			for(int i = 0; i < designatorFuncCall.getFuncCallFactorDes().obj.getName().length(); i++) {
				Code.put4(designatorFuncCall.getFuncCallFactorDes().obj.getName().charAt(i));
			}
			
			Code.put4(-1);
		}
		
		callingMeth.remove(callingMeth.size() - 1);
	}
	
	public void visit(StatementReturnEmpty statementReturnEmpty) {
		
		//Code.put(Code.exit);
		//Code.put(Code.return_);
		
	}
	
	public void visit(StatementReturnExpr statementReturnExpr) {
		
		//Code.put(Code.exit);
		//Code.put(Code.return_);
		
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
	
	
	public void visit(NameOfClass nameOfClass) {
		
		currClass = nameOfClass.obj;
		allClass.add(nameOfClass.obj);
	}
	
	public void visit(ClassDeclaration classDeclaration) {
		
		currClass = null;
		
	}
	
	public void visit(ConstructorDeclaration constructorDeclaration) {
		Code.put(Code.exit);
		Code.put(Code.return_);
		
		isConst = false;
	}
	
	public void visit(Type type) {
			
		Obj tmpClassObj = Tab.find(type.getTypeName());
		if(type.getParent() instanceof NewClassWithActParsOperatorFactor) {
			
			makingClass = tmpClassObj;
			actParsList = new ArrayList<Struct>();
			actParsListObj = new ArrayList<Obj>();
			//System.out.println("KLASNI TIP:" + nameOfConstr);
		}
	}
	
	public void visit(NewClassWithActParsOperatorFactor newClassWithActParsOperatorFactor) {
		Code.put(Code.new_);
		Code.put2(newClassWithActParsOperatorFactor.struct.getNumberOfFields() * 4);
		
		Code.put(Code.dup);
		Code.put(Code.dup);
		
		int tmpAdr = -1;
		
		for(MapClassVF k: allClassVF) {
			if(k.name.equals(newClassWithActParsOperatorFactor.getType().getTypeName())) {
				tmpAdr = k.adr;
			}
		}
		
		Code.loadConst(tmpAdr);
		
		
		String nameClass =  newClassWithActParsOperatorFactor.getType().getTypeName();
		int tmpA = -1;
		
		for(Constr c: allConstrs) {
			//System.out.println(c.name + "-" + c.types.size());
			if(c.name.contains(nameClass) && c.types.size() == actParsList.size()) {
				
				if(actParsList.size() == 0) {
					tmpA = c.adr;
					break;
				}
				
				int num = 0;
				
				for(int j = 0; j < actParsList.size(); j++) {
					if(c.types.get(j).getKind() == actParsList.get(j).getKind()) {
						num++;
					}
				}
				
				if(num == c.types.size()) {
					tmpA = c.adr;
					break;
				}
				
			}
		}
		
		//funcCallFactor.getFuncCallFactorDes().obj.getAdr()
		
		//System.out.println(tmpA);
		
		
		
		Code.put(Code.putfield);
		Code.put2(0);
		
		for(Obj o: actParsListObj) {
			//System.out.println(o.getAdr());
			Code.load(o);
		}
		
		Code.put(Code.call); 
		Code.put2(tmpA - Code.pc - 2);
		
		makingClass = null;
		
		actParsList.clear();
		actParsListObj.clear();
		
		
		//call cosnructor
	}
	
	public void visit(ClassBodyNoMethod classBodyNoMethod) {
		
		Obj objTmp = null;
		
		for(Obj o: currClass.getType().getMembers()) {
			if(o.getName().contains("~")) {
				objTmp = o;
				break;
			}
		}
		
		Constr tmpC = new Constr();
		tmpC.name = objTmp.getName();
		tmpC.types = new ArrayList<Struct>();
		tmpC.adr = Code.pc;
		allConstrs.add(tmpC);
		
		//enter
		Code.put(Code.enter);
		Code.put(objTmp.getLevel()); 
		Code.put(objTmp.getLocalSymbols().size());
		
		// exit 
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	public void visit(ClassBodyWithMethodNoConstructor classBodyWithMethodNoConstructor) {
		
		Obj objTmp = null;
		
		for(Obj o: currClass.getType().getMembers()) {
			if(o.getName().contains("~")) {
				objTmp = o;
				break;
			}
		}
		
		Constr tmpC = new Constr();
		tmpC.name = objTmp.getName();
		tmpC.types = new ArrayList<Struct>();
		tmpC.adr = Code.pc;
		allConstrs.add(tmpC);
		
		//enter
		Code.put(Code.enter);
		Code.put(objTmp.getLevel()); 
		Code.put(objTmp.getLocalSymbols().size());
		
		// exit 
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}

	public void visit(ClassBodyNoMethodWithEmptyBrackets classBodyNoMethodWithEmptyBrackets) {
		
		Obj objTmp = null;
		
		for(Obj o: currClass.getType().getMembers()) {
			if(o.getName().contains("~")) {
				objTmp = o;
				break;
			}
		}
		
		Constr tmpC = new Constr();
		tmpC.name = objTmp.getName();
		tmpC.types = new ArrayList<Struct>();
		tmpC.adr = Code.pc;
		allConstrs.add(tmpC);
		
		//enter
		Code.put(Code.enter);
		Code.put(objTmp.getLevel()); 
		Code.put(objTmp.getLocalSymbols().size());
		
		// exit 
		Code.put(Code.exit);
		Code.put(Code.return_);
		
	}
	
	public void visit(ConstructorDeclarationName constructorDeclarationName) {
		
		//callingMeth.add(funcCallFactorDesignator.getDesignator().obj);
		
		Code.put(Code.enter);
		Code.put(constructorDeclarationName.obj.getLevel());
		Code.put(constructorDeclarationName.obj.getLocalSymbols().size());
		
		isConst = true;
		
		Constr tmpC = new Constr();
		tmpC.name = constructorDeclarationName.obj.getName();
		tmpC.types = new ArrayList<Struct>();
		tmpC.adr = Code.pc;
		allConstrs.add(tmpC);
		
	}
	
	public void visit(SingleFormalParameter SingleFormalParameter) {
		if(isConst) {
			Constr t = allConstrs.get(allConstrs.size() - 1);
			t.types.add(SingleFormalParameter.getType().struct);
		}
	}
	
	public void visit(ActualParam actualParam) {
		
		boolean indik = false;
		
		
		if(makingClass != null) {
			
			actParsList.add(actualParam.getExpr().struct);
			
		}else {
			Obj tmpObj = callingMeth.get(callingMeth.size() - 1);
			
			//System.out.println("all virtual methods" + allVirtualMethods.size());
			for(Obj o: allVirtualMethods) {
				if(tmpObj.getName().equals(o.getName())) {
					indik = true;
					break;
				}
			}
			
			if(indik) {
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
			}
		}
		
	}
	
public void visit(ActualParams actualParams) {
		
		boolean indik = false;
		
		if(makingClass != null) {
			actParsList.add(actualParams.getExpr().struct);
		}else {
			Obj tmpObj = callingMeth.get(callingMeth.size() - 1);
			
			//System.out.println("all virtual methods" + allVirtualMethods.size());
			for(Obj o: allVirtualMethods) {
				if(tmpObj.getName().equals(o.getName())) {
					indik = true;
					break;
				}
			}
			
			if(indik) {
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
			}
		}
		
	}
	
}
