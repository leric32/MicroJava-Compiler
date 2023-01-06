// generated with ast extension for cup
// version 0.8
// 4/0/2023 0:9:16


package rs.ac.bg.etf.pp1.ast;

public class ClassBodyNoMethodWithEmptyBrackets extends ClassBody {

    private ListOfClassVar ListOfClassVar;

    public ClassBodyNoMethodWithEmptyBrackets (ListOfClassVar ListOfClassVar) {
        this.ListOfClassVar=ListOfClassVar;
        if(ListOfClassVar!=null) ListOfClassVar.setParent(this);
    }

    public ListOfClassVar getListOfClassVar() {
        return ListOfClassVar;
    }

    public void setListOfClassVar(ListOfClassVar ListOfClassVar) {
        this.ListOfClassVar=ListOfClassVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfClassVar!=null) ListOfClassVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfClassVar!=null) ListOfClassVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfClassVar!=null) ListOfClassVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassBodyNoMethodWithEmptyBrackets(\n");

        if(ListOfClassVar!=null)
            buffer.append(ListOfClassVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassBodyNoMethodWithEmptyBrackets]");
        return buffer.toString();
    }
}
