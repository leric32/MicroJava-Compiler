package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.SingleFormalParameter;
import rs.ac.bg.etf.pp1.ast.VarDeclaration;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class CounterVisitor extends VisitorAdaptor {
	
	protected int count;
	
	public int getCount() {
		return count;
	}
	
	public static class FormParamCounter extends CounterVisitor {

		@Override
		public void visit(SingleFormalParameter singleFormalParameter) {
			count++;
		}		
	}
	
	public static class VarCounter extends CounterVisitor {		
		@Override
		public void visit(VarDeclaration varDeclaration) {
			count++;
		}
	}
}
