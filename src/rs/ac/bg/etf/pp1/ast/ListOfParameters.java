// generated with ast extension for cup
// version 0.8
// 16/0/2023 21:23:17


package rs.ac.bg.etf.pp1.ast;

public class ListOfParameters extends FormParsList {

    private OneFormalParam OneFormalParam;
    private FormParsList FormParsList;

    public ListOfParameters (OneFormalParam OneFormalParam, FormParsList FormParsList) {
        this.OneFormalParam=OneFormalParam;
        if(OneFormalParam!=null) OneFormalParam.setParent(this);
        this.FormParsList=FormParsList;
        if(FormParsList!=null) FormParsList.setParent(this);
    }

    public OneFormalParam getOneFormalParam() {
        return OneFormalParam;
    }

    public void setOneFormalParam(OneFormalParam OneFormalParam) {
        this.OneFormalParam=OneFormalParam;
    }

    public FormParsList getFormParsList() {
        return FormParsList;
    }

    public void setFormParsList(FormParsList FormParsList) {
        this.FormParsList=FormParsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OneFormalParam!=null) OneFormalParam.accept(visitor);
        if(FormParsList!=null) FormParsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OneFormalParam!=null) OneFormalParam.traverseTopDown(visitor);
        if(FormParsList!=null) FormParsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OneFormalParam!=null) OneFormalParam.traverseBottomUp(visitor);
        if(FormParsList!=null) FormParsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListOfParameters(\n");

        if(OneFormalParam!=null)
            buffer.append(OneFormalParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsList!=null)
            buffer.append(FormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListOfParameters]");
        return buffer.toString();
    }
}
