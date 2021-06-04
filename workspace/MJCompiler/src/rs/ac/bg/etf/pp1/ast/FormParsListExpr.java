// generated with ast extension for cup
// version 0.8
// 4/5/2021 12:43:55


package rs.ac.bg.etf.pp1.ast;

public class FormParsListExpr extends FormParsList {

    private FormParsList FormParsList;
    private FormParsSingle FormParsSingle;

    public FormParsListExpr (FormParsList FormParsList, FormParsSingle FormParsSingle) {
        this.FormParsList=FormParsList;
        if(FormParsList!=null) FormParsList.setParent(this);
        this.FormParsSingle=FormParsSingle;
        if(FormParsSingle!=null) FormParsSingle.setParent(this);
    }

    public FormParsList getFormParsList() {
        return FormParsList;
    }

    public void setFormParsList(FormParsList FormParsList) {
        this.FormParsList=FormParsList;
    }

    public FormParsSingle getFormParsSingle() {
        return FormParsSingle;
    }

    public void setFormParsSingle(FormParsSingle FormParsSingle) {
        this.FormParsSingle=FormParsSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsList!=null) FormParsList.accept(visitor);
        if(FormParsSingle!=null) FormParsSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsList!=null) FormParsList.traverseTopDown(visitor);
        if(FormParsSingle!=null) FormParsSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsList!=null) FormParsList.traverseBottomUp(visitor);
        if(FormParsSingle!=null) FormParsSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParsListExpr(\n");

        if(FormParsList!=null)
            buffer.append(FormParsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsSingle!=null)
            buffer.append(FormParsSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParsListExpr]");
        return buffer.toString();
    }
}
