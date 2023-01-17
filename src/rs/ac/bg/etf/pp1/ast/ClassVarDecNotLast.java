// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class ClassVarDecNotLast extends ListOfClassVarDec {

    private ListOfClassVarDec ListOfClassVarDec;
    private ClassVar ClassVar;

    public ClassVarDecNotLast (ListOfClassVarDec ListOfClassVarDec, ClassVar ClassVar) {
        this.ListOfClassVarDec=ListOfClassVarDec;
        if(ListOfClassVarDec!=null) ListOfClassVarDec.setParent(this);
        this.ClassVar=ClassVar;
        if(ClassVar!=null) ClassVar.setParent(this);
    }

    public ListOfClassVarDec getListOfClassVarDec() {
        return ListOfClassVarDec;
    }

    public void setListOfClassVarDec(ListOfClassVarDec ListOfClassVarDec) {
        this.ListOfClassVarDec=ListOfClassVarDec;
    }

    public ClassVar getClassVar() {
        return ClassVar;
    }

    public void setClassVar(ClassVar ClassVar) {
        this.ClassVar=ClassVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfClassVarDec!=null) ListOfClassVarDec.accept(visitor);
        if(ClassVar!=null) ClassVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfClassVarDec!=null) ListOfClassVarDec.traverseTopDown(visitor);
        if(ClassVar!=null) ClassVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfClassVarDec!=null) ListOfClassVarDec.traverseBottomUp(visitor);
        if(ClassVar!=null) ClassVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassVarDecNotLast(\n");

        if(ListOfClassVarDec!=null)
            buffer.append(ListOfClassVarDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVar!=null)
            buffer.append(ClassVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassVarDecNotLast]");
        return buffer.toString();
    }
}
