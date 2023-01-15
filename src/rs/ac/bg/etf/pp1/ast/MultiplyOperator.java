// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:47


package rs.ac.bg.etf.pp1.ast;

public class MultiplyOperator extends Mulop {

    public MultiplyOperator () {
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
        buffer.append("MultiplyOperator(\n");

        buffer.append(tab);
        buffer.append(") [MultiplyOperator]");
        return buffer.toString();
    }
}
