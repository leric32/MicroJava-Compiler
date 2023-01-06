// generated with ast extension for cup
// version 0.8
// 4/0/2023 0:9:16


package rs.ac.bg.etf.pp1.ast;

public class FuncCallFactor extends Factor {

    private FuncCallFactorDes FuncCallFactorDes;
    private ActPars ActPars;

    public FuncCallFactor (FuncCallFactorDes FuncCallFactorDes, ActPars ActPars) {
        this.FuncCallFactorDes=FuncCallFactorDes;
        if(FuncCallFactorDes!=null) FuncCallFactorDes.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public FuncCallFactorDes getFuncCallFactorDes() {
        return FuncCallFactorDes;
    }

    public void setFuncCallFactorDes(FuncCallFactorDes FuncCallFactorDes) {
        this.FuncCallFactorDes=FuncCallFactorDes;
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
        if(FuncCallFactorDes!=null) FuncCallFactorDes.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FuncCallFactorDes!=null) FuncCallFactorDes.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FuncCallFactorDes!=null) FuncCallFactorDes.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FuncCallFactor(\n");

        if(FuncCallFactorDes!=null)
            buffer.append(FuncCallFactorDes.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FuncCallFactor]");
        return buffer.toString();
    }
}
