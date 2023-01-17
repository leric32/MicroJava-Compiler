// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyWithMethodNoConstructor extends ClassBody {

    private ListOfClassVar ListOfClassVar;
    private ClassMethodDecl ClassMethodDecl;

    public ClassBodyWithMethodNoConstructor (ListOfClassVar ListOfClassVar, ClassMethodDecl ClassMethodDecl) {
        this.ListOfClassVar=ListOfClassVar;
        if(ListOfClassVar!=null) ListOfClassVar.setParent(this);
        this.ClassMethodDecl=ClassMethodDecl;
        if(ClassMethodDecl!=null) ClassMethodDecl.setParent(this);
    }

    public ListOfClassVar getListOfClassVar() {
        return ListOfClassVar;
    }

    public void setListOfClassVar(ListOfClassVar ListOfClassVar) {
        this.ListOfClassVar=ListOfClassVar;
    }

    public ClassMethodDecl getClassMethodDecl() {
        return ClassMethodDecl;
    }

    public void setClassMethodDecl(ClassMethodDecl ClassMethodDecl) {
        this.ClassMethodDecl=ClassMethodDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfClassVar!=null) ListOfClassVar.accept(visitor);
        if(ClassMethodDecl!=null) ClassMethodDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfClassVar!=null) ListOfClassVar.traverseTopDown(visitor);
        if(ClassMethodDecl!=null) ClassMethodDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfClassVar!=null) ListOfClassVar.traverseBottomUp(visitor);
        if(ClassMethodDecl!=null) ClassMethodDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyWithMethodNoConstructor(\n");

        if(ListOfClassVar!=null)
            buffer.append(ListOfClassVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassMethodDecl!=null)
            buffer.append(ClassMethodDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyWithMethodNoConstructor]");
        return buffer.toString();
    }
}
