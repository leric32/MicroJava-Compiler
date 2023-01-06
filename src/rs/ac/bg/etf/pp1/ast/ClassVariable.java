// generated with ast extension for cup
// version 0.8
// 4/0/2023 0:9:16


package rs.ac.bg.etf.pp1.ast;

public class ClassVariable extends ClassVar {

    private String classVarName;
    private VarDeclBrackets VarDeclBrackets;

    public ClassVariable (String classVarName, VarDeclBrackets VarDeclBrackets) {
        this.classVarName=classVarName;
        this.VarDeclBrackets=VarDeclBrackets;
        if(VarDeclBrackets!=null) VarDeclBrackets.setParent(this);
    }

    public String getClassVarName() {
        return classVarName;
    }

    public void setClassVarName(String classVarName) {
        this.classVarName=classVarName;
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
        buffer.append("ClassVariable(\n");

        buffer.append(" "+tab+classVarName);
        buffer.append("\n");

        if(VarDeclBrackets!=null)
            buffer.append(VarDeclBrackets.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVariable]");
        return buffer.toString();
    }
}
