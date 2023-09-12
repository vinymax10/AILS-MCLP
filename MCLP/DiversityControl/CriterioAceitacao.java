package DiversityControl;

import SearchMethod.Media;
import Solution.Solucao;

public interface CriterioAceitacao 
{
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge);
	public double getEta();
	public void setEta(double eta);
	public Media getmFluxo();
	public Media getmEta();
	public double getLimiarF();
	public double getFluxoAtual();
	public void setFluxoIdeal(double fluxoIdeal);
	public Media getmDistLearnig();
	
}
