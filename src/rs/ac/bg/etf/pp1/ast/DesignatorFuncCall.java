// generated with ast extension for cup
// version 0.8
// 25/11/2022 22:27:57


package rs.ac.bg.etf.pp1.ast;

public class DesignatorFuncCall extends DesignatorStatement {

    private FunctionCall FunctionCall;
    private ActPars ActPars;

    public DesignatorFuncCall (FunctionCall FunctionCall, ActPars ActPars) {
        this.FunctionCall=FunctionCall;
        if(FunctionCall!=null) FunctionCall.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public FunctionCall getFunctionCall() {
        return FunctionCall;
    }

    public void setFunctionCall(FunctionCall FunctionCall) {
        this.FunctionCall=FunctionCall;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FunctionCall!=null) FunctionCall.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FunctionCall!=null) FunctionCall.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FunctionCall!=null) FunctionCall.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorFuncCall(\n");

        if(FunctionCall!=null)
            buffer.append(FunctionCall.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorFuncCall]");
        return buffer.toString();
    }
}
