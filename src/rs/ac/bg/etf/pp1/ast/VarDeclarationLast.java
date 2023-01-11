// generated with ast extension for cup
// version 0.8
// 8/0/2023 20:21:12


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationLast extends ListOfVarDec {

    private VarDeclLast VarDeclLast;

    public VarDeclarationLast (VarDeclLast VarDeclLast) {
        this.VarDeclLast=VarDeclLast;
        if(VarDeclLast!=null) VarDeclLast.setParent(this);
    }

    public VarDeclLast getVarDeclLast() {
        return VarDeclLast;
    }

    public void setVarDeclLast(VarDeclLast VarDeclLast) {
        this.VarDeclLast=VarDeclLast;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclLast!=null) VarDeclLast.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclLast!=null) VarDeclLast.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclLast!=null) VarDeclLast.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationLast(\n");

        if(VarDeclLast!=null)
            buffer.append(VarDeclLast.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationLast]");
        return buffer.toString();
    }
}
