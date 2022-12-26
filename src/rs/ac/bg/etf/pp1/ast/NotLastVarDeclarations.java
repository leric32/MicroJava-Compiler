// generated with ast extension for cup
// version 0.8
// 25/11/2022 22:27:57


package rs.ac.bg.etf.pp1.ast;

public class NotLastVarDeclarations extends VarDeclNotLast {

    private String varName;
    private VarDeclBrackets VarDeclBrackets;

    public NotLastVarDeclarations (String varName, VarDeclBrackets VarDeclBrackets) {
        this.varName=varName;
        this.VarDeclBrackets=VarDeclBrackets;
        if(VarDeclBrackets!=null) VarDeclBrackets.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public VarDeclBrackets getVarDeclBrackets() {
        return VarDeclBrackets;
    }

    public void setVarDeclBrackets(VarDeclBrackets VarDeclBrackets) {
        this.VarDeclBrackets=VarDeclBrackets;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclBrackets!=null) VarDeclBrackets.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclBrackets!=null) VarDeclBrackets.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclBrackets!=null) VarDeclBrackets.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NotLastVarDeclarations(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(VarDeclBrackets!=null)
            buffer.append(VarDeclBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NotLastVarDeclarations]");
        return buffer.toString();
    }
}
