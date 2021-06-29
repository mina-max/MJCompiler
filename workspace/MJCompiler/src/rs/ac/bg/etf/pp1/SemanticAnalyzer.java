package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.*;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class SemanticAnalyzer extends VisitorAdaptor {
	private static Struct currentType = MySymbolTable.noType;
	private static Struct currentMethodReturnType = MySymbolTable.noType;
	private static int currentLevel = 0;
	private static boolean classDefinition = false;
	private static boolean methodDefinition = false;
	private static boolean errorDetected = false;
	private static Obj currentClass = null;
	private static Obj currentMethod = null;
	Logger log = Logger.getLogger(getClass());
	private static int doWhileCnt = 0;
	public static int nVars;

	private static Map<String, Obj> currentMethodFormParams = new HashMap<String, Obj>();
	private static List<Struct> arrayStructs = new LinkedList<Struct>();

	public void report_error(String message, SyntaxNode info) {
		
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" - Greska na liniji ").append(line);
		log.error(msg.toString());
		MyCompiler.errors.add(new CompilerError(line, msg.toString(), CompilerErrorType.SEMANTIC_ERROR));
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji").append(line);
		log.info(msg.toString());
	}

	public void report_found(String message, Obj obj, SyntaxNode node) {
		StringBuilder msg = new StringBuilder(message);
		int line = (node == null) ? 0 : node.getLine();
		DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
		if (line != 0) {
			msg.append("Pretraga na ").append(line).append("(").append(obj.getName()).append("), ");
			stv.visitObjNode(obj);
			msg.append("nadjeno ").append(stv.getOutput());
		}

		log.info(msg.toString());
	}

	public boolean passed() {
		return !errorDetected;
	}
	public boolean symbolExists(SyntaxNode varDecl, String varName) {
		Obj sym = MySymbolTable.find(varName);
		if (sym != MySymbolTable.noObj && sym.getLevel() == currentLevel) {
			report_error("Vec postoji simbol '" + varName + "'", varDecl);
			return true;
		}
		return false;

	}

	public void visit(ProgName progName) {

		progName.obj = MySymbolTable.insert(Obj.Prog, progName.getProgName(), MySymbolTable.noType);
		MySymbolTable.openScope();
		System.out.println("Visited progName");
	}

	public void visit(Program program) {
		Obj mainMethodObj = MySymbolTable.find("main");
		if (mainMethodObj == MySymbolTable.noObj)
			report_error("Program mora imati main metodu", program);
		else {
			if (!mainMethodObj.getType().equals(MySymbolTable.noType))
				report_error("Povratna vrednost main metode mora biti void", program);
			if (mainMethodObj.getLevel() > 0)
				report_error("Main metoda ne sme da ima argumente", program);
		}
		nVars = MySymbolTable.currentScope.getnVars();
		MySymbolTable.chainLocalSymbols(program.getProgName().obj);
		MySymbolTable.closeScope();
	}

	public void visit(Type type) {
		Obj typeNode = MySymbolTable.find(type.getTypeName());
		if (typeNode == MySymbolTable.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = MySymbolTable.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = MySymbolTable.noType;
			}
		}
		currentType = type.struct;
	}

	public void visit(VarDeclSingle varDecl) {
		if (symbolExists(varDecl, varDecl.getVarName()))
			return;

		boolean isArray = false;
		if (varDecl.getArrayOpt() instanceof Array)
			isArray = true;

		int objKind = 0;
		if (classDefinition && !methodDefinition)
			objKind = Obj.Fld;
		else
			objKind = Obj.Var;

		if (isArray) {
			Struct newStruct = null;
			if (!arrayStructs.contains(currentType)) {
				newStruct = new Struct(Struct.Array, currentType);
				arrayStructs.add(newStruct);

			} else {
				int i = arrayStructs.lastIndexOf(currentType);
				newStruct = arrayStructs.get(i);
			}

			MySymbolTable.insert(objKind, varDecl.getVarName(), newStruct);

		} else {
			MySymbolTable.insert(objKind, varDecl.getVarName(), currentType);
		}

	}

	public void visit(ConstDeclSingle constDecl) {
		if (MySymbolTable.find(constDecl.getConstName()) != MySymbolTable.noObj) {
			report_error("Vec postoji simbol '" + constDecl.getConstName() + "'", constDecl);
			return;
		}
		int currentTypeKind = currentType.getKind();
		if (currentTypeKind == Struct.Char || currentTypeKind == Struct.Bool || currentTypeKind == Struct.Int) {

			int constType = Struct.None;
			if (constDecl.getInit() instanceof CharConst) {
				constType = Struct.Char;
			} else if (constDecl.getInit() instanceof BoolConst) {
				constType = Struct.Bool;
			} else
				constType = Struct.Int;

			if (constType == currentTypeKind) {
				Obj objConst = MySymbolTable.insert(Obj.Con, constDecl.getConstName(), currentType);
				int val = 0;
				if (constDecl.getInit() instanceof CharConst) {
					val = ((CharConst) constDecl.getInit()).getCharacter();
				} else if (constDecl.getInit() instanceof BoolConst) {
					val = ((BoolConst) constDecl.getInit()).getBoolVal();
				} else if (constDecl.getInit() instanceof NumberConst) {
					val = ((NumberConst) constDecl.getInit()).getNumber();
				}
				objConst.setAdr(val);
			} else {
				report_error("Tip konstante se ne poklapa!", constDecl);
			}

		} else {
			report_error("Tip za deklaraciju konstante nije validan!", constDecl);
		}
	}

	public void visit(ClassName className) {

		if (MySymbolTable.find(className.getClassName()) != MySymbolTable.noObj) {
			report_error("Vec postoji simbol: " + className.getClassName(), className);
			return;
		}
		classDefinition = true;
		Struct classStruct = new Struct(Struct.Class);
		currentClass = MySymbolTable.insert(Obj.Type, className.getClassName(), classStruct);
		MySymbolTable.openScope();
		currentLevel++;
	}

	public void visit(ClassDecl classDecl) {
		MySymbolTable.chainLocalSymbols(currentClass.getType());
		MySymbolTable.closeScope();
		currentLevel--;
		classDefinition = false;
		currentClass = null;
	}

	public void visit(MethodName methodName) {
		methodDefinition = true;
		methodName.obj = MySymbolTable.insert(Obj.Meth, methodName.getMethodName(), currentMethodReturnType);
		currentMethod = methodName.obj;

		MySymbolTable.openScope();
		currentLevel++;
		if (classDefinition) {
			Obj objThis = MySymbolTable.insert(Obj.Var, "this", currentClass.getType());
			currentMethodFormParams.put("this", objThis);
		}
	}

	public void visit(MethodDecl methodDecl) {
		MySymbolTable.chainLocalSymbols(currentMethod);
		currentMethod.setLevel(currentMethodFormParams.size());
		MySymbolTable.closeScope();
		currentLevel--;
		currentMethod = null;
		currentMethodFormParams.clear();
	}

	public void visit(ReturnTypeExpr returnType) {
		currentMethodReturnType = currentType;
	}

	public void visit(FormParsSingle formPar) {
		Obj objNode = null;
		boolean isArray = false;
		if (formPar.getArrayOpt() instanceof Array)
			isArray = true;

		if (isArray) {

		} else {
			objNode = MySymbolTable.insert(Obj.Var, formPar.getFormParName(), currentType);
		}

		currentMethodFormParams.put(formPar.getFormParName(), objNode);

	}

	public void visit(AssignDes assignDes) {
		Obj obj = MySymbolTable.find(assignDes.getDesignator().getDesName());

		if (!(obj.getKind() == Obj.Var || obj.getKind() == Obj.Fld || obj.getKind() == Obj.Elem)) {
			report_error("Designator mora oznacavati promenljivu, polje ili element niza", assignDes);
		}
		// IS ASSIGNABLE DODATI
		
		if(assignDes.getExpr().struct.getKind() == Struct.Array) {
			if(!(assignDes.getExpr().struct.getElemType().assignableTo(obj.getType()))) {
				report_error("Ne moze se dodeliti ova vrednost ovoj promenljivoj", assignDes);
			}
			return;
		}
		
		if(obj.getType().getKind() == Struct.Array) {
			if(!(assignDes.getExpr().struct.assignableTo(obj.getType().getElemType()))) {
				report_error("Ne moze se dodeliti ova vrednost ovoj promenljivoj", assignDes);
			}
			return;
		}
		if(!(assignDes.getExpr().struct.assignableTo(obj.getType()))) {
			report_error("Ne moze se dodeliti ova vrednost ovoj promenljivoj", assignDes);
		}
	}

	public void visit(IncDes incDes) {
		if (!(incDes.getDesignator().obj.getKind() == Obj.Var || incDes.getDesignator().obj.getKind() == Obj.Fld
				|| incDes.getDesignator().obj.getKind() == Obj.Elem)) {
			report_error("IncDesignator mora oznacavati promenljivu, polje ili element niza", incDes);
		}
		if (incDes.getDesignator().obj.getType().getKind() != Struct.Int) {
			report_error("IncDesignator mora biti tipa integer", incDes);
		}
	}

	public void visit(DecDes decDes) {
		if (!(decDes.getDesignator().obj.getKind() == Obj.Var || decDes.getDesignator().obj.getKind() == Obj.Fld
				|| decDes.getDesignator().obj.getKind() == Obj.Elem)) {
			report_error("DecDesignator mora oznacavati promenljivu, polje ili element niza", decDes);
		}
		if (decDes.getDesignator().obj.getType().getKind() != Struct.Int) {
			report_error("DecDesignator mora biti tipa integer", decDes);
		}
	}

	public void visit(Designator des) {
		String symbolName = des.getDesName();
		Obj obj = MySymbolTable.find(symbolName);
		des.obj = obj;
		if (des.getDesList() instanceof DesListExpr) {
			if (((DesListExpr) des.getDesList()).getDesElement() instanceof ArrayIdent) {
				report_found("Element niza - ", obj, des);
				//((DesListExpr) des.getDesList()).getDesElement().struct = obj.getType().getElemType();
			}
				
		} else if (obj != Tab.noObj) {

			if (obj.getKind() == Obj.Con)
				report_found("Simbolicka konstanta - ", obj, des);
			else if (obj.getKind() == Obj.Var) {
				if (obj.getLevel() == 0)
					report_found("Globalna promenljiva - ", obj, des);
				else if (currentMethodFormParams.containsKey(symbolName))
					report_found("Formalni parametar fje - ", obj, des);
				else
					report_found("Lokalna promenljiva - ", obj, des);
			}
		} else
			report_error("Simbol '" + symbolName + "' nije definisan", des);
	}

	public void visit(FuncDes funcDes) {
		String methodName = funcDes.getDesignator().getDesName();
		if (MySymbolTable.find(methodName).getKind() != Obj.Meth)
			report_error("Pogresno ime kod poziva funkcije", funcDes);

		if (funcDes.getActParsOpt() instanceof NoActPars) {
			if (MySymbolTable.find(methodName).getLevel() > 0)
				report_error("Prosledjen broj parametara fji ne odgovara deklaraciji", funcDes);
		} // FALI JOS MNOGO

	}

	public void visit(DoStatement doStatement) {
		doWhileCnt++;
	}

	public void visit(DoWhileStatement doWhileStatement) {
		if (doWhileStatement.getCondition().struct.getKind() != Struct.Bool)
			report_error("Condition u Do-While statementu mora da bude Bool tipa", doWhileStatement);
		doWhileCnt--;
	}

	public void visit(BreakStatement breakStatement) {
		if (doWhileCnt == 0)
			report_error("Break moze da se koristi samo unutar Do-While petlje", breakStatement);
	}

	public void visit(ContinueStatement cont) {
		if (doWhileCnt == 0)
			report_error("Continue moze da se koristi samo unutar Do-While petlje", cont);
	}

	public void visit(ReadStatement readStatement) {
		if (!(readStatement.getDesignator().obj.getKind() == Obj.Var
				|| readStatement.getDesignator().obj.getKind() == Obj.Fld
				|| readStatement.getDesignator().obj.getKind() == Obj.Elem)) {
			report_error("ReadDesignator mora oznacavati promenljivu, polje ili element niza", readStatement);
		}
		if (!(readStatement.getDesignator().obj.getType().getKind() == Struct.Int
				|| readStatement.getDesignator().obj.getType().getKind() == Struct.Char
				|| readStatement.getDesignator().obj.getType().getKind() == Struct.Bool)) {
			report_error("ReadDesignator mora biti tipa integer, char ili bool", readStatement);
		}
	}

	public void visit(PrintStatement printStatement) {
		if (!(printStatement.getExpr().struct.getKind() == Struct.Int
				|| printStatement.getExpr().struct.getKind() == Struct.Char
				|| printStatement.getExpr().struct.getKind() == Struct.Bool)) {
			report_error("Expr u Print Statementu mora biti tipa integer, char ili bool", printStatement);
		}
	}

	public void visit(ReturnStatement returnStatement) {
		if (methodDefinition) {
			if (returnStatement.getReturnExprOpt() instanceof NoExpression) {
				if (!(currentMethodReturnType.equals(MySymbolTable.noType)))
					report_error("Metoda mora imati povratnu vrednost", returnStatement);
			}
		} else
			report_error("Return moze da se koristi samo unutar metode", returnStatement);
	}

	public void visit(RetExpr returnExpr) {
		returnExpr.struct = returnExpr.getExpr().struct;
		if (!(returnExpr.struct.equals(currentMethodReturnType))) {
			report_error("Tip povratne vrednosti koju metoda vraca se ne poklapa sa deklaracijom", returnExpr);
		}
	}

	public void visit(CondOr cond) {
		cond.struct = cond.getCondTerm().struct;
		if (cond.struct.getKind() != Struct.Bool)
			report_error("Tip Condition-a mora biti Bool", cond);
	}
	
	public void visit(CondTermSingle cond) {
		cond.struct = cond.getCondTerm().struct;
		if (cond.struct.getKind() != Struct.Bool)
			report_error("Tip Condition-a mora biti Bool", cond);
	}
	
	public void visit(CondAnd cond) {
		cond.struct = cond.getCondFact().obj.getType();
		
	}
	
	public void visit(CondFactSingle cond) {
		cond.struct = cond.getCondFact().obj.getType();
		
		
	}

	public void visit(CondFactRelop condFact) {
		Struct expr1Type = condFact.getExpr().struct;
		Struct expr2Type = condFact.getExpr1().struct;
		condFact.obj = new Obj(0,"", MySymbolTable.boolType);
		if (!expr1Type.compatibleWith(expr2Type))
			report_error("Tip izraza kod relacionog operatora se ne poklapa", condFact);

		if ((expr1Type.getKind() == Struct.Array && expr2Type.getKind() == Struct.Array
				&& !(condFact.getRelop() instanceof EqualRel || condFact.getRelop() instanceof NotEqualRel)))
			report_error("Tip relacionog operatora nije odgovarajuci za ove tipove podataka", condFact);

		if ((expr1Type.getKind() == Struct.Class && expr2Type.getKind() == Struct.Class
				&& !(condFact.getRelop() instanceof EqualRel || condFact.getRelop() instanceof NotEqualRel)))
			report_error("Tip relacionog operatora nije odgovarajuci za ove tipove podataka", condFact);
	}
	
	public void visit(CondFactNoRelop cond) {
		Struct expr1Type = cond.getExpr().struct;
		cond.obj = new Obj(0,"", expr1Type);
		if (expr1Type !=MySymbolTable.boolType)
			report_error("Tip Condition-a mora biti Bool", cond);
	}

	public void visit(NegativeTerm expr) {
		expr.struct = expr.getTerm().struct;
		if (expr.getTerm().struct.getKind() != Struct.Int)
			report_error("Znak '-' mora da se nalazi ispred integera", expr);
	}

	public void visit(NotFirstTerm expr) {
		Struct exprType = expr.getExpr().struct;
		Struct termType = expr.getTerm().struct;

		expr.struct = expr.getTerm().struct;

		if (!exprType.compatibleWith(termType))
			report_error("Tipovi moraju biti kompatibilni", expr);

		if (exprType.getKind() != Struct.Int || termType.getKind() != Struct.Int)
			report_error("Tip podatka kod sabiranja mora biti integer", expr);

	}

	public void visit(TermExpr term) {
		Struct factorType = term.getFactor().struct;
		Struct termType = term.getTerm().struct;

		term.struct = term.getFactor().struct;

		if (factorType.getKind() != Struct.Int || termType.getKind() != Struct.Int) {
				report_error("Tip podatka kod matematickih operacija mora biti integer", term);
		}
			
	}

	public void visit(TermFactor term) {
		term.struct = term.getFactor().struct;
	}
	
	

	public void visit(NewArrayFactor newArray) {
		// proveri
		newArray.struct = newArray.getType().struct;
		if (newArray.getExpr().struct.getKind() != Struct.Int)
			report_error("Tip podatka kod operatora new[] mora biti integer", newArray);

	}

	public void visit(ArrayIdent array) {
		Struct arrayIdType = null;
		//if(array.getExpr().struct.getKind() == Struct.Array) {
			//arrayIdType = array.getExpr().struct.getElemType();
			//array.getExpr().struct = array.getExpr().struct.getElemType();
		//}
			
		//else
			arrayIdType = array.getExpr().struct;
		
		int typeExpr = arrayIdType.getKind();

		if (typeExpr != Struct.Int) {
			if (typeExpr == Struct.Array && arrayIdType.getElemType().getKind() == Struct.Int)
				return;
			else
				report_error("Pristupanje elementu niza moze samo pomocu integera", array);
		}

	}

	public void visit(PositiveTerm expr) {
		expr.struct = expr.getTerm().struct;
	}

	public void visit(VarFactor varFactor) {
		if(varFactor.getDesignator().obj.getType().getKind() == Struct.Array)
			varFactor.struct = varFactor.getDesignator().obj.getType().getElemType();
		else varFactor.struct = varFactor.getDesignator().obj.getType();
	}

	public void visit(MethodFactor methFactor) {
		methFactor.struct = methFactor.getDesignator().obj.getType();
	}

	public void visit(ExprFactor exprFactor) {
		exprFactor.struct = exprFactor.getExpr().struct;
	}

	public void visit(ConstFactor constFactor) {

		Init constInit = constFactor.getInit();
		Obj typeNode = MySymbolTable.noObj;
		if (constInit instanceof BoolConst) {
			typeNode = MySymbolTable.find("bool");
			constInit.struct = typeNode.getType();
		} else if (constInit instanceof CharConst) {
			typeNode = MySymbolTable.find("char");
			constInit.struct = typeNode.getType();
		} else if (constInit instanceof NumberConst) {
			typeNode = MySymbolTable.find("int");
			constInit.struct = typeNode.getType();
		}
		constFactor.struct = constInit.struct;
	}

}
