// generated with ast extension for cup
// version 0.8
// 14/0/2023 19:7:46


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDeclaration extends ClassVarDecl {

    private Type Type;
    private ListOfClassVarDec ListOfClassVarDec;

    public ClassVarDeclaration (Type Type, ListOfClassVarDec ListOfClassVarDec) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ListOfClassVarDec=ListOfClassVarDec;
        if(ListOfClassVarDec!=null) ListOfClassVarDec.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ListOfClassVarDec getListOfClassVarDec() {
        return ListOfClassVarDec;
    }

    public void setListOfClassVarDec(ListOfClassVarDec ListOfClassVarDec) {
        this.ListOfClassVarDec=ListOfClassVarDec;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ListOfClassVarDec!=null) ListOfClassVarDec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ListOfClassVarDec!=null) ListOfClassVarDec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ListOfClassVarDec!=null) ListOfClassVarDec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOfClassVarDec!=null)
            buffer.append(ListOfClassVarDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDeclaration]");
        return buffer.toString();
    }
}
