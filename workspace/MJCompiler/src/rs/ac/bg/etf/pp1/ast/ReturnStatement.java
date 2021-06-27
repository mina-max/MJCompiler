// generated with ast extension for cup
// version 0.8
// 27/5/2021 23:21:11


package rs.ac.bg.etf.pp1.ast;

public class ReturnStatement extends Statement {

    private ReturnExprOpt ReturnExprOpt;

    public ReturnStatement (ReturnExprOpt ReturnExprOpt) {
        this.ReturnExprOpt=ReturnExprOpt;
        if(ReturnExprOpt!=null) ReturnExprOpt.setParent(this);
    }

    public ReturnExprOpt getReturnExprOpt() {
        return ReturnExprOpt;
    }

    public void setReturnExprOpt(ReturnExprOpt ReturnExprOpt) {
        this.ReturnExprOpt=ReturnExprOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnExprOpt!=null) ReturnExprOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnExprOpt!=null) ReturnExprOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnExprOpt!=null) ReturnExprOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ReturnStatement(\n");

        if(ReturnExprOpt!=null)
            buffer.append(ReturnExprOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ReturnStatement]");
        return buffer.toString();
    }
}
