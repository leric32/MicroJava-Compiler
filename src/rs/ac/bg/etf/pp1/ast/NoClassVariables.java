// generated with ast extension for cup
// version 0.8
// 25/11/2022 22:27:57


package rs.ac.bg.etf.pp1.ast;

public class NoClassVariables extends ListOfClassVar {

    public NoClassVariables () {
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
        buffer.append("NoClassVariables(\n");

        buffer.append(tab);
        buffer.append(") [NoClassVariables]");
        return buffer.toString();
    }
}
