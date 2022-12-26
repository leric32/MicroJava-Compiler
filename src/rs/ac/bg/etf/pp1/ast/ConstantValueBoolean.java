// generated with ast extension for cup
// version 0.8
// 25/11/2022 22:27:57


package rs.ac.bg.etf.pp1.ast;

public class ConstantValueBoolean extends ConstAssignment {

    private String constName;
    private Boolean booleanConstValue;

    public ConstantValueBoolean (String constName, Boolean booleanConstValue) {
        this.constName=constName;
        this.booleanConstValue=booleanConstValue;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Boolean getBooleanConstValue() {
        return booleanConstValue;
    }

    public void setBooleanConstValue(Boolean booleanConstValue) {
        this.booleanConstValue=booleanConstValue;
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
        buffer.append("ConstantValueBoolean(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+booleanConstValue);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstantValueBoolean]");
        return buffer.toString();
    }
}
