// generated with ast extension for cup
// version 0.8
// 4/0/2023 0:9:16


package rs.ac.bg.etf.pp1.ast;

public class ListOfDesignator extends DesignatorList {

    private DesignatorList DesignatorList;
    private DesignatorOrEpsilon DesignatorOrEpsilon;

    public ListOfDesignator (DesignatorList DesignatorList, DesignatorOrEpsilon DesignatorOrEpsilon) {
        this.DesignatorList=DesignatorList;
        if(DesignatorList!=null) DesignatorList.setParent(this);
        this.DesignatorOrEpsilon=DesignatorOrEpsilon;
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.setParent(this);
    }

    public DesignatorList getDesignatorList() {
        return DesignatorList;
    }

    public void setDesignatorList(DesignatorList DesignatorList) {
        this.DesignatorList=DesignatorList;
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
        if(DesignatorList!=null) DesignatorList.accept(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorList!=null) DesignatorList.traverseTopDown(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorList!=null) DesignatorList.traverseBottomUp(visitor);
        if(DesignatorOrEpsilon!=null) DesignatorOrEpsilon.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListOfDesignator(\n");

        if(DesignatorList!=null)
            buffer.append(DesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOrEpsilon!=null)
            buffer.append(DesignatorOrEpsilon.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListOfDesignator]");
        return buffer.toString();
    }
}
