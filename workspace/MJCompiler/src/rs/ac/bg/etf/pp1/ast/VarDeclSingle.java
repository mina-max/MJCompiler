// generated with ast extension for cup
// version 0.8
// 27/5/2021 23:21:11


package rs.ac.bg.etf.pp1.ast;

public class VarDeclSingle implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String varName;
    private ArrayOpt ArrayOpt;

    public VarDeclSingle (String varName, ArrayOpt ArrayOpt) {
        this.varName=varName;
        this.ArrayOpt=ArrayOpt;
        if(ArrayOpt!=null) ArrayOpt.setParent(this);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public ArrayOpt getArrayOpt() {
        return ArrayOpt;
    }

    public void setArrayOpt(ArrayOpt ArrayOpt) {
        this.ArrayOpt=ArrayOpt;
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
        buffer.append("VarDeclSingle(\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        if(ArrayOpt!=null)
            buffer.append(ArrayOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclSingle]");
        return buffer.toString();
    }
}
