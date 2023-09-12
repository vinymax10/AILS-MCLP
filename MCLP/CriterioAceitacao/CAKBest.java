package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Solucao;

public class CAKBest extends CA
{
	int numChilds;
	Solucao bestChild;
	double bestF=0;
	double bestDistBLEdge;
	
	public CAKBest(Instancia instancia, Config config, Double max)
	{
		super(config);
		this.numChilds=config.getNumChilds();
		this.bestChild=new Solucao(instancia,config);
	}

	
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(fase2)
			return false;
		else
		{
			update(solucao.f);
			
			if(solucao.f>bestF)
			{
				bestChild.clone(solucao);
				bestF=solucao.f;
				bestDistBLEdge=distanciaBLEdge;
			}
			
			if(iterador%numChilds==0)
			{
				mDistLearnig.setValor(bestDistBLEdge);
				
				solucao.clone(bestChild);
				bestF=0;
				qtnPassouReal++;
				
				return true;
			}
			else
				return false;
		}
	}
}
