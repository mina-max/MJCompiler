// generated with ast extension for cup
// version 0.8
// 25/7/2021 12:38:0


package rs.ac.bg.etf.pp1.ast;

public class MethodBody implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private VarDeclOnly VarDeclOnly;
    private StatementList StatementList;

    public MethodBody (VarDeclOnly VarDeclOnly, StatementList StatementList) {
        this.VarDeclOnly=VarDeclOnly;
        if(VarDeclOnly!=null) VarDeclOnly.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public VarDeclOnly getVarDeclOnly() {
        return VarDeclOnly;
    }

    public void setVarDeclOnly(VarDeclOnly VarDeclOnly) {
        this.VarDeclOnly=VarDeclOnly;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
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
        if(VarDeclOnly!=null) VarDeclOnly.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclOnly!=null) VarDeclOnly.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodBody(\n");

        if(VarDeclOnly!=null)
            buffer.append(VarDeclOnly.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodBody]");
        return buffer.toString();
    }
}
