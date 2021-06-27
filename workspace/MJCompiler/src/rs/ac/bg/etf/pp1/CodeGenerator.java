package rs.ac.bg.etf.pp1;

import java.util.concurrent.ConcurrentLinkedDeque;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	public void visit(PrintStatement printStmt) {
		if (printStmt.getPrintParamOpt() instanceof PrintParam) {
			int width = ((PrintParam) printStmt.getPrintParamOpt()).getPrintWidth();
			Code.loadConst(width);
			if (printStmt.getExpr().struct.getKind() == Struct.Int
					|| printStmt.getExpr().struct.getKind() == Struct.Bool) { // int
				Code.put(Code.print);
			} else { // char
				Code.put(Code.bprint);
			}
		} else {
			if (printStmt.getExpr().struct.getKind() == Struct.Int
					|| printStmt.getExpr().struct.getKind() == Struct.Bool) { // int
				Code.loadConst(5);
				Code.put(Code.print);
			} else { // char
				Code.loadConst(1);
				Code.put(Code.bprint);
			}
		}
	}

	public void visit(ReadStatement readStmt) {
		if (readStmt.getDesignator().obj.getType().getKind() == Struct.Int)
			Code.put(Code.read);
		else
			Code.put(Code.bread);
		Code.store(readStmt.getDesignator().obj);
	}

	public void visit(NumberConst num) {
		Obj obj = MySymbolTable.insert(Obj.Con, "$", num.struct);
		obj.setAdr(num.getNumber());
		obj.setLevel(0);
		Code.load(obj);
	}

	public void visit(CharConst character) {
		Obj obj = MySymbolTable.insert(Obj.Con, "$", character.struct);
		obj.setAdr(character.getCharacter());
		obj.setLevel(0);
		Code.load(obj);
	}

	public void visit(BoolConst bool) {
		Obj obj = MySymbolTable.insert(Obj.Con, "$", bool.struct);
		obj.setAdr(bool.getBoolVal());
		obj.setLevel(0);
		Code.load(obj);
	}

	public void visit(VarFactor var) {
		Obj obj = var.getDesignator().obj;
		Designator des = var.getDesignator();
		if (des.getDesList() instanceof DesListExpr) {
			if (((DesListExpr) des.getDesList()).getDesElement() instanceof ArrayIdent) {
				Code.load(des.obj);
				Code.put(Code.dup_x1);
				Code.put(Code.pop);
				if (des.obj.getType().getKind() == Struct.Int)
					Code.put(Code.aload);
				else
					Code.put(Code.baload);
				return;
			}
		}
		Code.load(obj);
	}

	public void visit(MethodName method) {
		if ("main".equalsIgnoreCase(method.getMethodName())) {
			mainPc = Code.pc;
		}
		method.obj.setAdr(Code.pc);

		int formParsCnt = method.obj.getLevel();
		int varCnt = method.obj.getLocalSymbols().size() - formParsCnt;
		Code.put(Code.enter);
		Code.put(formParsCnt);
		Code.put(formParsCnt + varCnt);

	}

	public void visit(MethodDecl methodDecl) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(AssignDes assignDes) {
		Designator des = assignDes.getDesignator();
		if (des.getDesList() instanceof DesListExpr) {
			if (((DesListExpr) des.getDesList()).getDesElement() instanceof ArrayIdent) {
				/*
				 * Code.put(Code.dup_x1); Code.put(Code.pop);
				 */
				Code.load(des.obj);
				Code.put(Code.dup_x2);
				Code.put(Code.pop);
				if (des.obj.getType().getKind() == Struct.Int)
					Code.put(Code.astore);
				else
					Code.put(Code.bastore);
				return;
			}
		}
		Code.store(des.obj);
	}

	public void visit(IncDes incDes) {
		Code.load(incDes.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(incDes.getDesignator().obj);
	}

	public void visit(DecDes decDes) {
		Code.load(decDes.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(decDes.getDesignator().obj);
	}

	public void visit(NotFirstTerm expr) {
		if (expr.getAddOp() instanceof Plus)
			Code.put(Code.add);
		else
			Code.put(Code.sub);
	}

	public void visit(TermExpr expr) {
		if (expr.getMulOp() instanceof Mul)
			Code.put(Code.mul);
		else if (expr.getMulOp() instanceof Div)
			Code.put(Code.div);
		else
			Code.put(Code.rem);

	}

	public void visit(NegativeTerm term) {
		Code.put(Code.neg);
	}

	public void visit(NewArrayFactor newArray) {
		Code.put(Code.newarray);
		if (newArray.getType().struct.getKind() == Struct.Char || newArray.getType().struct.getKind() == Struct.Bool) {
			Code.put(0);
		} else {
			Code.put(1);
		}
	}

	public void visit(IfStatement ifStmt) {
		Condition cond = ((CorrectCondition) ifStmt.getConditionCorrect()).getCondition();
		if (ifStmt.getElseOpt() instanceof ElseStmt) {
			//smth
		}
		
		if(cond instanceof CondOr) {
			
		}
		
		

	}

	public void visit(CondFactRelop fact) {
		Code.putFalseJump(Code.eq, 0);
	}

	public void visit(CondFactNoRelop fact) {
		Code.putFalseJump(Code.eq, 0);
	}

}