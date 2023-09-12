package MetaHeuristicas;

import CriterioAceitacao.CA;

public interface Algorithm 
{
	public double getGap();
	public CA getCriterioAceitacao();
	public double getMelhorF();
	public int getIterador();
	public void setTime(boolean time);
	public void setPrint(boolean print);
	public void setAuditoria(boolean auditoria);
	public void procurar();
	public Solucao getMelhorSolucao();
	public double getTempoMF();
}
