// generated with ast extension for cup
// version 0.8
// 2/5/2021 23:44:15


package rs.ac.bg.etf.pp1.ast;

public class ClassDeclExpr extends ClassDecl {

    private String I1;
    private ExtendsOpt ExtendsOpt;
    private VarDeclOnly VarDeclOnly;
    private MethodDeclListOpt MethodDeclListOpt;

    public ClassDeclExpr (String I1, ExtendsOpt ExtendsOpt, VarDeclOnly VarDeclOnly, MethodDeclListOpt MethodDeclListOpt) {
        this.I1=I1;
        this.ExtendsOpt=ExtendsOpt;
        if(ExtendsOpt!=null) ExtendsOpt.setParent(this);
        this.VarDeclOnly=VarDeclOnly;
        if(VarDeclOnly!=null) VarDeclOnly.setParent(this);
        this.MethodDeclListOpt=MethodDeclListOpt;
        if(MethodDeclListOpt!=null) MethodDeclListOpt.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExtendsOpt getExtendsOpt() {
        return ExtendsOpt;
    }

    public void setExtendsOpt(ExtendsOpt ExtendsOpt) {
        this.ExtendsOpt=ExtendsOpt;
    }

    public VarDeclOnly getVarDeclOnly() {
        return VarDeclOnly;
    }

    public void setVarDeclOnly(VarDeclOnly VarDeclOnly) {
        this.VarDeclOnly=VarDeclOnly;
    }

    public MethodDeclListOpt getMethodDeclListOpt() {
        return MethodDeclListOpt;
    }

    public void setMethodDeclListOpt(MethodDeclListOpt MethodDeclListOpt) {
        this.MethodDeclListOpt=MethodDeclListOpt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtendsOpt!=null) ExtendsOpt.accept(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.accept(visitor);
        if(MethodDeclListOpt!=null) MethodDeclListOpt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtendsOpt!=null) ExtendsOpt.traverseTopDown(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.traverseTopDown(visitor);
        if(MethodDeclListOpt!=null) MethodDeclListOpt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtendsOpt!=null) ExtendsOpt.traverseBottomUp(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.traverseBottomUp(visitor);
        if(MethodDeclListOpt!=null) MethodDeclListOpt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDeclExpr(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtendsOpt!=null)
            buffer.append(ExtendsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclOnly!=null)
            buffer.append(VarDeclOnly.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclListOpt!=null)
            buffer.append(MethodDeclListOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDeclExpr]");
        return buffer.toString();
    }
}
