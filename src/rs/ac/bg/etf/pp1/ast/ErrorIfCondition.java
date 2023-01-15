// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:47


package rs.ac.bg.etf.pp1.ast;

public class ErrorIfCondition extends IfCond {

    private IfOnly IfOnly;

    public ErrorIfCondition (IfOnly IfOnly) {
        this.IfOnly=IfOnly;
        if(IfOnly!=null) IfOnly.setParent(this);
    }

    public IfOnly getIfOnly() {
        return IfOnly;
    }

    public void setIfOnly(IfOnly IfOnly) {
        this.IfOnly=IfOnly;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfOnly!=null) IfOnly.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfOnly!=null) IfOnly.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfOnly!=null) IfOnly.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ErrorIfCondition(\n");

        if(IfOnly!=null)
            buffer.append(IfOnly.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ErrorIfCondition]");
        return buffer.toString();
    }
}
