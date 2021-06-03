// generated with ast extension for cup
// version 0.8
// 2/5/2021 23:44:15


package rs.ac.bg.etf.pp1.ast;

public class VarDeclOnlyExpr extends VarDeclOnly {

    private VarDeclOnly VarDeclOnly;
    private VarDecl VarDecl;

    public VarDeclOnlyExpr (VarDeclOnly VarDeclOnly, VarDecl VarDecl) {
        this.VarDeclOnly=VarDeclOnly;
        if(VarDeclOnly!=null) VarDeclOnly.setParent(this);
        this.VarDecl=VarDecl;
        if(VarDecl!=null) VarDecl.setParent(this);
    }

    public VarDeclOnly getVarDeclOnly() {
        return VarDeclOnly;
    }

    public void setVarDeclOnly(VarDeclOnly VarDeclOnly) {
        this.VarDeclOnly=VarDeclOnly;
    }

    public VarDecl getVarDecl() {
        return VarDecl;
    }

    public void setVarDecl(VarDecl VarDecl) {
        this.VarDecl=VarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclOnly!=null) VarDeclOnly.accept(visitor);
        if(VarDecl!=null) VarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.traverseTopDown(visitor);
        if(VarDecl!=null) VarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclOnly!=null) VarDeclOnly.traverseBottomUp(visitor);
        if(VarDecl!=null) VarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclOnlyExpr(\n");

        if(VarDeclOnly!=null)
            buffer.append(VarDeclOnly.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDecl!=null)
            buffer.append(VarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclOnlyExpr]");
        return buffer.toString();
    }
}
