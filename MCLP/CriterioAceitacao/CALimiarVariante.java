package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;
import MetaHeuristicas.Solucao;

public class CALimiarVariante extends CA
{
	double melhorF;
	int interadorMF;
	double etaMin,etaMax;
	double periodicidade;
	
	public CALimiarVariante(Instancia instancia, Config config, Double max)
	{
		super(config);
		this.etaMin=config.getEtaMin();
		this.etaMax=config.getEtaMax();
		this.eta=config.getEtaMin();
		this.periodicidade=config.getPeriodicidade();
	}
	
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(fase2)
			return false;
		else
		{
			update(solucao.f);
			
			if(melhorF<solucao.f)
			{
				melhorF=solucao.f;
				interadorMF=iterador;
				eta=etaMin;
			}
			else if(eta<etaMax)
			{
				eta+=(etaMax-etaMin)/periodicidade;
				if(eta>etaMax)
				{
//					eta=etaMin;
					eta=etaMax;
				}
			}
			
//			System.out.println("eta: "+eta);
			
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
	
	@Override
	public double getEta() {
		return eta;
	}

	@Override
	public Media getmFluxo() {
		return mFluxo;
	}

	@Override
	public Media getmEta() {
		return mEta;
	}

	@Override
	public double getLimiarF() {
		return limiarF;
	}

	@Override
	public double getFluxoAtual() {
		return fluxoAtual;
	}
	
	public Media getmDistLearnig() {
		return mDistLearnig;
	}
	
	public void setEta(double eta) {
		this.eta = eta;
	}
	
	public void setFluxoIdeal(double fluxoIdeal) {}
}
