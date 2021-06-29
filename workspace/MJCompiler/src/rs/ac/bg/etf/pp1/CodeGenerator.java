package rs.ac.bg.etf.pp1;

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
	
	

/* public void visit(IfStatement ifStmt) {
		Condition cond = ((CorrectCondition) ifStmt.getConditionCorrect()).getCondition();
		boolean elseExists = false;
		int jmpAddr;
		if (ifStmt.getElseOpt() instanceof ElseStmt) {
			/*OVAJ KOD SE ODNOSI NA ULAZAK U THEN GRANU
			//ako postoji else, fixuje se skok posle then-a da skace na posle else (ovo gde smo sad)
			//ako ne postoji else, ostace jmp(0) sto je okej jer se odmah izvrsava sledeca naredba posle then-a (vidi visit elseKeyword)
			Code.put2(endOfThenAddr + 1, Code.pc - endOfThenAddr);
			elseExists = true;
		} else {
			/*AKO SE NE UDJE U THEN GRANU MORAMO DA OBEZBEDIMO PRESKAKANJE THEN GRANE
			//ako postoji else, skace se na nju, obezbedjeno u visit ElseKeyword
			//ako ne postoji else, skace se odmah ovde gde smo sad (drugi arg u zagradi je pomeraj za koliko se skace)
			Code.put2(currFixupAddr, Code.pc + 1 - currFixupAddr);
		}
		
		if(elseExists) {
			jmpAddr = elseAddr;
		} else {
			jmpAddr = Code.pc;
		}

		if (cond instanceof CondOr) {

			CondTerm condTerm = ((CondOr) cond).getCondTerm();

			if (condTerm instanceof CondAnd) {
				// YES OR YES AND
				CondFact condFact = ((CondAnd) condTerm).getCondFact();
				
			} else {
				// YES OR NO AND

			}
			
			

		} else {

			CondTerm condTerm = ((CondTermSingle) cond).getCondTerm();

			if (condTerm instanceof CondAnd) {
				// NO OR YES AND
				CondFact condFact = ((CondAnd) condTerm).getCondFact();
				
			} else {
				// NO OR NO AND
				CondFact condFact = ((CondFactSingle) condTerm).getCondFact();

				if (condFact instanceof CondFactRelop) {
					//ako ima neki relacioni operator, moramo da azuriramo operacioni kod
					Relop relOp = ((CondFactRelop) condFact).getRelop();
					int relopCode = getRelop(relOp);
					int inverseRelop = Code.inverse[relopCode];
					Code.put2(currFixupAddr-1, (Code.jcc + inverseRelop) << 8); //azuriranje op koda
					Code.put2(currFixupAddr, jmpAddr + 1 - currFixupAddr); //azuriranje adrese
					
				} else {
					//nema nikakav relacioni operator, ne moramo da azuriramo operacioni kod
					Code.put2(currFixupAddr, jmpAddr + 1 - currFixupAddr); //azuriranje adese
				}

			}

		}

	}
	*/
/*	
public void visit(IfStatement ifStmt) {
		Condition cond = ((CorrectCondition) ifStmt.getConditionCorrect()).getCondition();
		
		if (ifStmt.getElseOpt() instanceof ElseStmt) {
			//OVAJ KOD SE ODNOSI NA ULAZAK U THEN GRANU
			//ako postoji else, fixuje se skok posle then-a da skace na posle else (ovo gde smo sad)
			Code.put2(endOfThenAddr - 2, Code.pc + 3 - endOfThenAddr);
		
		} 
		
		boolean orExists = false;
		
		if(cond instanceof CondOr) {
			orExists = true;
			CondTerm term = ((CondOr)cond).getCondTerm();
			CondFact rightMostFact;
			boolean andExists = false;
			boolean singleCondTerm = false;
			
			if(term instanceof CondAnd) {
				andExists = true;
				rightMostFact = ((CondAnd)term).getCondFact();
				term = ((CondAnd)term).getCondTerm();
			}
			else {
				singleCondTerm = true;
				rightMostFact = ((CondFactSingle)term).getCondFact();
			}
			int relAddr = rightMostFact.obj.getKind();
			
			
			if (rightMostFact instanceof CondFactRelop) {
				//ako ima neki relacioni operator, moramo da azuriramo operacioni kod
				int inverseRelop = Code.inverse[getRelop(((CondFactRelop) rightMostFact).getRelop())];
				Code.put2(relAddr-1, (Code.jcc + inverseRelop) << 8); //azuriranje op koda
				Code.put2(relAddr, endOfThenAddr + 1 - relAddr); //azuriranje adrese
				
			} else {
				//azuriramo op kod jer je ovo skroz desni uslov kod ||, znaci da nijedan pre njega nije zadovoljen, radi se inverzija
				Code.put2(relAddr-1, (Code.jcc + Code.eq) << 8);
				Code.put2(relAddr, endOfThenAddr + 1 - relAddr); //azuriranje adese
			}
			
			
			while(term instanceof CondAnd) {
				CondFact fact = ((CondAnd)term).getCondFact();
				
				handleCondFact(fact, andExists, orExists, endOfThenAddr);
				
				
				term = ((CondAnd)term).getCondTerm();
			}
			
			if(!singleCondTerm) {
				CondFact fact = ((CondAnd)term).getCondFact();
				
				handleCondFact(fact, andExists, orExists, endOfThenAddr);
			}
			
			cond = ((CondOr)cond).getCondition();
			andExists = false;
			
		} else {
			
			CondTerm condTerm = ((CondTermSingle) cond).getCondTerm();
			int relAddr = 0;
			if (condTerm instanceof CondAnd) {

				CondFact condFact = ((CondAnd) condTerm).getCondFact();
				relAddr = condFact.obj.getKind();
				if (condFact instanceof CondFactRelop) {
					int inverseRelop = Code.inverse[getRelop(((CondFactRelop) condFact).getRelop())];
					Code.put2(relAddr - 1, (Code.jcc + inverseRelop) << 8);
					Code.put2(relAddr, endOfThenAddr + 1 - relAddr);

				} else {
					Code.put2(relAddr - 1, (Code.jcc + Code.eq) << 8);
					Code.put2(relAddr, endOfThenAddr + 1 - relAddr);
				}

				condTerm = ((CondAnd) condTerm).getCondTerm();
			} else {
				relAddr = endOfThenAddr;
			}
			
			
			while (condTerm instanceof CondAnd) {
				CondFact condFact = ((CondAnd) condTerm).getCondFact();
				int myRelAddr = condFact.obj.getKind();
				if(condFact instanceof CondFactRelop) {
					int inverseRelop = Code.inverse[getRelop(((CondFactRelop) condFact).getRelop())];
					Code.put2(myRelAddr - 1, (Code.jcc + inverseRelop) << 8); // azuriranje op koda
					Code.put2(myRelAddr, endOfThenAddr + 1 - myRelAddr); // azuriranje adrese
				} else {
					
				}
				
				
				
				
				condTerm = ((CondAnd) condTerm).getCondTerm();
			}
			
			CondFact condFact = ((CondFactSingle) condTerm).getCondFact();
			handleCondFact(condFact, false, false, relAddr);
			return;
			
		}
		
		
		
		while(cond instanceof CondOr) {
			//handle cond term
			CondTerm condTerm = ((CondTermSingle)cond).getCondTerm();
			
			handleCondTerm(condTerm, orExists);
						
			cond = ((CondOr)cond).getCondition();
		}
		
		
		
		//handle cond term
		CondTerm condTerm = ((CondTermSingle)cond).getCondTerm();
		if(condTerm instanceof CondAnd) {
			handleCondTerm(condTerm, orExists);		
		} else {
			CondFact condFact = ((CondFactSingle) condTerm).getCondFact();
			int relAddr = condFact.obj.getKind();
			if(orExists) {
				//SKACE SE NA THEN GRANU
				if (condFact instanceof CondFactRelop) {
					Relop relOp = ((CondFactRelop) condFact).getRelop();
					int relopCode = getRelop(relOp);
					//int inverseRelop = Code.inverse[relopCode];
					Code.put2(relAddr-1, (Code.jcc + relopCode) << 8); //azuriranje op koda
					Code.put2(relAddr, startOfThen + 1 - relAddr); //azuriranje adrese
					
				} else {
					Code.put2(relAddr, startOfThen + 1 - relAddr); //azuriranje adese
				}
			} else {
				//SKACE SE NA ELSE GRANU
				if (condFact instanceof CondFactRelop) {
					Relop relOp = ((CondFactRelop) condFact).getRelop();
					int relopCode = getRelop(relOp);
					int inverseRelop = Code.inverse[relopCode];
					Code.put2(relAddr-1, (Code.jcc + inverseRelop) << 8); //azuriranje op koda
					Code.put2(relAddr, endOfThenAddr + 1 - relAddr); //azuriranje adrese
					
				} else {
					Code.put2(relAddr - 1, (Code.jcc + Code.eq) << 8);
					Code.put2(relAddr, endOfThenAddr + 1 - relAddr); //azuriranje adese
				}
			}

			
		}
		
		
	}

	public void handleCondFact(CondFact condFact, boolean andExists, boolean orExists, int prevRelOpAddr) {
		int relAddr = condFact.obj.getKind();
		if (condFact instanceof CondFactRelop) {
			if (orExists) {
				int relop = getRelop(((CondFactRelop) condFact).getRelop());
				Code.put2(relAddr - 1, (Code.jcc + relop) << 8); // azuriranje op koda
				Code.put2(relAddr, startOfThen + 1 - relAddr); // azuriranje adrese
			} else if (andExists) {
				int inverseRelop = Code.inverse[getRelop(((CondFactRelop) condFact).getRelop())];
				Code.put2(relAddr - 1, (Code.jcc + inverseRelop) << 8); // azuriranje op koda
				Code.put2(relAddr, prevRelOpAddr + 1 - relAddr); // azuriranje adrese
			} else {
				int inverseRelop = Code.inverse[getRelop(((CondFactRelop) condFact).getRelop())];
				Code.put2(relAddr - 1, (Code.jcc + inverseRelop) << 8); // azuriranje op koda
				Code.put2(relAddr, endOfThenAddr + 1 - relAddr); // azuriranje adrese
			}
		} else {
			if (orExists) {
				Code.put2(relAddr, startOfThen + 1 - relAddr); // azuriranje adese
			} else if (andExists) {
				Code.put2(relAddr - 1, (Code.jcc + Code.eq) << 8);
				Code.put2(relAddr, prevRelOpAddr + 1 - relAddr); // azuriranje adese
			} else {
				Code.put2(relAddr - 1, (Code.jcc + Code.eq) << 8);
				Code.put2(relAddr, endOfThenAddr + 1 - relAddr); // azuriranje adese
			}
		}
	}

	public void handleCondTerm(CondTerm condTerm, boolean orExists) {
		boolean andExists = false;
		int prevRelop = 0;
		
		if(condTerm instanceof CondAnd) {
			andExists = true;
			CondFact condFact = ((CondAnd) condTerm).getCondFact();
			int relop = condFact.obj.getKind();
			prevRelop = relop + 3;
				if (condFact instanceof CondFactRelop) {
					//ako ima neki relacioni operator, moramo da azuriramo operacioni kod
					Relop relOp = ((CondFactRelop) condFact).getRelop();
					int relopCode = getRelop(relOp);
					int inverseRelop = Code.inverse[relopCode];
					Code.put2(relop-1, (Code.jcc + inverseRelop) << 8); //azuriranje op koda
					Code.put2(relop, prevRelop + 1 - relop); //azuriranje adrese
					
				} else {
					Code.put2(relop, (Code.jcc + Code.eq) << 8);
					Code.put2(relop, prevRelop + 1 - relop); //azuriranje adese
				}
				condTerm = ((CondAnd) condTerm).getCondTerm();
		}
		
		while (condTerm instanceof CondAnd) {
			CondFact condFact = ((CondAnd) condTerm).getCondFact();
			handleCondFact(condFact, true, orExists, prevRelop);
			condTerm = ((CondAnd) condTerm).getCondTerm();
		}
		
		CondFact condFact = ((CondFactSingle) condTerm).getCondFact();
		handleCondFact(condFact, andExists, orExists, prevRelop);
		
		
	}	
	*/


	public void visit(IfStatement ifStmt) {
		CondFact condFact = ((CorrectCondition) ifStmt.getConditionCorrect()).getCondFact();
		
		if (ifStmt.getElseOpt() instanceof ElseStmt) {
			//OVAJ KOD SE ODNOSI NA ULAZAK U THEN GRANU
			//ako postoji else, fixuje se skok posle then-a da skace na posle else (ovo gde smo sad)
			Code.put2(endOfThenAddr - 2, Code.pc + 3 - endOfThenAddr);
		}
		
		if (condFact instanceof CondFactRelop) {
			int inverseRelop = Code.inverse[getRelop(((CondFactRelop) condFact).getRelop())];
			Code.put2(currFixupAddr - 1, (Code.jcc + inverseRelop) << 8);
			Code.put2(currFixupAddr, endOfThenAddr + 1 - currFixupAddr);

		} else {
			Code.put2(currFixupAddr - 1, (Code.jcc + Code.eq) << 8);
			Code.put2(currFixupAddr, endOfThenAddr + 1 - currFixupAddr);
		}
		
		
	}
	
	
	public int getRelop(Relop relOp) {
		if (relOp instanceof EqualRel) {
			return Code.eq;
		} else if (relOp instanceof NotEqualRel) {
			return Code.ne;
		} else if (relOp instanceof GreaterEqRel) {
			return Code.ge;
		} else if (relOp instanceof GreaterRel) {
			return Code.gt;
		} else if (relOp instanceof LessEqRel) {
			return Code.le;
		} else if (relOp instanceof LessRel) {
			return Code.lt;
		}

		return -1; //nikad ne dolazi dovde
	}
	
	int currFixupAddr = 0;
	int endOfThenAddr = 0;
	int startOfThen = 0;
	int elseAddr = 0;
	
	public void visit(CorrectCondition cond) {
		startOfThen = Code.pc;
	}
	
	public void visit(ThenStmt thenStmt) {
		//stavlja se jmp u slucaju da je usao u then granu preskace se else
		
		Code.putJump(Code.pc + 3);
		endOfThenAddr = Code.pc;
	}
	
	public void visit(ElseKeyword elseStmt) {		
		//ako nije ispunjen uslov ovo je adresa za skakanje
		elseAddr = Code.pc;
	}

	public void visit(CondFactRelop fact) {
		Code.putFalseJump(Code.eq, 0);
		currFixupAddr = Code.pc - 2;
		fact.obj = new Obj(currFixupAddr, "", null);
	}

	public void visit(CondFactNoRelop fact) {
		
		Code.loadConst(0); //poredi da li je == 0, ako je == 0 onda skace
		Code.putFalseJump(Code.eq, 0);
		currFixupAddr = Code.pc - 2;
		fact.obj = new Obj(currFixupAddr, "", null);
	}

}
