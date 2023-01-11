// generated with ast extension for cup
// version 0.8
// 8/0/2023 20:21:12


package rs.ac.bg.etf.pp1.ast;

public class StatementForeach extends Statement {

    private Designator Designator;
    private ForEachLoop ForEachLoop;
    private String feVar;
    private Statement Statement;

    public StatementForeach (Designator Designator, ForEachLoop ForEachLoop, String feVar, Statement Statement) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ForEachLoop=ForEachLoop;
        if(ForEachLoop!=null) ForEachLoop.setParent(this);
        this.feVar=feVar;
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ForEachLoop getForEachLoop() {
        return ForEachLoop;
    }

    public void setForEachLoop(ForEachLoop ForEachLoop) {
        this.ForEachLoop=ForEachLoop;
    }

    public String getFeVar() {
        return feVar;
    }

    public void setFeVar(String feVar) {
        this.feVar=feVar;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ForEachLoop!=null) ForEachLoop.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ForEachLoop!=null) ForEachLoop.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ForEachLoop!=null) ForEachLoop.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementForeach(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEachLoop!=null)
            buffer.append(ForEachLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+feVar);
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementForeach]");
        return buffer.toString();
    }
}
