// generated with ast extension for cup
// version 0.8
// 2/5/2021 23:44:15


package rs.ac.bg.etf.pp1.ast;

public class DesListExpr extends DesList {

    private DesList DesList;
    private DesElement DesElement;

    public DesListExpr (DesList DesList, DesElement DesElement) {
        this.DesList=DesList;
        if(DesList!=null) DesList.setParent(this);
        this.DesElement=DesElement;
        if(DesElement!=null) DesElement.setParent(this);
    }

    public DesList getDesList() {
        return DesList;
    }

    public void setDesList(DesList DesList) {
        this.DesList=DesList;
    }

    public DesElement getDesElement() {
        return DesElement;
    }

    public void setDesElement(DesElement DesElement) {
        this.DesElement=DesElement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesList!=null) DesList.accept(visitor);
        if(DesElement!=null) DesElement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesList!=null) DesList.traverseTopDown(visitor);
        if(DesElement!=null) DesElement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesList!=null) DesList.traverseBottomUp(visitor);
        if(DesElement!=null) DesElement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesListExpr(\n");

        if(DesList!=null)
            buffer.append(DesList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesElement!=null)
            buffer.append(DesElement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesListExpr]");
        return buffer.toString();
    }
}
