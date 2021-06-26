package rs.ac.bg.etf.pp1;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
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

	private static Map<String, Obj> currentMethodFormParams = new HashMap<String, Obj>();

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" - Greska na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji").append(line);
		log.info(msg.toString());
	}
	
	public void report_found(Obj obj, SyntaxNode node) {
		StringBuilder msg = new StringBuilder();
		int line = (node == null) ? 0 : node.getLine();
		DumpSymbolTableVisitor stv = new DumpSymbolTableVisitor();
		if (line != 0) {
			msg.append("Pretraga na ").append(line).append("(").append(obj.getName()).append("), ");
			stv.visitObjNode(obj);
			msg.append("nadjeno ").append(stv.getOutput()); 
		}
			
		log.info(msg.toString());
	}

	public boolean symbolExists(SyntaxNode varDecl, String varName) {
		Obj sym = MySymbolTable.find(varName);
		if (sym != MySymbolTable.noObj && sym.getLevel() == currentLevel) {
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
		if(mainMethodObj == MySymbolTable.noObj)
			report_error("Program mora imati main metodu", program);
		else {
			if(!mainMethodObj.getType().equals(MySymbolTable.noType))
				report_error("Povratna vrednost main metode mora biti void", program);
			if(mainMethodObj.getLevel() > 0) 
				report_error("Main metoda ne sme da ima argumente", program);
		}
			
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
			//} else if (constDecl.getInit() instanceof BooleanConstant) {
				//constType = Struct.Bool;
			} else
				constType = Struct.Int;

			if (constType == currentTypeKind) {
				Obj objConst = MySymbolTable.insert(Obj.Con, constDecl.getConstName(), currentType);
				int val = 0;
				if (constDecl.getInit() instanceof CharConst) {
					val = ((CharConst)constDecl.getInit()).getCharacter();
				//} else if (constDecl.getInit() instanceof BooleanConstant) {
					//val = ((BooleanConstant)constDecl.getInit()).getBoolConst();					
				} else if (constDecl.getInit() instanceof NumberConst) {
					val = ((NumberConst)constDecl.getInit()).getNumber();
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

	}

	public void visit(ClassDecl classDecl) {
		MySymbolTable.chainLocalSymbols(currentClass.getType());
		MySymbolTable.closeScope();
		classDefinition = false;
		currentClass = null;
	}

	public void visit(MethodName methodName) {
		methodDefinition = true;
		methodName.obj = MySymbolTable.insert(Obj.Meth, methodName.getMethodName(), currentMethodReturnType);
		currentMethod = methodName.obj;

		MySymbolTable.openScope();

		if (classDefinition) {
			Obj objThis = MySymbolTable.insert(Obj.Var, "this", currentClass.getType());
			currentMethodFormParams.put("this", objThis);
		}
	}

	public void visit(MethodDecl methodDecl) {
		MySymbolTable.chainLocalSymbols(currentMethod);
		currentMethod.setLevel(currentMethodFormParams.size());
		MySymbolTable.closeScope();
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
		if (!(assignDes.getDesignator().obj.getKind() == Obj.Var || assignDes.getDesignator().obj.getKind() == Obj.Fld
				|| assignDes.getDesignator().obj.getKind() == Obj.Elem)) {
			report_error("Designator mora oznacavati promenljivu, polje ili element niza", assignDes);
		}
		// IS ASSIGNABLE DODATI
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
		Obj obj = MySymbolTable.find(des.getDesName());

		if (obj == Tab.noObj) {
			report_error("Simbol '" + des.getDesName() + "' nije definisan", des);
		};
		des.obj = obj;
		if(obj.getKind() == Obj.Con || obj.getKind() == Obj.Var)
			report_found(obj,des);

	}

	public void visit(FuncDes funcDes) {
		String methodName = funcDes.getDesignator().getDesName();
		if(MySymbolTable.find(methodName).getKind() != Obj.Meth)
			report_error("Pogresno ime kod poziva funkcije", funcDes);
		
		if (funcDes.getActParsOpt() instanceof NoActPars) {
			if (MySymbolTable.find(methodName).getLevel() > 0)
				report_error("Prosledjen broj parametara fji ne odgovara deklaraciji", funcDes);
		} //FALI JOS MNOGO

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

	public void visit(Condition cond) {
		if (cond.struct.getKind() != Struct.Bool)
			report_error("Tip Condition-a mora biti Bool", cond);
	}

	public void visit(CondFactRelop condFact) {
		Struct expr1Type = condFact.getExpr().struct;
		Struct expr2Type = condFact.getExpr1().struct;
		
		if(!expr1Type.compatibleWith(expr2Type)) 
			report_error("Tip izraza kod relacionog operatora se ne poklapa", condFact);
		
		if((expr1Type.getKind() == Struct.Array && expr2Type.getKind() == Struct.Array 
				&& !(condFact.getRelop() instanceof EqualRel || 
						condFact.getRelop() instanceof NotEqualRel)))
			report_error("Tip relacionog operatora nije odgovarajuci za ove tipove podataka", condFact);
		
		if((expr1Type.getKind() == Struct.Class && expr2Type.getKind() == Struct.Class 
				&& !(condFact.getRelop() instanceof EqualRel || 
						condFact.getRelop() instanceof NotEqualRel)))
			report_error("Tip relacionog operatora nije odgovarajuci za ove tipove podataka", condFact);
	}
	
	public void visit(NegativeTerm term) {
		if(term.getTerm().struct.getKind() != Struct.Int)
			report_error("Znak '-' mora da se nalazi ispred integera", term);
	}
	
	public void visit(NotFirstTerm term) {
		Struct exprType = term.getExpr().struct;
		Struct termType = term.getTerm().struct;
		
		if(!exprType.compatibleWith(termType)) 
			report_error("Tipovi moraju biti kompatibilni", term);
		
		if(exprType.getKind() != Struct.Int || termType.getKind() != Struct.Int)
			report_error("Tip podatka kod sabiranja mora biti integer", term);
		
	}
	
	public void visit(TermExpr term) {
		Struct factorType = term.getFactor().struct;
		Struct termType = term.getTerm().struct;
		if(factorType.getKind() != Struct.Int || termType.getKind() != Struct.Int)
			report_error("Tip podatka kod sabiranja mora biti integer", term);
	}
	
	public void visit(NewArrayFactor newArray) {
		if(newArray.getExpr().struct.getKind() != Struct.Int)
			report_error("Tip podatka kod operatora new[] mora biti integer", newArray);
	}
	
	public void visit(ArrayIdent array) {
		Struct arrayIdType = array.getExpr().struct;
		
		if(arrayIdType.getKind() != Struct.Int)
			report_error("Pristupanje elementu niza moze samo pomocu integera", array);
		
	}
	
	
	
	
	
	
	

	
}
