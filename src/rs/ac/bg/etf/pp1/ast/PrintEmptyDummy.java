// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class PrintEmptyDummy extends PrintEmpty {

    public PrintEmptyDummy () {
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
        buffer.append("PrintEmptyDummy(\n");

        buffer.append(tab);
        buffer.append(") [PrintEmptyDummy]");
        return buffer.toString();
    }
}
