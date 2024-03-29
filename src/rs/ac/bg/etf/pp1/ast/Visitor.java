// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(ListOfCharDec ListOfCharDec);
    public void visit(Mulop Mulop);
    public void visit(MethodDecl MethodDecl);
    public void visit(CheckForeach2 CheckForeach2);
    public void visit(ConstructorDecl ConstructorDecl);
    public void visit(Relop Relop);
    public void visit(ListOfClassVarDec ListOfClassVarDec);
    public void visit(VarDeclBrackets VarDeclBrackets);
    public void visit(DesignatorOrEpsilon DesignatorOrEpsilon);
    public void visit(FunctionCall FunctionCall);
    public void visit(StatementList StatementList);
    public void visit(ClassName ClassName);
    public void visit(ClassVarDecl ClassVarDecl);
    public void visit(ListOfFactors ListOfFactors);
    public void visit(Addop Addop);
    public void visit(PrintEmpty PrintEmpty);
    public void visit(ListOfVarDec ListOfVarDec);
    public void visit(Factor Factor);
    public void visit(FixupIf2 FixupIf2);
    public void visit(CondTerm CondTerm);
    public void visit(FixupIf1 FixupIf1);
    public void visit(VarDeclNotLast VarDeclNotLast);
    public void visit(Designator Designator);
    public void visit(ConstAssignment ConstAssignment);
    public void visit(ClassBody ClassBody);
    public void visit(FormParsList FormParsList);
    public void visit(Condition Condition);
    public void visit(IfCond IfCond);
    public void visit(ConstructorDeclName ConstructorDeclName);
    public void visit(FixupWhile1 FixupWhile1);
    public void visit(ClassVar ClassVar);
    public void visit(WhileLoop WhileLoop);
    public void visit(ActualParamList ActualParamList);
    public void visit(VarDeclLast VarDeclLast);
    public void visit(ConstructorDeclList ConstructorDeclList);
    public void visit(VarDeclList VarDeclList);
    public void visit(Expr Expr);
    public void visit(ActPars ActPars);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(DesignatorList DesignatorList);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(OneFormalParam OneFormalParam);
    public void visit(FuncCallFactorDes FuncCallFactorDes);
    public void visit(Statement Statement);
    public void visit(VarDecl VarDecl);
    public void visit(ConstructorVarDecl ConstructorVarDecl);
    public void visit(ClassDecl ClassDecl);
    public void visit(ConstDecl ConstDecl);
    public void visit(ClassExtended ClassExtended);
    public void visit(CondFact CondFact);
    public void visit(MethodVarDeclList MethodVarDeclList);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(IfOnly IfOnly);
    public void visit(FormPars FormPars);
    public void visit(ClassMethodDecl ClassMethodDecl);
    public void visit(CondJump CondJump);
    public void visit(ListOfClassVar ListOfClassVar);
    public void visit(ModuoOperator ModuoOperator);
    public void visit(DivideOperator DivideOperator);
    public void visit(MultiplyOperator MultiplyOperator);
    public void visit(MinusOperator MinusOperator);
    public void visit(PlusOperator PlusOperator);
    public void visit(LessEqualOperator LessEqualOperator);
    public void visit(LessOperator LessOperator);
    public void visit(GreaterEqualOperator GreaterEqualOperator);
    public void visit(GreaterOperator GreaterOperator);
    public void visit(NotEqualOperator NotEqualOperator);
    public void visit(EqualOperator EqualOperator);
    public void visit(FuncCallFactorDesignator FuncCallFactorDesignator);
    public void visit(NewClassWithActParsOperatorFactor NewClassWithActParsOperatorFactor);
    public void visit(NewArrayOperatorFactor NewArrayOperatorFactor);
    public void visit(FuncCallFactor FuncCallFactor);
    public void visit(ExprFactor ExprFactor);
    public void visit(BooleanConstFactor BooleanConstFactor);
    public void visit(CharConstFactor CharConstFactor);
    public void visit(NumberConstFactor NumberConstFactor);
    public void visit(DesignatorFactor DesignatorFactor);
    public void visit(SingleFactor SingleFactor);
    public void visit(MultipleFactors MultipleFactors);
    public void visit(Term Term);
    public void visit(TermNegativeExpr TermNegativeExpr);
    public void visit(TermPositiveExpr TermPositiveExpr);
    public void visit(AddExpr AddExpr);
    public void visit(ArrayDesig ArrayDesig);
    public void visit(SimpleDesignator SimpleDesignator);
    public void visit(ArrayDesignator ArrayDesignator);
    public void visit(ClassFieldDesignator ClassFieldDesignator);
    public void visit(Epsilon Epsilon);
    public void visit(DesignArrAssignment DesignArrAssignment);
    public void visit(SingleOrEndOfListDesignator SingleOrEndOfListDesignator);
    public void visit(ListOfDesignator ListOfDesignator);
    public void visit(FunctionCallName FunctionCallName);
    public void visit(DesignatorArrayAssignment DesignatorArrayAssignment);
    public void visit(DesignatorDec DesignatorDec);
    public void visit(DesignatorInc DesignatorInc);
    public void visit(DesignatorFuncCall DesignatorFuncCall);
    public void visit(ErrorDesignatorAssignment ErrorDesignatorAssignment);
    public void visit(DesignatorAssignment DesignatorAssignment);
    public void visit(SingleCondition SingleCondition);
    public void visit(RelopCondition RelopCondition);
    public void visit(ConditionalJump ConditionalJump);
    public void visit(ConditionsWithoutAnd ConditionsWithoutAnd);
    public void visit(ConditionsWithAnd ConditionsWithAnd);
    public void visit(ConditionsWithoutOr ConditionsWithoutOr);
    public void visit(ConditionsWithOr ConditionsWithOr);
    public void visit(ActualParam ActualParam);
    public void visit(ActualParams ActualParams);
    public void visit(NoActuals NoActuals);
    public void visit(Actuals Actuals);
    public void visit(ForEachIdent ForEachIdent);
    public void visit(CheckForeachTwo CheckForeachTwo);
    public void visit(ForEachLoop ForEachLoop);
    public void visit(WhileLoopParen WhileLoopParen);
    public void visit(FirstFixupForWhile FirstFixupForWhile);
    public void visit(SecondFixupForIf SecondFixupForIf);
    public void visit(FirstFixupForIf FirstFixupForIf);
    public void visit(OnlyIf OnlyIf);
    public void visit(ErrorIfCondition ErrorIfCondition);
    public void visit(IfCondition IfCondition);
    public void visit(PrintEmptyDummy PrintEmptyDummy);
    public void visit(StatementBlock StatementBlock);
    public void visit(StatementForeach StatementForeach);
    public void visit(StatementPrintWithWidth StatementPrintWithWidth);
    public void visit(StatementPrintWithoutWidth StatementPrintWithoutWidth);
    public void visit(StatementRead StatementRead);
    public void visit(StatementReturnExpr StatementReturnExpr);
    public void visit(StatementReturnEmpty StatementReturnEmpty);
    public void visit(StatementContinue StatementContinue);
    public void visit(StatementBreak StatementBreak);
    public void visit(StatementWhile StatementWhile);
    public void visit(StatementIfElse StatementIfElse);
    public void visit(StatementIf StatementIf);
    public void visit(StatementDesignator StatementDesignator);
    public void visit(NoStatements NoStatements);
    public void visit(Statements Statements);
    public void visit(MethodWithoutVariables MethodWithoutVariables);
    public void visit(MethodVariables MethodVariables);
    public void visit(VoidMethodTypeName VoidMethodTypeName);
    public void visit(ReturnMethodTypeName ReturnMethodTypeName);
    public void visit(MethodDeclaration MethodDeclaration);
    public void visit(NoMethodDeclarations NoMethodDeclarations);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(SingleOrEndOfListMethod SingleOrEndOfListMethod);
    public void visit(ListOfMethodsInClass ListOfMethodsInClass);
    public void visit(ConstructorWithoutVar ConstructorWithoutVar);
    public void visit(ConstructorListOfVar ConstructorListOfVar);
    public void visit(SingleFormalParameter SingleFormalParameter);
    public void visit(ErrorSingleOrEndOfListParameter ErrorSingleOrEndOfListParameter);
    public void visit(ErrorListOfParameters ErrorListOfParameters);
    public void visit(SingleOrEndOfListParameter SingleOrEndOfListParameter);
    public void visit(ListOfParameters ListOfParameters);
    public void visit(NoFormalParameters NoFormalParameters);
    public void visit(FormalParameters FormalParameters);
    public void visit(ConstructorDeclarationName ConstructorDeclarationName);
    public void visit(ConstructorDeclaration ConstructorDeclaration);
    public void visit(SingleOrLastConstructorDeclaration SingleOrLastConstructorDeclaration);
    public void visit(ListOfConstructorDeclarations ListOfConstructorDeclarations);
    public void visit(ClassVariable ClassVariable);
    public void visit(ClassVarDecLast ClassVarDecLast);
    public void visit(ClassVarDecNotLast ClassVarDecNotLast);
    public void visit(ErrorClassVarDeclaration ErrorClassVarDeclaration);
    public void visit(ClassVarDeclaration ClassVarDeclaration);
    public void visit(NoClassVariables NoClassVariables);
    public void visit(ClassVariables ClassVariables);
    public void visit(ClassBodyNoMethodWithEmptyBrackets ClassBodyNoMethodWithEmptyBrackets);
    public void visit(ClassBodyWithMethodAndConstructor ClassBodyWithMethodAndConstructor);
    public void visit(ClassBodyWithMethodNoConstructor ClassBodyWithMethodNoConstructor);
    public void visit(ClassBodyNoMethodWithConstructor ClassBodyNoMethodWithConstructor);
    public void visit(ClassBodyNoMethod ClassBodyNoMethod);
    public void visit(ErrorClassExtended ErrorClassExtended);
    public void visit(ClassIsNotExtended ClassIsNotExtended);
    public void visit(ClassIsExtended ClassIsExtended);
    public void visit(NameOfClass NameOfClass);
    public void visit(ClassDeclaration ClassDeclaration);
    public void visit(EndOfListOfCharDeclarations EndOfListOfCharDeclarations);
    public void visit(ListOfCharDeclarations ListOfCharDeclarations);
    public void visit(ConstantValueChar ConstantValueChar);
    public void visit(ConstantValueNumber ConstantValueNumber);
    public void visit(ConstantValueBoolean ConstantValueBoolean);
    public void visit(ConstDeclaration ConstDeclaration);
    public void visit(ErrorVarDeclLast ErrorVarDeclLast);
    public void visit(LastVarDeclarations LastVarDeclarations);
    public void visit(VarDeclarationOfNotArray VarDeclarationOfNotArray);
    public void visit(VarDeclarationOfArray VarDeclarationOfArray);
    public void visit(ErrorVarDeclNotLast ErrorVarDeclNotLast);
    public void visit(NotLastVarDeclarations NotLastVarDeclarations);
    public void visit(VarDeclarationLast VarDeclarationLast);
    public void visit(VarDeclarationNotLast VarDeclarationNotLast);
    public void visit(Type Type);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(ClassDeclarations ClassDeclarations);
    public void visit(ConstDeclarations ConstDeclarations);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}
