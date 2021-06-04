// generated with ast extension for cup
// version 0.8
// 4/5/2021 12:43:55


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclExpr extends MethodDecl {

    private ReturnType ReturnType;
    private String I2;
    private FormParsOpt FormParsOpt;
    private VarDeclOnly VarDeclOnly;
    private StatementList StatementList;

    public MethodDeclExpr (ReturnType ReturnType, String I2, FormParsOpt FormParsOpt, VarDeclOnly VarDeclOnly, StatementList StatementList) {
        this.ReturnType=ReturnType;
        if(ReturnType!=null) ReturnType.setParent(this);
        this.I2=I2;
        this.FormParsOpt=FormParsOpt;
        if(FormParsOpt!=null) FormParsOpt.setParent(this);
        this.VarDeclOnly=VarDeclOnly;
        if(VarDeclOnly!=null) VarDeclOnly.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public ReturnType getReturnType() {
        return ReturnType;
    }

    public void setReturnType(ReturnType ReturnType) {
        this.ReturnType=ReturnType;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public FormParsOpt getFormParsOpt() {
        return FormParsOpt;
    }

    public void setFormParsOpt(FormParsOpt FormParsOpt) {
        this.FormParsOpt=FormParsOpt;
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ReturnType!=null) ReturnType.accept(visitor);
        if(FormParsOpt!=null) FormParsOpt.accept(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ReturnType!=null) ReturnType.traverseTopDown(visitor);
        if(FormParsOpt!=null) FormParsOpt.traverseTopDown(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ReturnType!=null) ReturnType.traverseBottomUp(visitor);
        if(FormParsOpt!=null) FormParsOpt.traverseBottomUp(visitor);
        if(VarDeclOnly!=null) VarDeclOnly.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclExpr(\n");

        if(ReturnType!=null)
            buffer.append(ReturnType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        if(FormParsOpt!=null)
            buffer.append(FormParsOpt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

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
        buffer.append(") [MethodDeclExpr]");
        return buffer.toString();
    }
}
