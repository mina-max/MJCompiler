// generated with ast extension for cup
// version 0.8
// 4/5/2021 12:43:55


package rs.ac.bg.etf.pp1.ast;

public class DesignatorName extends Designator {

    private String I1;
    private DesList DesList;

    public DesignatorName (String I1, DesList DesList) {
        this.I1=I1;
        this.DesList=DesList;
        if(DesList!=null) DesList.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public DesList getDesList() {
        return DesList;
    }

    public void setDesList(DesList DesList) {
        this.DesList=DesList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesList!=null) DesList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesList!=null) DesList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesList!=null) DesList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorName(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(DesList!=null)
            buffer.append(DesList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorName]");
        return buffer.toString();
    }
}
