// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class ListOfCharDeclarations extends ListOfCharDec {

    private ListOfCharDec ListOfCharDec;
    private ConstAssignment ConstAssignment;

    public ListOfCharDeclarations (ListOfCharDec ListOfCharDec, ConstAssignment ConstAssignment) {
        this.ListOfCharDec=ListOfCharDec;
        if(ListOfCharDec!=null) ListOfCharDec.setParent(this);
        this.ConstAssignment=ConstAssignment;
        if(ConstAssignment!=null) ConstAssignment.setParent(this);
    }

    public ListOfCharDec getListOfCharDec() {
        return ListOfCharDec;
    }

    public void setListOfCharDec(ListOfCharDec ListOfCharDec) {
        this.ListOfCharDec=ListOfCharDec;
    }

    public ConstAssignment getConstAssignment() {
        return ConstAssignment;
    }

    public void setConstAssignment(ConstAssignment ConstAssignment) {
        this.ConstAssignment=ConstAssignment;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ListOfCharDec!=null) ListOfCharDec.accept(visitor);
        if(ConstAssignment!=null) ConstAssignment.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ListOfCharDec!=null) ListOfCharDec.traverseTopDown(visitor);
        if(ConstAssignment!=null) ConstAssignment.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ListOfCharDec!=null) ListOfCharDec.traverseBottomUp(visitor);
        if(ConstAssignment!=null) ConstAssignment.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListOfCharDeclarations(\n");

        if(ListOfCharDec!=null)
            buffer.append(ListOfCharDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssignment!=null)
            buffer.append(ConstAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListOfCharDeclarations]");
        return buffer.toString();
    }
}
