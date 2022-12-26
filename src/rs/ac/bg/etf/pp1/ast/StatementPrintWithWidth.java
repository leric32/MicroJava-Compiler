// generated with ast extension for cup
// version 0.8
// 25/11/2022 22:27:57


package rs.ac.bg.etf.pp1.ast;

public class StatementPrintWithWidth extends Statement {

    private Expr Expr;
    private Integer width;

    public StatementPrintWithWidth (Expr Expr, Integer width) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.width=width;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width=width;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrintWithWidth(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+width);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrintWithWidth]");
        return buffer.toString();
    }
}
