// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class SingleOrEndOfListDesignator extends DesignatorList {

    private DesignatorOrEpsilon DesignatorOrEpsilon;

    public SingleOrEndOfListDesignator (DesignatorOrEpsilon DesignatorOrEpsilon) {
        this.DesignatorOrEpsilon=DesignatorOrEpsilon;
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.setParent(this);
    }

    public DesignatorOrEpsilon getDesignatorOrEpsilon() {
        return DesignatorOrEpsilon;
    }

    public void setDesignatorOrEpsilon(DesignatorOrEpsilon DesignatorOrEpsilon) {
        this.DesignatorOrEpsilon=DesignatorOrEpsilon;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleOrEndOfListDesignator(\n");

        if(DesignatorOrEpsilon!=null)
            buffer.append(DesignatorOrEpsilon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleOrEndOfListDesignator]");
        return buffer.toString();
    }
}
