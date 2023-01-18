package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import java.util.*;

public class SemanticAnalyzer extends VisitorAdaptor {
	
	//Logger
	Logger log = Logger.getLogger(getClass());
	
	int countPrint = 0;
	int countCall = 0;
	static int nVars;
	
	boolean errorDetected = false;
	boolean hasMainMeth = false;
	Struct currType = null;
	boolean isArr = false;
	boolean printRead = false;
	
	//maps of methods
	Map<String, List<Struct>> allMethods = new HashMap<String, List<Struct>>();
	
	//maps of class constructors
	List<List<Struct>> allClassConstr = new ArrayList<List<Struct>>();
	
	public class CustomPair{
		public String name;
		public Struct struct;
		
		public CustomPair(String name, Struct struct) {
			this.name = name;
			this.struct = struct;
		}
		
	}
	List<CustomPair> constrPars = new ArrayList<CustomPair>();
	boolean isConstr = false;
	
	//classes
	Obj currClass = null;
	Obj superClass = null;
	
	//methods and constructors
	List<Obj> callingMeth = new ArrayList<>();
	int actParsNum = 0;
	String nameOfConstr = null;
	
	//Constructor
	SemanticAnalyzer(){
		Tab.currentScope.addToLocals(new Obj(Obj.Type, "bool", TabExtension.boolType));
		
		//add in for ord, len and chr
		List<Struct> ordList = new ArrayList<Struct>();
		ordList.add(Tab.charType);
		allMethods.put("ord", ordList);
		
		List<Struct> lenList = new ArrayList<Struct>();
		
		Struct s1 = new Struct(Struct.Array);
		s1.setElementType(Tab.intType);
		Struct s2 = new Struct(Struct.Array);
		s2.setElementType(Tab.charType);
		Struct s3 = new Struct(Struct.Array);
		s3.setElementType(TabExtension.boolType);
		lenList.add(s1);
		lenList.add(s2);
		lenList.add(s3);
		allMethods.put("len", lenList);
		
		List<Struct> chrList = new ArrayList<Struct>();
		chrList.add(Tab.intType);
		allMethods.put("chr", chrList);
		
	}
	
	//util funtions
	String typeOfStructNode(int i) {
		switch(i) {
		case 0:
			return "none";
		case 1:
			return "int";
		case 2:
			return "char";
		case 3:
			return "array";
		case 4:
			return "class";
		case 5:
			return "bool";
		default:
			return "error";	
		}
	}
	
	//PRINT AND ERROR
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	//PROGRAM
	
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getPName(), Tab.noType);
		Tab.openScope();
	}
	
	public void visit(Program program) {
		
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		
		//if main is not been found
		if(!hasMainMeth) {
			report_error("SEMANTICKA GRESKA: Glavni metod sa potpisom: void main() {...} metod nije pronadjen", null);
		}
	}
	
	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		
		if (typeNode == Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola", type);
			type.struct = Tab.noType;
		} 
		else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} 
			else {
				report_error("SEMANTICKA GRESKA: Ime " + type.getTypeName() + " ne predstavlja tip ", type);
				type.struct = Tab.noType;
			}
			
			Obj tmpClassObj = Tab.find(type.getTypeName());
			if(tmpClassObj.getType().getKind() == Struct.Class && type.getParent() instanceof NewClassWithActParsOperatorFactor) {
				
				nameOfConstr = tmpClassObj.getName();
				//System.out.println("KLASNI TIP:" + nameOfConstr);
			}
		} 
		
		currType = type.struct;
	}
	
	// CONSTANTS

	public void visit(ConstantValueBoolean constantValueBoolean) {
		
		//check if the name is already in symbol table
		Obj constNode = Tab.find(constantValueBoolean.getConstName());
		if(constNode != Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Ime " + constantValueBoolean.getConstName() + " je vec deklarisano", constantValueBoolean);
			return;
		}
		
		//check if the type is compatible with the assignment value
		if(!TabExtension.boolType.equals(currType)) {
			report_error("SEMANTICKA GRESKA: Tip bool nije isti tip vrednosti kao i " + typeOfStructNode(currType.getKind()), constantValueBoolean);
			return;
		}
		
		constNode = Tab.insert(Obj.Con, constantValueBoolean.getConstName(), TabExtension.boolType);
		int cVal = (constantValueBoolean.getBooleanConstValue() == true)? 1:0;
		constNode.setAdr(cVal);
		
	}
	
	public void visit(ConstantValueNumber constantValueNumber) {
		
		//check if the name is already in symbol table
		Obj constNode = Tab.find(constantValueNumber.getConstName());
		if(constNode != Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Ime " + constantValueNumber.getConstName() + " je vec deklarisano", constantValueNumber);
			return;
		}
		
		//check if the type is compatible with the assignment value
		if(!Tab.intType.equals(currType)) {
			report_error("SEMANTICKA GRESKA: Tip int nije isti tip vrednosti kao i " + typeOfStructNode(currType.getKind()), constantValueNumber);
			return;
		}
		
		constNode = Tab.insert(Obj.Con, constantValueNumber.getConstName(), Tab.intType);
		constNode.setAdr(constantValueNumber.getNumberConstValue());
		
	}
	
	public void visit(ConstantValueChar constantValueChar) {
		
		//check if the name is already in symbol table
		Obj constNode = Tab.find(constantValueChar.getConstName());
		if(constNode != Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Ime " + constantValueChar.getConstName() + " je vec deklarisano", constantValueChar);
			return;
		}
		
		//check if the type is compatible with the assignment value
		if(!Tab.charType.equals(currType)) {
			report_error("SEMANTICKA GRESKA: Tip char nije isti tip vrednosti kao i " + typeOfStructNode(currType.getKind()), constantValueChar);
			return;
		}
		
		constNode = Tab.insert(Obj.Con, constantValueChar.getConstName(), Tab.charType);
		constNode.setAdr(constantValueChar.getCharConstValue());
		
	}
	
	public void visit(VarDeclarationOfArray varDeclarationOfArray) {
		isArr = true;
		
	}
	
	Struct returnStruct = null;
	String methName = null;
	Obj currMeth = null;
	boolean returnFound = false;
	int numOfFormPars = 0;
	
	// METHODS
	public void visit(ReturnMethodTypeName returnMethodTypeName) {
		methName = returnMethodTypeName.getMethName();
		
		//check if the name is already in symbol table in current scope--------DODAJ ZA CLASS-------------
		Obj methNode = Tab.currentScope().findSymbol(methName);
		if(methNode != null && currClass == null) {
			report_error("SEMANTICKA GRESKA: Ime metode ili funkcije " + methName + " je ranije vec deklarisano", returnMethodTypeName);
			Tab.openScope();
			return;
		}
		
		//Add if it is overrided
		//Insert into symbol table new name of method
		currMeth = Tab.insert(Obj.Meth, methName, currType);
		returnMethodTypeName.obj = currMeth;
		
		//open of inner scope of method
		Tab.openScope();
		
		//check if it is method of class
		if(currClass != null) {
			Tab.insert(Obj.Var, "this", currClass.getType());
		}
				
		
		//add into hashmap
		allMethods.put(methName, new ArrayList<>());
		
		if(currClass == null) {
			report_info("Definicija funkcije " + methName, returnMethodTypeName);
		}else {
			report_info("Definicija metode " + methName, returnMethodTypeName);
		}
	}
	
	public void visit(VoidMethodTypeName voidMethodTypeName) {
		methName = voidMethodTypeName.getMethName();
		//check if the name is already in symbol table in current scope--------DODAJ ZA CLASS-------------
		Obj methNode = Tab.currentScope().findSymbol(methName);
		if(methNode != null && currClass == null) {
			report_error("SEMANTICKA GRESKA: Ime metode " + methName + " je ranije vec deklarisano", voidMethodTypeName);
			Tab.openScope();
			return;
		}
		
		//Add if it is overrided
		//Insert into symbol table new name of method
		currMeth = Tab.insert(Obj.Meth, methName, Tab.noType);
		voidMethodTypeName.obj = currMeth;
		
		//open of inner scope of method
		Tab.openScope();
		
		//check if it is method of class
		if(currClass != null) {
			Tab.insert(Obj.Var, "this", currClass.getType());
		}
				
		
		//add into hashmap
		allMethods.put(methName, new ArrayList<>());
		
		if(currClass == null) {
			report_info("Definicija funkcije " + methName, voidMethodTypeName);
		}else {
			report_info("Definicija metode " + methName, voidMethodTypeName);
		}
		
	}
	
	public void visit(SingleFormalParameter singleFormalParameter) {
		//increment number of formal pars
		numOfFormPars++;
		
		//check if the parameter is already in the same scope defined
		if (Tab.currentScope.findSymbol(singleFormalParameter.getParamName()) != null){
			report_error("SEMANTICKA GRESKA: Ime formalnog parametra " + singleFormalParameter.getParamName() + " je vec deklarisano", singleFormalParameter);
			return;
		}
		
		Obj parNode = null;
		
		if(isArr) {
			if(isConstr == false) {
				parNode = Tab.insert(Obj.Var, singleFormalParameter.getParamName(), new Struct(Struct.Array, currType));
				if(currMeth != null) {
					List<Struct> listOfMethods = allMethods.get(currMeth.getName());
					listOfMethods.add(parNode.getType());
					allMethods.put(currMeth.getName(), listOfMethods);
				}
			}else {
				Tab.insert(Obj.Var, singleFormalParameter.getParamName(), new Struct(Struct.Array, currType));
				constrPars.add(new CustomPair(singleFormalParameter.getParamName(), new Struct(Struct.Array, currType)));
			}

			isArr = false;
		}else {
			if(isConstr == false) {
				parNode = Tab.insert(Obj.Var, singleFormalParameter.getParamName(), currType);
				if(currMeth != null) {
					List<Struct> listOfMethods = allMethods.get(currMeth.getName());
					listOfMethods.add(parNode.getType());
					allMethods.put(currMeth.getName(), listOfMethods);
				}
			}else {
				Tab.insert(Obj.Var, singleFormalParameter.getParamName(), currType);
				constrPars.add(new CustomPair(singleFormalParameter.getParamName(), currType));
			}
		}
		
	}
	
	public void visit(NotLastVarDeclarations notLastVarDeclarations) {
		//check if the parameter is already in the same scope defined
		if (Tab.currentScope.findSymbol(notLastVarDeclarations.getVarName()) != null){
			report_error("SEMANTICKA GRESKA: Ime promenljive " + notLastVarDeclarations.getVarName() + " je vec deklarisano", notLastVarDeclarations);
			return;
		}
		
		//dodaj ako je polje klase Obj.Fld
		if(isArr) {
			Tab.insert(Obj.Var, notLastVarDeclarations.getVarName(), new Struct(Struct.Array, currType));
			report_info("Kreirana je promenjiva " + notLastVarDeclarations.getVarName() + "[]", notLastVarDeclarations);
			isArr = false;
		}else {
			Tab.insert(Obj.Var, notLastVarDeclarations.getVarName(), currType);
			report_info("Kreirana je promenjiva " + notLastVarDeclarations.getVarName(), notLastVarDeclarations);
		}
		
	}
	
	public void visit(LastVarDeclarations lastVarDeclarations) {
		//check if the parameter is already in the same scope defined
		if (Tab.currentScope.findSymbol(lastVarDeclarations.getVarName()) != null){
			report_error("SEMANTICKA GRESKA: Ime promenljive " + lastVarDeclarations.getVarName() + " je vec deklarisano", lastVarDeclarations);
			return;
		}
		
		//dodaj ako je polje klase Obj.Fld
		if(isArr) {
			Tab.insert(Obj.Var, lastVarDeclarations.getVarName(), new Struct(Struct.Array, currType));
			report_info("Kreirana je promenjiva " + lastVarDeclarations.getVarName() + "[]", lastVarDeclarations);
			isArr = false;
		}else {
			Tab.insert(Obj.Var, lastVarDeclarations.getVarName(), currType);
			report_info("Kreirana je promenjiva " + lastVarDeclarations.getVarName(), lastVarDeclarations);
		}
		
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		if (!returnFound && currMeth.getType() != Tab.noType) {
			report_error("SEMANTICKA GRESKA: Funcija " + currMeth.getName() + " nema return iskaz ", methodDeclaration);
		}
		
		//add number of formal parameters
		if(currClass != null)
			numOfFormPars++; //for this
		currMeth.setLevel(numOfFormPars);
		Tab.chainLocalSymbols(currMeth);
		
		//closing inner scope of method
		Tab.closeScope();
		
		//check if this is main method
		if (methName.equals("main") && numOfFormPars == 0) {
			hasMainMeth = true;
		}
		
		//print pars
//		List<Struct> listOfMethods = allMethods.get(currMeth.getName());
//		for(Struct s: listOfMethods) {
//			report_info("parametar: " + typeOfStructNode(s.getKind()), null);
//		}
		
		//reset
		methName = null;
		returnFound = false;
		currMeth = null;
		numOfFormPars = 0;
	}
	
	//DESIGNATOR STATEMENTS
	
	public void visit(DesignatorAssignment designatorAssignment) {
		
		//check if it is declared
		if(designatorAssignment.getDesignator().obj == Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Promenljiva sa leve strane nije deklarisana ", designatorAssignment);
			return;
		}
		
		//check if left part is var, fld or elem
		if(designatorAssignment.getDesignator().obj.getKind() != Obj.Var 
				&& designatorAssignment.getDesignator().obj.getKind() != Obj.Fld
				&& designatorAssignment.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("SEMANTICKA GRESKA: Designator nije promelnjiva, element niza ili polje unutar objekta ", designatorAssignment);
			return;
		}
		
		//check if right part is assignable to left
//		report_info("des obj" + designatorAssignment.getDesignator().obj.getType().getKind(), designatorAssignment);
//		report_info("expr obj" + designatorAssignment.getExpr().struct.getKind(), designatorAssignment);
//		if(designatorAssignment.getExpr().struct.getKind() != Struct.Class && !designatorAssignment.getExpr().struct.assignableTo(designatorAssignment.getDesignator().obj.getType())) {
//			report_info("ulazi", null);
//		}else if(designatorAssignment.getExpr().struct.getKind() == Struct.Class && designatorAssignment.getDesignator().obj.getType().getKind() == Struct.Class){
//			report_info("ne ulazi", null);
//		}
		if(designatorAssignment.getExpr().struct.getKind() != Struct.Class && !designatorAssignment.getExpr().struct.assignableTo(designatorAssignment.getDesignator().obj.getType())) {
			report_error("SEMANTICKA GRESKA: Leva strana nije kompatibilna desnoj strani pri dodeli", designatorAssignment); 
		}else if(designatorAssignment.getExpr().struct.getKind() == Struct.Class && designatorAssignment.getDesignator().obj.getType().getKind() == Struct.Class){
			//System.out.println("usao u class assignment");
			
			//System.out.println("1 class " + designatorAssignment.getDesignator().obj.getType().getElemType());
			//System.out.println("1 class " + designatorAssignment.getExpr().struct.getElemType().getElemType());
			
			Struct s1 = null;
			Struct s2 = null;
			boolean ind = true;
			
			if(designatorAssignment.getDesignator().obj.getType().getKind() == Struct.Array) {
				s1 = designatorAssignment.getDesignator().obj.getType().getElemType();
			}else {
				s1 = designatorAssignment.getDesignator().obj.getType();
			}
			
			s2 = designatorAssignment.getExpr().struct;
			
			while(s2 != null) {
				if(s1 == s2) {
					ind = false;
				}
				s2 = s2.getElemType();
			}
			
			if(ind && designatorAssignment.getExpr().struct != Tab.nullType) {
				report_error("SEMANTICKA GRESKA: Leva strana nije kompatibilna desnoj strani pri dodeli", designatorAssignment);
			}
			
		}
		
	}
	
	public void visit(DesignatorInc designatorInc) {
		//check if left part is var, fld or elem
		if(designatorInc.getDesignator().obj.getKind() != Obj.Var 
				&& designatorInc.getDesignator().obj.getKind() != Obj.Fld
				&& designatorInc.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("SEMANTICKA GRESKA: Designator nije promelnjiva, element niza ili polje unutar objekta", designatorInc);
			//return;
		}
		
		
		if(designatorInc.getDesignator().obj.getType() != Tab.intType) {
			report_error("SEMANTICKA GRESKA: Inkrementiranje se radi nad tipom int, a promenljiva je tipa " +
					typeOfStructNode(designatorInc.getDesignator().obj.getType().getKind()) + " ", designatorInc);
		}
	}
	
	public void visit(DesignatorDec designatorDec) {
		//check if left part is var, fld or elem
		if(designatorDec.getDesignator().obj.getKind() != Obj.Var 
				&& designatorDec.getDesignator().obj.getKind() != Obj.Fld
				&& designatorDec.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("SEMANTICKA GRESKA: Designator nije promelnjiva, element niza ili polje unutar objekta", designatorDec);
			//return;
		}
		
		
		if(designatorDec.getDesignator().obj.getType() != Tab.intType) {
			report_error("SEMANTICKA GRESKA: Dekrementiranje se radi nad tipom int, a promenljiva je tipa " +
					typeOfStructNode(designatorDec.getDesignator().obj.getType().getKind()) + " ", designatorDec);
		}
	}
	
	
	
	public void visit(FunctionCallName functionCallName) {
		
		if(functionCallName.getDesignator().obj.getKind() != Obj.Meth) {
			report_error("SEMANTICKA GRESKA: Ime " + functionCallName.getDesignator().obj.getName() + " ne predstavlja funkciju ", functionCallName);
			//return;
		}
		
		callingMeth.add(functionCallName.getDesignator().obj);
		
		report_info("Detektovan je poziv funkcije " + functionCallName.getDesignator().obj.getName(), functionCallName);
	}
	
	public void visit(FunctionCall functionCall) {
		
		//reset
		actParsNum = 0;
		callingMeth.remove(callingMeth.size() - 1);
		
	}
	
	public void visit(DesignatorFuncCall designatorFuncCall) {
		
		List<Struct> listOfPars = allMethods.get(callingMeth.get(callingMeth.size() - 1).getName());
		if(actParsNum != listOfPars.size()) {
			report_error("SEMANTICKA GRESKA: Broj stvarnih argumenata funkcije " + callingMeth.get(callingMeth.size() - 1).getName() +
					" nije isti kao i broj formalnih argumenata", designatorFuncCall);
			//return;
		}
		
		report_info("Detektovan je poziv funkcije " + designatorFuncCall.getFuncCallFactorDes().obj.getName(), designatorFuncCall);
		
		//reset
		actParsNum = 0;
		callingMeth.remove(callingMeth.size() - 1);
	}
	
	// ACTUAL PARAMS
	public void visit(ActualParams actualParams) {
		
		//check if there is calling method
		if(callingMeth.size() == 0 && nameOfConstr == null) {
			report_error("SEMANTICKA GRESKA: Funkcija ili metoda koja se poziva ne postoji!", actualParams);
			return;
		}
		
		if(nameOfConstr == null) {
			List<Struct> listOfPars = allMethods.get(callingMeth.get(callingMeth.size() - 1).getName());
			
			if(!actualParams.getExpr().struct.compatibleWith(listOfPars.get(actParsNum))) {
				if(listOfPars.get(actParsNum).getKind() == Struct.Array) {
					
				}
				report_error("SEMANTICKA GRESKA: Formalni parametar funkcije " + callingMeth.get(callingMeth.size() - 1).getName() +
						" koji je tipa " + (listOfPars.get(actParsNum).getKind() == Struct.Array? "Array of ": "") +  
						typeOfStructNode(listOfPars.get(actParsNum).getKind() == Struct.Array? listOfPars.get(actParsNum).getElemType().getKind() : listOfPars.get(actParsNum).getKind()) + " nije kompatibilan sa stvarnim argumentom " +
						"koji je tipa " + (actualParams.getExpr().struct.getKind() == Struct.Array? "Array of ": "") 
						+ typeOfStructNode(actualParams.getExpr().struct.getKind() == Struct.Array? actualParams.getExpr().struct.getKind() : actualParams.getExpr().struct.getKind()), actualParams);
			}
		}else {
			boolean ind = true;
			Struct tmpStr = null;
			for(Map.Entry<String, List<Struct>> m : allMethods.entrySet()) {
				if(m.getKey().contains("#") && (m.getKey().contains(nameOfConstr))) {
					List<Struct> listOfPars = m.getValue();
					if(listOfPars.size() > actParsNum && actualParams.getExpr().struct.compatibleWith(listOfPars.get(actParsNum))) {
						ind = false;
						tmpStr = listOfPars.get(actParsNum);
						//System.out.println(tmpStr);
					}
					
				}
			}
			
			if(ind) {
				report_error("SEMANTICKA GRESKA: Formalni parametar konstruktora nije kompatibilan sa stvarnim argumentom " +
						"koji je tipa "+ (actualParams.getExpr().struct.getKind() == Struct.Array? "Array of ": "") +
						typeOfStructNode(actualParams.getExpr().struct.getKind() == Struct.Array? actualParams.getExpr().struct.getElemType().getKind() : actualParams.getExpr().struct.getKind()), actualParams);
			}
		}
		actParsNum++;
	}
	
	public void visit(ActualParam actualParam) {
		
		//check if there is calling method
		if(callingMeth.size() == 0 && nameOfConstr == null) {
			report_error("SEMANTICKA GRESKA: Funkcija ili metoda koja se poziva ne postoji!", actualParam);
			return;
		}
		
		if(nameOfConstr == null) {
			List<Struct> listOfPars = allMethods.get(callingMeth.get(callingMeth.size() - 1).getName());
			
			if(!actualParam.getExpr().struct.compatibleWith(listOfPars.get(actParsNum))) {
				report_error("SEMANTICKA GRESKA: Formalni parametar funkcije " + callingMeth.get(callingMeth.size() - 1).getName() +
						" koji je tipa " + (listOfPars.get(actParsNum).getKind() == Struct.Array? "Array of ": "") +
						typeOfStructNode(listOfPars.get(actParsNum).getKind() == Struct.Array? listOfPars.get(actParsNum).getElemType().getKind() : listOfPars.get(actParsNum).getKind()) +" nije kompatibilan sa stvarnim argumentom " +
						"koji je tipa "+ (actualParam.getExpr().struct.getKind() == Struct.Array? "Array of ": "") +
						typeOfStructNode(actualParam.getExpr().struct.getKind() == Struct.Array? actualParam.getExpr().struct.getElemType().getKind() : actualParam.getExpr().struct.getKind()), actualParam);
			} 
		}else {
			boolean ind = true;
			Struct tmpStr = null;
			for(Map.Entry<String, List<Struct>> m : allMethods.entrySet()) {
				if(m.getKey().contains("#") && (m.getKey().contains(nameOfConstr))) {
					List<Struct> listOfPars = m.getValue();
					if(listOfPars.size() > actParsNum && actualParam.getExpr().struct.compatibleWith(listOfPars.get(actParsNum))) {
						ind = false;
						tmpStr = listOfPars.get(actParsNum);
						//System.out.println(tmpStr);
					}
					
				}
			}
			
			if(ind) {
				report_error("SEMANTICKA GRESKA: Formalni parametar konstruktora nije kompatibilan sa stvarnim argumentom " +
						"koji je tipa "+ (actualParam.getExpr().struct.getKind() == Struct.Array? "Array of ": "") +
						typeOfStructNode(actualParam.getExpr().struct.getKind() == Struct.Array? actualParam.getExpr().struct.getElemType().getKind() : actualParam.getExpr().struct.getKind()), actualParam);
			}
		}
		actParsNum++;
	}
	
	Struct dstArrAss = null;
	
	public void visit(DesignArrAssignment designArrAssignment) {
		//check if left part is var, fld or elem
		if(designArrAssignment.getDesignator().obj.getKind() != Obj.Var 
				&& designArrAssignment.getDesignator().obj.getKind() != Obj.Fld
				&& designArrAssignment.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("SEMANTICKA GRESKA: Element " + designArrAssignment.getDesignator().obj.getName() + " na liniji "
				+ designArrAssignment.getLine() + " nije promelnjiva, element niza ili polje unutar objekta", designArrAssignment);
			//return;
		}
		
		if(dstArrAss == null)
			dstArrAss = designArrAssignment.getDesignator().obj.getType();
		
	}
	
	public void visit(DesignatorArrayAssignment designatorArrayAssignment) {
		
		//check if right value is array
		if(designatorArrayAssignment.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("SEMANTICKA GRESKA: Element " + designatorArrayAssignment.getDesignator().obj.getName() + " na liniji "
					+ designatorArrayAssignment.getLine() + " nije niz", designatorArrayAssignment);
			dstArrAss = null;
			return;
		}
		
		
		//check if right part is assignable to left
		if(!designatorArrayAssignment.getDesignator().obj.getType().getElemType().assignableTo(dstArrAss)) {
			report_error("SEMANTICKA GRESKA: Leva strana nije kompatibilna desnoj strani pri dodeli", designatorArrayAssignment); 
		}
		
		dstArrAss = null;
	}
	
	int whileLoopsNum = 0;
	int foreachLoopsNum = 0;
	
	//while and foreach loop
	public void visit(WhileLoopParen whileLoopParen) {
		whileLoopsNum++;
	}
	
	public void visit(ForEachLoop forEachLoop) {
		foreachLoopsNum++;
	}
	
	
	public void visit(StatementWhile statementWhile) {
		whileLoopsNum--;
	}
	
	public void visit(StatementForeach statementForeach) {
		foreachLoopsNum--;
		
		//type of designator in foreach loop must be array
		if(statementForeach.getForEachLoop().getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("SEMANTICKA GRESKA: Designator u foreach petlje treba biti niz proizvoljnog tipa, a ne "
					+ typeOfStructNode(statementForeach.getForEachLoop().getDesignator().obj.getType().getKind()) + " tipa", statementForeach);        	
			return;
		}
		
		//ident must be local or global var
		Obj varNode = Tab.find(statementForeach.getForEachIdent().getFeVar());
		if(varNode.getKind() != Obj.Var) {
			report_error("SEMANTICKA GRESKA: Identifikator u foreach pelji mora biti promnljiva, a nije", statementForeach);
			return;
		}
		
		//ident must be same type as type od array element
		if(varNode.getType() != statementForeach.getForEachLoop().getDesignator().obj.getType().getElemType()) {
			report_error("SEMANTICKA GRESKA: Identifikator u foreach pelji mora biti istog tipa kao i elementi niza -" + 
					typeOfStructNode(statementForeach.getForEachLoop().getDesignator().obj.getType().getElemType().getKind()) + ", a ident je tipa - "
			+ typeOfStructNode(varNode.getType().getKind()), statementForeach);
			return;
		}
		
	}
	
	public void visit(ForEachIdent forEachIdent) {
		Obj varNode = Tab.find(forEachIdent.getFeVar());
		forEachIdent.obj = varNode;
	}
	
	public void visit(SingleCondition singleCondition) {
		//check if condition is bool type or not
		if(singleCondition.getExpr().struct != TabExtension.boolType) {
			report_error("SEMANTICKA GRESKA: Tip izraza u uslovu if, while ili foreach petlje treba biti tipa bool, a nije", singleCondition);        	
			return;
		}
		
	}
	
	
	//type of operations currently used
	// == - 0, != - 1, > - 2, >= 3, < - 4, <= - 5
	int relOp = -1;
//	// + - 0, "-" - 1
//	int addOp;
//	// * - 0, / - 1, % - 2
//	int mulOp;
	
	public void visit(RelopCondition relopCondition) {
		
		if(!relopCondition.getExpr().struct.compatibleWith(relopCondition.getExpr1().struct)) {
			//dodaj ime tipovima
			report_error("SEMANTICKA GRESKA: Izrazi u uslovu kontrole moraju biti uporedivi", relopCondition);   
		}
		
		//report_info(relOp + " and " + typeOfStructNode(relopCondition.getExpr().struct.getKind()),null);
		
		//if one of expr is class or array element, relop must be == or !=
		if((relopCondition.getExpr().struct.getKind() == Struct.Array
				|| relopCondition.getExpr1().struct.getKind() == Struct.Array
				|| relopCondition.getExpr().struct.getKind() == Struct.Class
				|| relopCondition.getExpr1().struct.getKind() == Struct.Class) && (
						relOp != 0 && relOp != 1)) {
			report_error("SEMANTICKA GRESKA: Izrazi u uslovu kontrole koje imaju polja klase ili niza moraju se porediti samo operatorima == ili !=", relopCondition);
		}
		relOp = -1;
	}
	
	public void visit(ErrorIfCondition errorIfCondition) {
		report_error("SEMANTICKA GRESKA: Izrazi u uslovu if-a nije bool tipa ", errorIfCondition);
		
	}
	
	//OPERATIONS
	public void visit(EqualOperator equalOperator) {
		relOp = 0;
	}
	
	public void visit(NotEqualOperator notEqualOperator) {
		relOp = 1;
	}
	
	public void visit(GreaterOperator greaterOperator) {
		relOp = 2;
	}
	
	public void visit(GreaterEqualOperator greaterEqualOperator) {
		relOp = 3;
	}
	
	public void visit(LessOperator lessOperator) {
		relOp = 4;
	}
	
	public void visit(LessEqualOperator lessEqualOperator) {
		relOp = 5;
	}
	
	
	
	//STATEMENTS
	public void visit(StatementBreak statementBreak) {
		if(whileLoopsNum == 0 && foreachLoopsNum == 0) {
			report_error("SEMANTICKA GRESKA: Naredba break mora biti u while ili foreach izrazu", statementBreak);
			return;
		}
	}
	
	public void visit(StatementContinue statementContinue) {
		if(whileLoopsNum == 0 && foreachLoopsNum == 0) {
			report_error("SEMANTICKA GRESKA: Naredba continue mora biti u while ili foreach izrazu", statementContinue);
			return;
		}
	}
	
	public void visit(StatementRead statementRead) {
		//check if designator is var, fld or elem
		if(statementRead.getDesignator().obj.getKind() != Obj.Var 
				&& statementRead.getDesignator().obj.getKind() != Obj.Fld
				&& statementRead.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("SEMANTICKA GRESKA: Designator " + statementRead.getDesignator().obj.getName() + " na liniji "
				+ statementRead.getLine() + " nije promelnjiva, element niza ili polje unutar objekta", statementRead);
			//return;
		}
		
		//check if designator is int, char or bool type
		if(statementRead.getDesignator().obj.getType() != Tab.intType 
				&& statementRead.getDesignator().obj.getType() != Tab.charType
				&& statementRead.getDesignator().obj.getType() != TabExtension.boolType) {
			report_error("SEMANTICKA GRESKA: Designator " + statementRead.getDesignator().obj.getName() + " na liniji "
				+ statementRead.getLine() + " nije tipa int, char ili bool", statementRead);
			//return;
		}
		
	}
	
	public void visit(StatementPrintWithoutWidth statementPrintWithoutWidth) {
		
		countPrint++;
		log.info("Prepoznata naredba print");
		
		//check if expr is int, char or bool type
		if(statementPrintWithoutWidth.getExpr().struct != Tab.intType 
				&& statementPrintWithoutWidth.getExpr().struct != Tab.charType
				&& statementPrintWithoutWidth.getExpr().struct != TabExtension.boolType) {
			report_error("SEMANTICKA GRESKA: Expr na liniji "
				+ statementPrintWithoutWidth.getLine() + " nije tipa int, char ili bool", statementPrintWithoutWidth);
			//return;
		}
		printRead = false;
	}
	
	public void visit(StatementPrintWithWidth statementPrintWithWidth) {
			
			countPrint++;
			log.info("Prepoznata naredba print");
			
			//check if expr is int, char or bool type
			if(statementPrintWithWidth.getExpr().struct != Tab.intType 
					&& statementPrintWithWidth.getExpr().struct != Tab.charType
					&& statementPrintWithWidth.getExpr().struct != TabExtension.boolType) {
				report_error("SEMANTICKA GRESKA: Expr na liniji "
					+ statementPrintWithWidth.getLine() + " nije tipa int, char ili bool", statementPrintWithWidth);
				//return;
			}
			printRead = false;
		}
	
	public void visit(PrintEmptyDummy PrintEmptyDummy) {
		printRead = true;
	}
	
	public void visit(StatementReturnExpr statementReturnExpr) {
		
		if(currMeth == null) {
			report_error("SEMANTICKA GRESKA: Return naredba ne sme postojavi van tela metode ili funkcije", statementReturnExpr);
			return;
		}
		
		if(currMeth.getType() == Tab.noType) {
			report_error("SEMANTICKA GRESKA: Return naredba ne sme postojavi u telu void metode ili funkcije", statementReturnExpr);
			return;
		}
		
		if(!currMeth.getType().equals(statementReturnExpr.getExpr().struct)) {
			report_error("SEMANTICKA GRESKA: Return naredba tipa - " + typeOfStructNode(statementReturnExpr.getExpr().struct.getKind()) + 
					" nije ista sa tipom povratne funkcije metode ili funkcije koji je " + typeOfStructNode(currMeth.getType().getKind()), statementReturnExpr);
			return;
		}
		
		returnFound = true;
	}
	
	public void visit(StatementReturnEmpty statementReturnEmpty) {
		if(currMeth.getType() != Tab.noType) {
			report_error("SEMANTICKA GRESKA: Prazan return naredba ne sme postojavi u telu metode ili funkcije koje nisu void", statementReturnEmpty);
			return;
		}
	}
	
	//Expr
	public void visit(TermNegativeExpr termNegativeExpr) {
		if(termNegativeExpr.getTerm().struct != Tab.intType) {
			report_error("SEMANTICKA GRESKA: Tip izraza koji se negira mora biti int", termNegativeExpr);
			termNegativeExpr.struct = Tab.noType;
			return;
		}else {
			//otherwise it must be int type
			termNegativeExpr.struct = Tab.intType;
		}
	}
	
	public void visit(TermPositiveExpr termPositiveExpr) {
		//add type to expr from term
		termPositiveExpr.struct = termPositiveExpr.getTerm().struct;
		
		//check if this is ActParam and if it is correct type
		if(callingMeth.size() != 0) {
			//report_info("usao u callingMeth - " + callingMeth.getName(), termPositiveExpr);
			//check if it is len, chr or ord method
			if(callingMeth.get(callingMeth.size() - 1).getName().equals("len")) {
				//type must be array
				if(termPositiveExpr.getTerm().struct.getKind() != Struct.Array) {
					report_error("SEMANTICKA GRESKA: Tip izraza u funkciji len mora biti nizovskog tipa, a ne tipa " + 
							typeOfStructNode(termPositiveExpr.getTerm().struct.getKind()), termPositiveExpr);
					termPositiveExpr.struct = Tab.noType;
					return;
				}
			}else if(callingMeth.get(callingMeth.size() - 1).getName().equals("ord")) {
				//type must be char
				if(termPositiveExpr.getTerm().struct.getKind() != Struct.Char) {
					report_error("SEMANTICKA GRESKA: Tip izraza u funkciji ord mora biti tipa char, a ne tipa " + 
							typeOfStructNode(termPositiveExpr.getTerm().struct.getKind()), termPositiveExpr);
					termPositiveExpr.struct = Tab.noType;
					return;
				}
			}else if(callingMeth.get(callingMeth.size() - 1).getName().equals("chr")) {
				//type must be char
				if(termPositiveExpr.getTerm().struct.getKind() != Struct.Int) {
					report_error("SEMANTICKA GRESKA: Tip izraza u funkciji chr mora biti tipa int, a ne tipa " + 
							typeOfStructNode(termPositiveExpr.getTerm().struct.getKind()), termPositiveExpr);
					termPositiveExpr.struct = Tab.noType;
					return;
				}
			}
			
		}
		
	}
	
	public void visit(AddExpr addExpr) {
		//is compatible
		//report_info("-------" + addExpr.getExpr().struct.getKind() + "-----------" + addExpr.getTerm().struct.getKind(), addExpr);
		if(!addExpr.getExpr().struct.compatibleWith(addExpr.getTerm().struct)) {
			report_error("SEMANTICKA GRESKA: Levi i desni sabirak nisu kompatibilni ", addExpr);
			addExpr.struct = Tab.noType;
			return;
		}
		
		//check if this is ActParam and if it is correct type
		if(currMeth != null) {
			
			
			
		}
		
		addExpr.struct = Tab.intType;
	}
	
	//TERMS AND FACTORS
	
	public void visit(Term term) {
		term.struct = term.getListOfFactors().struct;
	}
	
	public void visit(MultipleFactors multipleFactors) {
		//check if both term and factor are int type
		if(multipleFactors.getListOfFactors().struct != Tab.intType || multipleFactors.getFactor().struct != Tab.intType) {
			report_error("SEMANTICKA GRESKA: Tip svih cinilaca mora biti int", multipleFactors);
			multipleFactors.struct = Tab.noType;
			return;
		}else {
			multipleFactors.struct = Tab.intType;
		}
	}
	
	public void visit(SingleFactor singleFactor) {
    	singleFactor.struct = singleFactor.getFactor().struct;
    }
	
	public void visit(DesignatorFactor designatorFactor) {
		designatorFactor.struct = designatorFactor.getDesignator().obj.getType();
		
		//IPSIS?
	}
	
	
	public void visit(NumberConstFactor numberConstFactor) {
		numberConstFactor.struct = Tab.intType;
		
		//report_info("Detektovana upotreba celobrojne konstante " + numberConstFactor.getNumValue(), numberConstFactor);
	}
	
	public void visit(CharConstFactor charConstFactor) {
		charConstFactor.struct = Tab.charType;
		
		//report_info("Detektovana upotreba znakovne konstante " + charConstFactor.getCharValue(), charConstFactor);
	}
	
	public void visit(BooleanConstFactor booleanConstFactor) {
		booleanConstFactor.struct = TabExtension.boolType;
		
		//report_info("Detektovana upotreba logicke konstante " + booleanConstFactor.getBooleanValue(), booleanConstFactor);
	}
	
	public void visit(ExprFactor exprFactor) {
		exprFactor.struct = exprFactor.getExpr().struct;
		
		
	}
	
	public void visit(FuncCallFactorDesignator funcCallFactorDesignator) {
		
		if(funcCallFactorDesignator.getDesignator().obj.getKind() != Obj.Meth) {
    		report_error("SEMANTICKA GRESKA: " + funcCallFactorDesignator.getDesignator().obj.getName() + " ne predstavlja funkciju", funcCallFactorDesignator);        	    		
    		funcCallFactorDesignator.obj =  Tab.noObj;
    		return;
    	}
		
		callingMeth.add(funcCallFactorDesignator.getDesignator().obj);
		//report_info("FC" + funcCallFactorDesignator.getDesignator().obj.getName(), funcCallFactorDesignator);
		
		funcCallFactorDesignator.obj = funcCallFactorDesignator.getDesignator().obj;
		
	}
	
	public void visit(FuncCallFactor funcCallFactor) {
		
		//check if the method or function has a return value
		if(funcCallFactor.getFuncCallFactorDes().obj.getType() == Tab.noType) {
			report_error("SEMANTICKA GRESKA: " + funcCallFactor.getFuncCallFactorDes().obj.getName() + " nema povratnu vrednost, a nalazi se u izrazu", funcCallFactor);        	
    		
		}
		
		funcCallFactor.struct = funcCallFactor.getFuncCallFactorDes().obj.getType();
		actParsNum = 0;
		callingMeth.remove(callingMeth.size() - 1);
		
	}
	
	public void visit(NewArrayOperatorFactor newArrayOperatorFactor) {
		//check if expr is int
		//report_info(newArrayOperatorFactor.getExpr().struct.getKind() + "", null);
		if(newArrayOperatorFactor.getExpr().struct != Tab.intType) {
			report_error("SEMANTICKA GRESKA:Tip duzine niza mora biti tipa int", newArrayOperatorFactor);        	    		
			newArrayOperatorFactor.struct =  Tab.noType;
    		return;
		}else {
			newArrayOperatorFactor.struct = new Struct(Struct.Array, newArrayOperatorFactor.getType().struct);
		}
	}
	
	public void visit(NewClassWithActParsOperatorFactor newClassWithActParsOperatorFactor) {
		//UARDI ZA KRIRANJE KLASA
		if(newClassWithActParsOperatorFactor.getType().struct.getKind() != Struct.Class){
			report_error("SEMANTICKA GRESKA: Tip "+ typeOfStructNode(newClassWithActParsOperatorFactor.getType().struct.getKind()) + 
					" nije tipa Class", newClassWithActParsOperatorFactor);
			newClassWithActParsOperatorFactor.struct = Tab.noType;
			return;
		}
		newClassWithActParsOperatorFactor.struct = newClassWithActParsOperatorFactor.getType().struct;
		
		report_info("Detektovano je pravljenje objekta unutrasnje klase " + newClassWithActParsOperatorFactor.getType().getTypeName(), newClassWithActParsOperatorFactor);
		
		//remove from calling
		nameOfConstr = null;
		actParsNum = 0;
	}
	
	//DESIGNATORS
	
	
	public void visit(ClassFieldDesignator classFieldDesignator) {
		
		if(classFieldDesignator.getDesignator().obj.getType().getKind() == Struct.Class) {
			
			if(currClass != null && currClass.getType() == classFieldDesignator.getDesignator().obj.getType()) {
				Obj tmpObj = Tab.currentScope().getOuter().findSymbol(classFieldDesignator.getClassField());
				if(tmpObj == null) {
					report_error(classFieldDesignator.getClassField() + " nije polje na klase " + classFieldDesignator.getDesignator().obj.getName(), classFieldDesignator);
					classFieldDesignator.obj = Tab.noObj;
					return;
				}else {
					report_info("Pristup polju " + classFieldDesignator.getDesignator().obj.getName() + "." + classFieldDesignator.getClassField(), classFieldDesignator);	
				}
				classFieldDesignator.obj = tmpObj;
			}else {
				//prolazimo polja druge klase
				for(Obj m: classFieldDesignator.getDesignator().obj.getType().getMembers()) {
					if(m.getName().equals(classFieldDesignator.getClassField())) {
						report_info("Pristup polju " + classFieldDesignator.getDesignator().obj.getName() + "." + classFieldDesignator.getClassField() + " tipa " + m.getKind(), classFieldDesignator);
						classFieldDesignator.obj = m;
						return;
					}
				}
			}
			
			
		}else {
			report_error(classFieldDesignator.getDesignator().obj.getName() + " ne predstavlja referencu na klasu, vec je tipa " + typeOfStructNode(classFieldDesignator.getDesignator().obj.getType().getKind()), classFieldDesignator);
			classFieldDesignator.obj = Tab.noObj;
		}
	}
	
	public void visit(ArrayDesignator arrayDesignator) {
		if(arrayDesignator.getArrayDesig().getDesignator().obj.getType().getKind() != Struct.Array) {
			arrayDesignator.obj = Tab.noObj;
			report_error("SEMANTICKA GRESKA: " + arrayDesignator.getArrayDesig().getDesignator().obj.getName() + " mora bit niz nekog tipa", arrayDesignator);
			return;
		}
		
		if(arrayDesignator.getExpr().struct != Tab.intType) {
			arrayDesignator.obj = Tab.noObj;
			report_error("SEMANTICKA GRESKA: Indeks niza mora bit tipa int", arrayDesignator);
			return;
		}
		
		arrayDesignator.obj = new Obj(Obj.Elem, arrayDesignator.getArrayDesig().getDesignator().obj.getName(),
				arrayDesignator.getArrayDesig().getDesignator().obj.getType().getElemType());
		
		report_info("Detektovan je pristup elemntu niza " + arrayDesignator.getArrayDesig().getDesignator().obj.getName(), arrayDesignator);
		
	}
	
	
	
	
	public void visit(SimpleDesignator simpleDesignator) {
		
		//find if variable is declared
		Obj varObj = Tab.find(simpleDesignator.getName());
		//report_info(varObj.getKind() + "verify", null);
//		if(callingMeth == null)
//			report_info("nullic", null);
//		else
//			report_info(callingMeth.getName() + "nullic", null);
		if((varObj == Tab.noObj || (callingMeth.size() != 0 && varObj.getKind() != Obj.Var && varObj.getKind() != Obj.Con && varObj.getKind() != Obj.Meth))
				&& !simpleDesignator.getName().equals("this")) {
			report_error("SEMANTICKA GRESKA: Nije deklarisana promenljiva sa imenom " + simpleDesignator.getName() , simpleDesignator);
			simpleDesignator.obj = Tab.noObj;
			return;
		}
		
		simpleDesignator.obj = varObj;
		
		if(simpleDesignator.obj.getKind() == Obj.Con) {
			report_info("Detektovan je pristup simbolickoj konstantni " + simpleDesignator.obj.getName(), simpleDesignator);
		}
	}
	
	
	//CLASSES
	
	
	public void visit(NameOfClass nameOfClass) {
		
		//check if there is class with the same name
		if(Tab.find(nameOfClass.getClassName()) != Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Vec je deklarisana klasa sa imenom " + nameOfClass.getClassName(), nameOfClass);
			nameOfClass.obj = Tab.noObj;
			Tab.openScope();
			currClass = Tab.noObj;
			return;
		}
		
		//insert into symbol table
		nameOfClass.obj = Tab.insert(Obj.Type, nameOfClass.getClassName(), new Struct(Struct.Class));
		currClass = nameOfClass.obj;
		report_info("Kreirana je klasa " + nameOfClass.getClassName(), nameOfClass);
		
		//new scope
		Tab.openScope();
		
		//add vtf address
		Tab.insert(Obj.Fld, "vtf_adr", Tab.intType);
		
		
		//add to hashMap
		//allClassConstr.put(nameOfClass.getClassName(), new ArrayList<>());
	}
	
	public void visit(ClassIsExtended classIsExtended) {
		
		//check if there is class with the name of extended class
		if(Tab.find(classIsExtended.getType().getTypeName()) == Tab.noObj) {
			report_error("SEMANTICKA GRESKA: Nadklasa sa imenom " + classIsExtended.getType().getTypeName() + " ne postoji", classIsExtended);
			return;
		}
		
		Obj classNode = Tab.find(classIsExtended.getType().getTypeName());
		
		if(classNode != Tab.noObj && classNode.getType().getKind() != Struct.Class) {
			report_error("SEMANTICKA GRESKA: Ime " + classIsExtended.getType().getTypeName() + " nije korisnicki definisana klasa", classIsExtended);
			return;
		}
		
		//add SUPERCLASS
		currClass.getType().setElementType(classNode.getType());
		superClass = classNode;
		
		
		//add field from superclass
		for(Obj m: classNode.getType().getMembers()) {
			if(m.getKind() == Obj.Fld && !"vtf_adr".equals(m.getName())) {
				Tab.insert(Obj.Fld, m.getName(), m.getType());
			}
		}
		
		
		//copy method from superclass
//		if(superClass != null) {
//			for(Obj m: superClass.getType().getMembers()) {
//				if(m.getKind() == Obj.Meth && !m.getName().contains("#")) {
//					Tab.currentScope().addToLocals(m);
//				}
//			}
//		}
	}
	
	public void visit(ClassIsNotExtended classIsNotExtended) {
		superClass = Tab.noObj;
		currClass.getType().setElementType(Tab.noType);
	}
	
	public void visit(ClassDeclaration classDeclaration) {
		//report_info("Broj konstruktora: " + allClassConstr.size(), null);
		
		if(classDeclaration.getClassExtended() instanceof ClassIsExtended) {
			if(superClass != null) {
				for(Obj m: superClass.getType().getMembers()) {
					Obj tmpMeth = Tab.currentScope().findSymbol(m.getName());
					if(m.getKind() == Obj.Meth && !m.getName().contains("#") && tmpMeth == null) {
						Tab.currentScope().addToLocals(m);
					}
				}
			}
		}
		
		//dodajemo podarzumevani konstruktor
		if(allClassConstr.size() == 0) {
			Obj tmpConstObj = Tab.insert(Obj.Meth, classDeclaration.getClassName().obj.getName() + "~", Tab.noType);
			Tab.openScope();
			
			//insert this into function
			Tab.insert(Obj.Var, "this", currClass.getType());
			tmpConstObj.setLevel(1);
			Tab.chainLocalSymbols(tmpConstObj);
			Tab.closeScope();
		}
		Tab.chainLocalSymbols(currClass.getType());
		Tab.closeScope();
		currClass = null;
		superClass = null;
		allClassConstr.clear();
	}
	
	public void visit(ClassVariable classVariable) {
		
		//check if the name is already in symbol table in current scope
		Obj classVarNode = Tab.currentScope().findSymbol(classVariable.getClassVarName());
		if(classVarNode != null) {
			report_error("SEMANTICKA GRESKA: Ime polja " + classVariable.getClassVarName() + " je ranije vec deklarisano", classVariable);
			return;
		}
		
		//add to symbol table
		if(isArr) {
			Tab.insert(Obj.Fld, classVariable.getClassVarName(), new Struct(Struct.Array, currType));
			isArr = false;
		}else {
			Tab.insert(Obj.Fld, classVariable.getClassVarName(), currType);
		}
		
	}
	
	public void visit(ConstructorDeclaration constructorDeclaration) {
				
		//print
		for (List<Struct> e : allClassConstr) {
			//report_info("CONSTRUCTOR: " + currClass.getName(), null);
			for(Struct s: e) {
				//report_info("" + typeOfStructNode(s.getKind()), null);
			}
		}
		
		//check if there is some construcotr with the same formal parameters
		for (List<Struct> e : allClassConstr) {
			if(e.size() == constrPars.size()) {
				int ind = 0;
				for(int i = 0; i < constrPars.size(); i++) {
					//report_info("STRUCT CURRENT: " + e.getValue().get(i), null);
					//report_info("STRUCT PARENT: " + constrPars.get(i).struct, null);
					if(e.get(i).equals(constrPars.get(i).struct)) {
						ind++;
					}
				}
				//report_info("Sa kojim uporedjujem konst ima paramatera: " + ind, null);
				//report_info("Trenutni konst ima paramatera: " + constrPars.size(), null);
				if(ind == constrPars.size()) {
					//we have found constructor with the same formal parameters
					report_error("SEMANTICKA GRESKA: Konstruktor sa formalnim parametrima vec postoji", constructorDeclaration);
					isConstr = false;
					currMeth.setLevel(constrPars.size() + 1);
					Tab.chainLocalSymbols(currMeth);
					
					Tab.closeScope();
					return;
				}
			}
		}
		
		//add number of formal parameters
		List<Struct> tmpList = new ArrayList<Struct>();
		for(CustomPair cp: constrPars) {
			//Tab.insert(Obj.Var, cp.name, cp.struct);
			tmpList.add(cp.struct);
		}
		currMeth.setLevel(constrPars.size() + 1);
		Tab.chainLocalSymbols(currMeth);
		
		Tab.closeScope();
		
		allMethods.put(currMeth.getName(), tmpList);
		currMeth = null;
		isConstr = false;
		numOfFormPars = 0;
		allClassConstr.add(tmpList);
		//report_info("NAPRAVLJEN KONST SA FORM PARS" + constrPars.size(), null);
		constrPars.clear();
		
	}
	
	public void visit(ConstructorDeclarationName constructorDeclarationName) {
		
		//check if the constructor name is different than the name of the class
		if(!constructorDeclarationName.getConstructorName().equals(currClass.getName())) {
			report_error("SEMANTICKA GRESKA: Ime konstruktor " + constructorDeclarationName.getConstructorName() + 
					" ne poklapa se sa imenom same klase " + currClass.getName(), constructorDeclarationName);
			isConstr = true;
			currMeth = Tab.noObj;
			Tab.openScope();
			//return;
		}
		
		//report_info("Konst broj: " + allClassConstr.size(), null);
		currMeth = Tab.insert(Obj.Meth, constructorDeclarationName.getConstructorName() + "#" + allClassConstr.size(), Tab.noType);
		constructorDeclarationName.obj = currMeth;
		Tab.openScope();
		
		//insert this into function
		Tab.insert(Obj.Var, "this", currClass.getType());
		
		isConstr = true;
	}
	
	
	public boolean passed() {
		return !errorDetected;
	}

	
}

