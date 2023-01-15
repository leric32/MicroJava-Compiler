// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:46


package rs.ac.bg.etf.pp1.ast;

public class StatementPrintWithoutWidth extends Statement {

    private PrintEmpty PrintEmpty;
    private Expr Expr;

    public StatementPrintWithoutWidth (PrintEmpty PrintEmpty, Expr Expr) {
        this.PrintEmpty=PrintEmpty;
        if(PrintEmpty!=null) PrintEmpty.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public PrintEmpty getPrintEmpty() {
        return PrintEmpty;
    }

    public void setPrintEmpty(PrintEmpty PrintEmpty) {
        this.PrintEmpty=PrintEmpty;
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
        if(PrintEmpty!=null) PrintEmpty.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(PrintEmpty!=null) PrintEmpty.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(PrintEmpty!=null) PrintEmpty.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementPrintWithoutWidth(\n");

        if(PrintEmpty!=null)
            buffer.append(PrintEmpty.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementPrintWithoutWidth]");
        return buffer.toString();
    }
}
