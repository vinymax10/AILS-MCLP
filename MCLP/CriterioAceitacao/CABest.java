package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Solucao;

public class CABest extends CA
{
	double melhorF=Integer.MIN_VALUE;
	
	public CABest(Instancia instancia, Config config, Double max)
	{
		super(config);
	}
	
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(fase2)
			return false;
		else
		{
			update(solucao.f);
			
			if(solucao.f>melhorF)
				melhorF=solucao.f;
			
			if(solucao.f>=melhorF)
			{
				qtnPassouReal++;
				mDistLearnig.setValor(distanciaBLEdge);
				return true;
			}
			else
				return false;
		}
	}
}
