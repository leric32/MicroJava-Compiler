// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class ConstructorDeclarationName extends ConstructorDeclName {

    private String constructorName;

    public ConstructorDeclarationName (String constructorName) {
        this.constructorName=constructorName;
    }

    public String getConstructorName() {
        return constructorName;
    }

    public void setConstructorName(String constructorName) {
        this.constructorName=constructorName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstructorDeclarationName(\n");

        buffer.append(" "+tab+constructorName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstructorDeclarationName]");
        return buffer.toString();
    }
}
