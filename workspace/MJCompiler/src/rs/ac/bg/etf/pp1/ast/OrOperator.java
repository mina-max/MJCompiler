// generated with ast extension for cup
// version 0.8
// 25/7/2021 12:38:0


package rs.ac.bg.etf.pp1.ast;

public class OrOperator extends OrOp {

    public OrOperator () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OrOperator(\n");

        buffer.append(tab);
        buffer.append(") [OrOperator]");
        return buffer.toString();
    }
}