package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.util.Log4JUtils;

public class MJTest {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}

	public static void main(String[] args) throws Exception {

		MyCompiler compiler = new MyCompiler();
		
		String sourceFilePath = "test/syntax/test21.mj";
		String outputFilePath = "";
		System.out.println("Lista gresaka:\n");
		System.out.println((compiler.compile(sourceFilePath, outputFilePath )).toString());

	}

}
