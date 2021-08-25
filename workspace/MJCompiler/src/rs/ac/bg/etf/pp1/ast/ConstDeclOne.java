// generated with ast extension for cup
// version 0.8
// 25/7/2021 12:38:0


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclOne extends ConstDeclList {

    private ConstDeclSingle ConstDeclSingle;

    public ConstDeclOne (ConstDeclSingle ConstDeclSingle) {
        this.ConstDeclSingle=ConstDeclSingle;
        if(ConstDeclSingle!=null) ConstDeclSingle.setParent(this);
    }

    public ConstDeclSingle getConstDeclSingle() {
        return ConstDeclSingle;
    }

    public void setConstDeclSingle(ConstDeclSingle ConstDeclSingle) {
        this.ConstDeclSingle=ConstDeclSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstDeclSingle!=null) ConstDeclSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstDeclSingle!=null) ConstDeclSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstDeclSingle!=null) ConstDeclSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclOne(\n");

        if(ConstDeclSingle!=null)
            buffer.append(ConstDeclSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclOne]");
        return buffer.toString();
    }
}
