package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Solucao;

public class CADistancia extends CA
{
	double distLearningIdeal;
	double novoEta;
	double Eb;
	
	public CADistancia(Instancia instancia, Config config, Double max)
	{
		super(config);
		this.distLearningIdeal=config.getDistLearning()*config.getDistM();
		this.eta=config.getDistLearning();
	}

	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(fase2)
			return false;
		else
		{
			if(qtnPassouReal>numIterUpdate)
			{
				novoEta=(distLearningIdeal/mDistLearnig.getMediaDinam())*eta;
				eta+=(novoEta-eta);
				eta=Math.max(eta, 0.01);
			}
			
			update(solucao.f);
			
			limiarF=(int)(mFBL.getMediaDinam()+((1-eta)*(teto-mFBL.getMediaDinam())));
			if(solucao.f>limiarF)
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
