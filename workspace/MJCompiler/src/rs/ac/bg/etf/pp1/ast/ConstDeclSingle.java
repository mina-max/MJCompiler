// generated with ast extension for cup
// version 0.8
// 29/5/2021 14:18:47


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclSingle implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String constName;
    private Init Init;

    public ConstDeclSingle (String constName, Init Init) {
        this.constName=constName;
        this.Init=Init;
        if(Init!=null) Init.setParent(this);
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public Init getInit() {
        return Init;
    }

    public void setInit(Init Init) {
        this.Init=Init;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
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
        buffer.append("ConstDeclSingle(\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        if(Init!=null)
            buffer.append(Init.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclSingle]");
        return buffer.toString();
    }
}
