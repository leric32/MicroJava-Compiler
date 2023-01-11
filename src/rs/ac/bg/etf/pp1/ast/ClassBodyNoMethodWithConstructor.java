// generated with ast extension for cup
// version 0.8
// 8/0/2023 20:21:12


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyNoMethodWithConstructor extends ClassBody {

    private ListOfClassVar ListOfClassVar;
    private ConstructorDeclList ConstructorDeclList;

    public ClassBodyNoMethodWithConstructor (ListOfClassVar ListOfClassVar, ConstructorDeclList ConstructorDeclList) {
        this.ListOfClassVar=ListOfClassVar;
        if(ListOfClassVar!=null) ListOfClassVar.setParent(this);
        this.ConstructorDeclList=ConstructorDeclList;
        if(ConstructorDeclList!=null) ConstructorDeclList.setParent(this);
    }

    public ListOfClassVar getListOfClassVar() {
        return ListOfClassVar;
    }

    public void setListOfClassVar(ListOfClassVar ListOfClassVar) {
        this.ListOfClassVar=ListOfClassVar;
    }

    public ConstructorDeclList getConstructorDeclList() {
        return ConstructorDeclList;
    }

    public void setConstructorDeclList(ConstructorDeclList ConstructorDeclList) {
        this.ConstructorDeclList=ConstructorDeclList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfClassVar!=null) ListOfClassVar.accept(visitor);
        if(ConstructorDeclList!=null) ConstructorDeclList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfClassVar!=null) ListOfClassVar.traverseTopDown(visitor);
        if(ConstructorDeclList!=null) ConstructorDeclList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfClassVar!=null) ListOfClassVar.traverseBottomUp(visitor);
        if(ConstructorDeclList!=null) ConstructorDeclList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyNoMethodWithConstructor(\n");

        if(ListOfClassVar!=null)
            buffer.append(ListOfClassVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstructorDeclList!=null)
            buffer.append(ConstructorDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyNoMethodWithConstructor]");
        return buffer.toString();
    }
}
