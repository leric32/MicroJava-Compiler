// generated with ast extension for cup
// version 0.8
// 8/0/2023 20:21:12


package rs.ac.bg.etf.pp1.ast;

public class CharConstFactor extends Factor {

    private Character charValue;

    public CharConstFactor (Character charValue) {
        this.charValue=charValue;
    }

    public Character getCharValue() {
        return charValue;
    }

    public void setCharValue(Character charValue) {
        this.charValue=charValue;
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
        buffer.append("CharConstFactor(\n");

        buffer.append(" "+tab+charValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CharConstFactor]");
        return buffer.toString();
    }
}
