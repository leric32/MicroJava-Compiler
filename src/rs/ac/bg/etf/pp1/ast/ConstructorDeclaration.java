// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class ConstructorDeclaration extends ConstructorDecl {

    private ConstructorDeclName ConstructorDeclName;
    private FormPars FormPars;
    private ConstructorVarDecl ConstructorVarDecl;
    private StatementList StatementList;

    public ConstructorDeclaration (ConstructorDeclName ConstructorDeclName, FormPars FormPars, ConstructorVarDecl ConstructorVarDecl, StatementList StatementList) {
        this.ConstructorDeclName=ConstructorDeclName;
        if(ConstructorDeclName!=null) ConstructorDeclName.setParent(this);
        this.FormPars=FormPars;
        if(FormPars!=null) FormPars.setParent(this);
        this.ConstructorVarDecl=ConstructorVarDecl;
        if(ConstructorVarDecl!=null) ConstructorVarDecl.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public ConstructorDeclName getConstructorDeclName() {
        return ConstructorDeclName;
    }

    public void setConstructorDeclName(ConstructorDeclName ConstructorDeclName) {
        this.ConstructorDeclName=ConstructorDeclName;
    }

    public FormPars getFormPars() {
        return FormPars;
    }

    public void setFormPars(FormPars FormPars) {
        this.FormPars=FormPars;
    }

    public ConstructorVarDecl getConstructorVarDecl() {
        return ConstructorVarDecl;
    }

    public void setConstructorVarDecl(ConstructorVarDecl ConstructorVarDecl) {
        this.ConstructorVarDecl=ConstructorVarDecl;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstructorDeclName!=null) ConstructorDeclName.accept(visitor);
        if(FormPars!=null) FormPars.accept(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstructorDeclName!=null) ConstructorDeclName.traverseTopDown(visitor);
        if(FormPars!=null) FormPars.traverseTopDown(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstructorDeclName!=null) ConstructorDeclName.traverseBottomUp(visitor);
        if(FormPars!=null) FormPars.traverseBottomUp(visitor);
        if(ConstructorVarDecl!=null) ConstructorVarDecl.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorDeclaration(\n");

        if(ConstructorDeclName!=null)
            buffer.append(ConstructorDeclName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormPars!=null)
            buffer.append(FormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstructorVarDecl!=null)
            buffer.append(ConstructorVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorDeclaration]");
        return buffer.toString();
    }
}
