// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:46


package rs.ac.bg.etf.pp1.ast;

public class StatementForeach extends Statement {

    private ForEachLoop ForEachLoop;
    private ForEachIdent ForEachIdent;
    private Statement Statement;
    private CheckForeach2 CheckForeach2;

    public StatementForeach (ForEachLoop ForEachLoop, ForEachIdent ForEachIdent, Statement Statement, CheckForeach2 CheckForeach2) {
        this.ForEachLoop=ForEachLoop;
        if(ForEachLoop!=null) ForEachLoop.setParent(this);
        this.ForEachIdent=ForEachIdent;
        if(ForEachIdent!=null) ForEachIdent.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.CheckForeach2=CheckForeach2;
        if(CheckForeach2!=null) CheckForeach2.setParent(this);
    }

    public ForEachLoop getForEachLoop() {
        return ForEachLoop;
    }

    public void setForEachLoop(ForEachLoop ForEachLoop) {
        this.ForEachLoop=ForEachLoop;
    }

    public ForEachIdent getForEachIdent() {
        return ForEachIdent;
    }

    public void setForEachIdent(ForEachIdent ForEachIdent) {
        this.ForEachIdent=ForEachIdent;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public CheckForeach2 getCheckForeach2() {
        return CheckForeach2;
    }

    public void setCheckForeach2(CheckForeach2 CheckForeach2) {
        this.CheckForeach2=CheckForeach2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForEachLoop!=null) ForEachLoop.accept(visitor);
        if(ForEachIdent!=null) ForEachIdent.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(CheckForeach2!=null) CheckForeach2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForEachLoop!=null) ForEachLoop.traverseTopDown(visitor);
        if(ForEachIdent!=null) ForEachIdent.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(CheckForeach2!=null) CheckForeach2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForEachLoop!=null) ForEachLoop.traverseBottomUp(visitor);
        if(ForEachIdent!=null) ForEachIdent.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(CheckForeach2!=null) CheckForeach2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("StatementForeach(\n");

        if(ForEachLoop!=null)
            buffer.append(ForEachLoop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ForEachIdent!=null)
            buffer.append(ForEachIdent.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CheckForeach2!=null)
            buffer.append(CheckForeach2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StatementForeach]");
        return buffer.toString();
    }
}
