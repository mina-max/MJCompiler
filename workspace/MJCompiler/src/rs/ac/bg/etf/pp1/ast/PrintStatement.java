// generated with ast extension for cup
// version 0.8
// 27/5/2021 23:21:11


package rs.ac.bg.etf.pp1.ast;

public class PrintStatement extends Statement {

    private Expr Expr;
    private PrintParamOpt PrintParamOpt;

    public PrintStatement (Expr Expr, PrintParamOpt PrintParamOpt) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.PrintParamOpt=PrintParamOpt;
        if(PrintParamOpt!=null) PrintParamOpt.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public PrintParamOpt getPrintParamOpt() {
        return PrintParamOpt;
    }

    public void setPrintParamOpt(PrintParamOpt PrintParamOpt) {
        this.PrintParamOpt=PrintParamOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(PrintParamOpt!=null) PrintParamOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(PrintParamOpt!=null) PrintParamOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(PrintParamOpt!=null) PrintParamOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("PrintStatement(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(PrintParamOpt!=null)
            buffer.append(PrintParamOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [PrintStatement]");
        return buffer.toString();
    }
}
