// generated with ast extension for cup
// version 0.8
// 4/5/2021 12:43:55


package rs.ac.bg.etf.pp1.ast;

public class VarDeclSingleExpr extends VarDeclSingle {

    private String I1;
    private ArrayOpt ArrayOpt;

    public VarDeclSingleExpr (String I1, ArrayOpt ArrayOpt) {
        this.I1=I1;
        this.ArrayOpt=ArrayOpt;
        if(ArrayOpt!=null) ArrayOpt.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ArrayOpt getArrayOpt() {
        return ArrayOpt;
    }

    public void setArrayOpt(ArrayOpt ArrayOpt) {
        this.ArrayOpt=ArrayOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ArrayOpt!=null) ArrayOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ArrayOpt!=null) ArrayOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ArrayOpt!=null) ArrayOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclSingleExpr(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ArrayOpt!=null)
            buffer.append(ArrayOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclSingleExpr]");
        return buffer.toString();
    }
}
