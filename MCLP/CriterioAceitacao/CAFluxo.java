package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Solucao;

public class CAFluxo extends CA
{
	double fluxoIdeal;
	double novoEta;
	double qtnPassouIdeal;
	
	public CAFluxo(Instancia instancia, Config config, Double max)
	{
		super(config);
		this.numIterUpdate=config.getNumIterUpdate();
		this.fluxoIdeal=config.getFluxoIdeal();
	}

	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(fase2)
			return false;
		else
		{
			if(qtnPassouReal>numIterUpdate)
			{
				qtnPassouIdeal=fluxoIdeal*(iterador-ultIterUpdate);
				novoEta=(qtnPassouIdeal/qtnPassouReal)*eta;
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
	
	public boolean aceitaSolucao(int f)
	{
		if(qtnPassouReal>numIterUpdate)
		{
			qtnPassouIdeal=fluxoIdeal*(iterador-ultIterUpdate);
			novoEta=(qtnPassouIdeal/qtnPassouReal)*eta;
			eta+=(novoEta-eta);
			eta=Math.max(eta, 0.01);
		}
		
		update(f);
		
		limiarF=(int)(mFBL.getMediaDinam()+((1-eta)*(teto-mFBL.getMediaDinam())));
		if(f>limiarF)
		{
			qtnPassouReal++;
			return true;
		}
		else
			return false;
	}
}
