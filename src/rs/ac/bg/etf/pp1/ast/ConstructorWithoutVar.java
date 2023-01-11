// generated with ast extension for cup
// version 0.8
// 8/0/2023 20:21:12


package rs.ac.bg.etf.pp1.ast;

public class ConstructorWithoutVar extends ConstructorVarDecl {

    public ConstructorWithoutVar () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorWithoutVar(\n");

        buffer.append(tab);
        buffer.append(") [ConstructorWithoutVar]");
        return buffer.toString();
    }
}
