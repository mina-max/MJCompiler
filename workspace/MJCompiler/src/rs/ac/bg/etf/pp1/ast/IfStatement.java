// generated with ast extension for cup
// version 0.8
// 25/7/2021 12:38:0


package rs.ac.bg.etf.pp1.ast;

public class IfStatement extends Statement {

    private ConditionCorrect ConditionCorrect;
    private ThenStatement ThenStatement;
    private ElseOpt ElseOpt;

    public IfStatement (ConditionCorrect ConditionCorrect, ThenStatement ThenStatement, ElseOpt ElseOpt) {
        this.ConditionCorrect=ConditionCorrect;
        if(ConditionCorrect!=null) ConditionCorrect.setParent(this);
        this.ThenStatement=ThenStatement;
        if(ThenStatement!=null) ThenStatement.setParent(this);
        this.ElseOpt=ElseOpt;
        if(ElseOpt!=null) ElseOpt.setParent(this);
    }

    public ConditionCorrect getConditionCorrect() {
        return ConditionCorrect;
    }

    public void setConditionCorrect(ConditionCorrect ConditionCorrect) {
        this.ConditionCorrect=ConditionCorrect;
    }

    public ThenStatement getThenStatement() {
        return ThenStatement;
    }

    public void setThenStatement(ThenStatement ThenStatement) {
        this.ThenStatement=ThenStatement;
    }

    public ElseOpt getElseOpt() {
        return ElseOpt;
    }

    public void setElseOpt(ElseOpt ElseOpt) {
        this.ElseOpt=ElseOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionCorrect!=null) ConditionCorrect.accept(visitor);
        if(ThenStatement!=null) ThenStatement.accept(visitor);
        if(ElseOpt!=null) ElseOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionCorrect!=null) ConditionCorrect.traverseTopDown(visitor);
        if(ThenStatement!=null) ThenStatement.traverseTopDown(visitor);
        if(ElseOpt!=null) ElseOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionCorrect!=null) ConditionCorrect.traverseBottomUp(visitor);
        if(ThenStatement!=null) ThenStatement.traverseBottomUp(visitor);
        if(ElseOpt!=null) ElseOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfStatement(\n");

        if(ConditionCorrect!=null)
            buffer.append(ConditionCorrect.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ThenStatement!=null)
            buffer.append(ThenStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ElseOpt!=null)
            buffer.append(ElseOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfStatement]");
        return buffer.toString();
    }
}
