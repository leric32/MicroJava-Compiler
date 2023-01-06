// generated with ast extension for cup
// version 0.8
// 4/0/2023 0:9:16


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclaration extends ConstDecl {

    private Type Type;
    private ConstAssignment ConstAssignment;
    private ListOfCharDec ListOfCharDec;

    public ConstDeclaration (Type Type, ConstAssignment ConstAssignment, ListOfCharDec ListOfCharDec) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ConstAssignment=ConstAssignment;
        if(ConstAssignment!=null) ConstAssignment.setParent(this);
        this.ListOfCharDec=ListOfCharDec;
        if(ListOfCharDec!=null) ListOfCharDec.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ConstAssignment getConstAssignment() {
        return ConstAssignment;
    }

    public void setConstAssignment(ConstAssignment ConstAssignment) {
        this.ConstAssignment=ConstAssignment;
    }

    public ListOfCharDec getListOfCharDec() {
        return ListOfCharDec;
    }

    public void setListOfCharDec(ListOfCharDec ListOfCharDec) {
        this.ListOfCharDec=ListOfCharDec;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ConstAssignment!=null) ConstAssignment.accept(visitor);
        if(ListOfCharDec!=null) ListOfCharDec.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ConstAssignment!=null) ConstAssignment.traverseTopDown(visitor);
        if(ListOfCharDec!=null) ListOfCharDec.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ConstAssignment!=null) ConstAssignment.traverseBottomUp(visitor);
        if(ListOfCharDec!=null) ListOfCharDec.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclaration(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstAssignment!=null)
            buffer.append(ConstAssignment.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ListOfCharDec!=null)
            buffer.append(ListOfCharDec.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclaration]");
        return buffer.toString();
    }
}
