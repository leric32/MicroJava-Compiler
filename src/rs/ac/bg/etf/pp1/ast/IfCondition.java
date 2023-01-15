// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:47


package rs.ac.bg.etf.pp1.ast;

public class IfCondition extends IfCond {

    private IfOnly IfOnly;
    private Condition Condition;
    private FixupIf1 FixupIf1;

    public IfCondition (IfOnly IfOnly, Condition Condition, FixupIf1 FixupIf1) {
        this.IfOnly=IfOnly;
        if(IfOnly!=null) IfOnly.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.FixupIf1=FixupIf1;
        if(FixupIf1!=null) FixupIf1.setParent(this);
    }

    public IfOnly getIfOnly() {
        return IfOnly;
    }

    public void setIfOnly(IfOnly IfOnly) {
        this.IfOnly=IfOnly;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
    }

    public FixupIf1 getFixupIf1() {
        return FixupIf1;
    }

    public void setFixupIf1(FixupIf1 FixupIf1) {
        this.FixupIf1=FixupIf1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IfOnly!=null) IfOnly.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(FixupIf1!=null) FixupIf1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IfOnly!=null) IfOnly.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(FixupIf1!=null) FixupIf1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IfOnly!=null) IfOnly.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(FixupIf1!=null) FixupIf1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfCondition(\n");

        if(IfOnly!=null)
            buffer.append(IfOnly.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FixupIf1!=null)
            buffer.append(FixupIf1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfCondition]");
        return buffer.toString();
    }
}
