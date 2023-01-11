// generated with ast extension for cup
// version 0.8
// 8/0/2023 20:21:12


package rs.ac.bg.etf.pp1.ast;

public class ArrayDesignator extends Designator {

    private ArrayDesig ArrayDesig;
    private Expr Expr;

    public ArrayDesignator (ArrayDesig ArrayDesig, Expr Expr) {
        this.ArrayDesig=ArrayDesig;
        if(ArrayDesig!=null) ArrayDesig.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public ArrayDesig getArrayDesig() {
        return ArrayDesig;
    }

    public void setArrayDesig(ArrayDesig ArrayDesig) {
        this.ArrayDesig=ArrayDesig;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayDesig!=null) ArrayDesig.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayDesig!=null) ArrayDesig.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayDesig!=null) ArrayDesig.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ArrayDesignator(\n");

        if(ArrayDesig!=null)
            buffer.append(ArrayDesig.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ArrayDesignator]");
        return buffer.toString();
    }
}
