// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class StatementWhile extends Statement {

    private WhileLoop WhileLoop;
    private FixupWhile1 FixupWhile1;
    private Condition Condition;
    private Statement Statement;

    public StatementWhile (WhileLoop WhileLoop, FixupWhile1 FixupWhile1, Condition Condition, Statement Statement) {
        this.WhileLoop=WhileLoop;
        if(WhileLoop!=null) WhileLoop.setParent(this);
        this.FixupWhile1=FixupWhile1;
        if(FixupWhile1!=null) FixupWhile1.setParent(this);
        this.Condition=Condition;
        if(Condition!=null) Condition.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public WhileLoop getWhileLoop() {
        return WhileLoop;
    }

    public void setWhileLoop(WhileLoop WhileLoop) {
        this.WhileLoop=WhileLoop;
    }

    public FixupWhile1 getFixupWhile1() {
        return FixupWhile1;
    }

    public void setFixupWhile1(FixupWhile1 FixupWhile1) {
        this.FixupWhile1=FixupWhile1;
    }

    public Condition getCondition() {
        return Condition;
    }

    public void setCondition(Condition Condition) {
        this.Condition=Condition;
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
        if(WhileLoop!=null) WhileLoop.accept(visitor);
        if(FixupWhile1!=null) FixupWhile1.accept(visitor);
        if(Condition!=null) Condition.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WhileLoop!=null) WhileLoop.traverseTopDown(visitor);
        if(FixupWhile1!=null) FixupWhile1.traverseTopDown(visitor);
        if(Condition!=null) Condition.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WhileLoop!=null) WhileLoop.traverseBottomUp(visitor);
        if(FixupWhile1!=null) FixupWhile1.traverseBottomUp(visitor);
        if(Condition!=null) Condition.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementWhile(\n");

        if(WhileLoop!=null)
            buffer.append(WhileLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FixupWhile1!=null)
            buffer.append(FixupWhile1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Condition!=null)
            buffer.append(Condition.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementWhile]");
        return buffer.toString();
    }
}
