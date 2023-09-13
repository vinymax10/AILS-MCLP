package SearchMethod;

import DiversityControl.AcceptanceCriteria;
import Solution.Solucao;

public interface Algorithm
{
	public double getGap();

	public AcceptanceCriteria getCriterioAceitacao();

	public double getMelhorF();

	public int getIterador();

	public void setTime(boolean time);

	public void setPrint(boolean print);

	public void setAuditoria(boolean auditoria);

	public void procurar();

	public Solucao getMelhorSolucao();

	public double getTempoMF();
}
