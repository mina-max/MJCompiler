// generated with ast extension for cup
// version 0.8
// 25/5/2021 20:13:42


package rs.ac.bg.etf.pp1.ast;

public class MethodDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ReturnType ReturnType;
    private MethodSign MethodSign;
    private MethodBody MethodBody;

    public MethodDecl (ReturnType ReturnType, MethodSign MethodSign, MethodBody MethodBody) {
        this.ReturnType=ReturnType;
        if(ReturnType!=null) ReturnType.setParent(this);
        this.MethodSign=MethodSign;
        if(MethodSign!=null) MethodSign.setParent(this);
        this.MethodBody=MethodBody;
        if(MethodBody!=null) MethodBody.setParent(this);
    }

    public ReturnType getReturnType() {
        return ReturnType;
    }

    public void setReturnType(ReturnType ReturnType) {
        this.ReturnType=ReturnType;
    }

    public MethodSign getMethodSign() {
        return MethodSign;
    }

    public void setMethodSign(MethodSign MethodSign) {
        this.MethodSign=MethodSign;
    }

    public MethodBody getMethodBody() {
        return MethodBody;
    }

    public void setMethodBody(MethodBody MethodBody) {
        this.MethodBody=MethodBody;
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
        if(ReturnType!=null) ReturnType.accept(visitor);
        if(MethodSign!=null) MethodSign.accept(visitor);
        if(MethodBody!=null) MethodBody.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnType!=null) ReturnType.traverseTopDown(visitor);
        if(MethodSign!=null) MethodSign.traverseTopDown(visitor);
        if(MethodBody!=null) MethodBody.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnType!=null) ReturnType.traverseBottomUp(visitor);
        if(MethodSign!=null) MethodSign.traverseBottomUp(visitor);
        if(MethodBody!=null) MethodBody.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDecl(\n");

        if(ReturnType!=null)
            buffer.append(ReturnType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodSign!=null)
            buffer.append(MethodSign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodBody!=null)
            buffer.append(MethodBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDecl]");
        return buffer.toString();
    }
}
