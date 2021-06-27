// generated with ast extension for cup
// version 0.8
// 27/5/2021 23:21:11


package rs.ac.bg.etf.pp1.ast;

public class VarDeclOne extends VarDeclList {

    private VarDeclSingle VarDeclSingle;

    public VarDeclOne (VarDeclSingle VarDeclSingle) {
        this.VarDeclSingle=VarDeclSingle;
        if(VarDeclSingle!=null) VarDeclSingle.setParent(this);
    }

    public VarDeclSingle getVarDeclSingle() {
        return VarDeclSingle;
    }

    public void setVarDeclSingle(VarDeclSingle VarDeclSingle) {
        this.VarDeclSingle=VarDeclSingle;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarDeclSingle!=null) VarDeclSingle.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarDeclSingle!=null) VarDeclSingle.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarDeclSingle!=null) VarDeclSingle.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarDeclOne(\n");

        if(VarDeclSingle!=null)
            buffer.append(VarDeclSingle.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarDeclOne]");
        return buffer.toString();
    }
}
