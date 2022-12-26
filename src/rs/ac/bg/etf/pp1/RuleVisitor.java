package rs.ac.bg.etf.pp1;
import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.StatementPrintWithoutWidth;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class RuleVisitor extends VisitorAdaptor {
	
	Logger log = Logger.getLogger(getClass());
	int count = 0;
	
	public void visit(StatementPrintWithoutWidth StatementPrintWithoutWidth) {
		count++;
		log.info("Prepoznata naredba print!");
	}
	
}
