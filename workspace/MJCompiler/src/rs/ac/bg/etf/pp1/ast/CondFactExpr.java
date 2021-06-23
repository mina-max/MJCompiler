// generated with ast extension for cup
// version 0.8
// 22/5/2021 19:12:43


package rs.ac.bg.etf.pp1.ast;

public class CondFactExpr extends CondFact {

    private Expr Expr;
    private RelopOpt RelopOpt;

    public CondFactExpr (Expr Expr, RelopOpt RelopOpt) {
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
        this.RelopOpt=RelopOpt;
        if(RelopOpt!=null) RelopOpt.setParent(this);
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public RelopOpt getRelopOpt() {
        return RelopOpt;
    }

    public void setRelopOpt(RelopOpt RelopOpt) {
        this.RelopOpt=RelopOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expr!=null) Expr.accept(visitor);
        if(RelopOpt!=null) RelopOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
        if(RelopOpt!=null) RelopOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        if(RelopOpt!=null) RelopOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CondFactExpr(\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(RelopOpt!=null)
            buffer.append(RelopOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CondFactExpr]");
        return buffer.toString();
    }
}
