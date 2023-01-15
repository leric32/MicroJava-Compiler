// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:47


package rs.ac.bg.etf.pp1.ast;

public class ForEachIdent implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String feVar;

    public ForEachIdent (String feVar) {
        this.feVar=feVar;
    }

    public String getFeVar() {
        return feVar;
    }

    public void setFeVar(String feVar) {
        this.feVar=feVar;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("ForEachIdent(\n");

        buffer.append(" "+tab+feVar);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForEachIdent]");
        return buffer.toString();
    }
}
