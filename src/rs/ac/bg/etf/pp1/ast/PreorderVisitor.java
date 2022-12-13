package rs.ac.bg.etf.pp1.ast;

public class PreorderVisitor extends VisitorAdaptor {

	@Override
	public void visit(ActualParams ActualParams) {
		if (ActualParams.getActualParamList() != null) { 
			ActualParams.getActualParamList().accept(this);
		}
		if (ActualParams.getExpr() != null) {
			ActualParams.getExpr().accept(this);
		}
	}
	
	@Override
	public void visit(ActualParamList ActualParamList) {
		// TODO Auto-generated method stub
		super.visit(ActualParamList);
	}
	
	@Override
	public void visit(Actuals Actuals) {
		// TODO Auto-generated method stub
		super.visit(Actuals);
	}
	
	@Override
	public void visit(ActualPars ActualPars) {
		// TODO Auto-generated method stub
		super.visit(ActualPars);
	}
}
