package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;

public class MyCompiler implements Compiler {
	public static List<CompilerError> errors = new LinkedList<CompilerError>();
	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	@Override
	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		// TODO Auto-generated method stub
		Logger log = Logger.getLogger(MyCompiler.class);
		
		Reader br = null;
		try {
			File sourceCode = new File(sourceFilePath);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());

			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);

			MJParser p = new MJParser(lexer);
			Symbol s = p.parse(); // pocetak parsiranja

			Program prog = (Program) (s.value);
			// ispis sintaksnog stabla
			System.out.println("==================SYNTAX TREE==================");
			System.out.println(prog.toString(""));
			log.info("===================================");
			
			System.out.println("======================SEMANTIC ANALYSIS========================");	
			MySymbolTable.init();
			SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
			prog.traverseBottomUp(semanticAnalyzer);
			MySymbolTable.dump();
			
			if(semanticAnalyzer.passed()) {
				System.out.println("Parsiranje je uspesno zavrseno!");
				System.out.println("======================CODE GENERATION========================");	
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = SemanticAnalyzer.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				File objFile = new File(outputFilePath);
                if (objFile.exists()) objFile.delete();
                Code.write(new FileOutputStream(objFile));
			} else {
				System.out.println("Parsiranje NIJE uspesno zavrseno!");
			}
			return errors;

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null)
				try {
					br.close();
				} catch (IOException e1) {
					log.error(e1.getMessage(), e1);
				}
		}
		return null;
	}

}
