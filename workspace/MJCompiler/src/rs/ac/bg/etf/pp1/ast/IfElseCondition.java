// generated with ast extension for cup
// version 0.8
// 25/5/2021 20:13:42


package rs.ac.bg.etf.pp1.ast;

public class IfElseCondition extends Statement {

    private ConditionCorrect ConditionCorrect;
    private Statement Statement;
    private Statement Statement1;

    public IfElseCondition (ConditionCorrect ConditionCorrect, Statement Statement, Statement Statement1) {
        this.ConditionCorrect=ConditionCorrect;
        if(ConditionCorrect!=null) ConditionCorrect.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
        this.Statement1=Statement1;
        if(Statement1!=null) Statement1.setParent(this);
    }

    public ConditionCorrect getConditionCorrect() {
        return ConditionCorrect;
    }

    public void setConditionCorrect(ConditionCorrect ConditionCorrect) {
        this.ConditionCorrect=ConditionCorrect;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public Statement getStatement1() {
        return Statement1;
    }

    public void setStatement1(Statement Statement1) {
        this.Statement1=Statement1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionCorrect!=null) ConditionCorrect.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
        if(Statement1!=null) Statement1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionCorrect!=null) ConditionCorrect.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
        if(Statement1!=null) Statement1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionCorrect!=null) ConditionCorrect.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        if(Statement1!=null) Statement1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IfElseCondition(\n");

        if(ConditionCorrect!=null)
            buffer.append(ConditionCorrect.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement1!=null)
            buffer.append(Statement1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IfElseCondition]");
        return buffer.toString();
    }
}
