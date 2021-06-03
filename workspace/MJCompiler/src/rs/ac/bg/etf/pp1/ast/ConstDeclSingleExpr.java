// generated with ast extension for cup
// version 0.8
// 2/5/2021 23:44:15


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSingleExpr extends ConstDeclSingle {

    private String I1;
    private Init Init;

    public ConstDeclSingleExpr (String I1, Init Init) {
        this.I1=I1;
        this.Init=Init;
        if(Init!=null) Init.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public Init getInit() {
        return Init;
    }

    public void setInit(Init Init) {
        this.Init=Init;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Init!=null) Init.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Init!=null) Init.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Init!=null) Init.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclSingleExpr(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(Init!=null)
            buffer.append(Init.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSingleExpr]");
        return buffer.toString();
    }
}
