// generated with ast extension for cup
// version 0.8
// 27/5/2021 23:21:11


package rs.ac.bg.etf.pp1.ast;

public class NotEqualRel extends Relop {

    public NotEqualRel () {
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
        buffer.append("NotEqualRel(\n");

        buffer.append(tab);
        buffer.append(") [NotEqualRel]");
        return buffer.toString();
    }
}
