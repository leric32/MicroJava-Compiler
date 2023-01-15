// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:46


package rs.ac.bg.etf.pp1.ast;

public class VarDeclarationNotLast extends ListOfVarDec {

    private VarDeclNotLast VarDeclNotLast;
    private ListOfVarDec ListOfVarDec;

    public VarDeclarationNotLast (VarDeclNotLast VarDeclNotLast, ListOfVarDec ListOfVarDec) {
        this.VarDeclNotLast=VarDeclNotLast;
        if(VarDeclNotLast!=null) VarDeclNotLast.setParent(this);
        this.ListOfVarDec=ListOfVarDec;
        if(ListOfVarDec!=null) ListOfVarDec.setParent(this);
    }

    public VarDeclNotLast getVarDeclNotLast() {
        return VarDeclNotLast;
    }

    public void setVarDeclNotLast(VarDeclNotLast VarDeclNotLast) {
        this.VarDeclNotLast=VarDeclNotLast;
    }

    public ListOfVarDec getListOfVarDec() {
        return ListOfVarDec;
    }

    public void setListOfVarDec(ListOfVarDec ListOfVarDec) {
        this.ListOfVarDec=ListOfVarDec;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclNotLast!=null) VarDeclNotLast.accept(visitor);
        if(ListOfVarDec!=null) ListOfVarDec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclNotLast!=null) VarDeclNotLast.traverseTopDown(visitor);
        if(ListOfVarDec!=null) ListOfVarDec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclNotLast!=null) VarDeclNotLast.traverseBottomUp(visitor);
        if(ListOfVarDec!=null) ListOfVarDec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclarationNotLast(\n");

        if(VarDeclNotLast!=null)
            buffer.append(VarDeclNotLast.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOfVarDec!=null)
            buffer.append(ListOfVarDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclarationNotLast]");
        return buffer.toString();
    }
}
