// generated with ast extension for cup
// version 0.8
// 29/5/2021 14:18:47


package rs.ac.bg.etf.pp1.ast;

public class MethodFactor extends Factor {

    private Designator Designator;
    private ActParsOpt ActParsOpt;

    public MethodFactor (Designator Designator, ActParsOpt ActParsOpt) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.ActParsOpt=ActParsOpt;
        if(ActParsOpt!=null) ActParsOpt.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public ActParsOpt getActParsOpt() {
        return ActParsOpt;
    }

    public void setActParsOpt(ActParsOpt ActParsOpt) {
        this.ActParsOpt=ActParsOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(ActParsOpt!=null) ActParsOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(ActParsOpt!=null) ActParsOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(ActParsOpt!=null) ActParsOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodFactor(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActParsOpt!=null)
            buffer.append(ActParsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodFactor]");
        return buffer.toString();
    }
}
