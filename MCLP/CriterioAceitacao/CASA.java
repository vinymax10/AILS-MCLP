package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;
import MetaHeuristicas.Solucao;

public class CASA extends CA
{
//	double sa;
	double etaMin,etaMax;
	long ini;
	double max;
	double alfa=1;
	int iteradorGlobal=0;
	
	public CASA(Instancia instancia, Config config, Double max)
	{
		super(config);
		this.eta=config.getEtaMax();
		this.max=max;
		this.etaMin=config.getEtaMin();
		this.etaMax=config.getEtaMax();
	}
	
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(iteradorGlobal==0)
			ini=System.currentTimeMillis();
		
		if(!fase2)
			update(solucao.f);
		
		iteradorGlobal++;
//		--------------------------------------------
//		exp		
		if(iteradorGlobal%numIterUpdate==0)
		{
			double tempoMaximo=(max);
			double atual=(double)(System.currentTimeMillis()-ini)/1000;
			double porcentagemTempo=atual/tempoMaximo;
			double total=(double)iteradorGlobal/porcentagemTempo;
			
			alfa=Math.pow(etaMin/etaMax, (double) 1/total);
//			alfa=Math.pow(etaMin/etaMax, (double) atual/((double)iteradorGlobal*tempoMaximo));
			
		}
		
		eta*=alfa;
		eta=Math.max(eta, etaMin);

		limiarF=(int)(mFBL.getMediaDinam()+((1-eta)*(teto-mFBL.getMediaDinam())));
		if(!fase2&&solucao.f>limiarF)
		{
			qtnPassouReal++;
			mDistLearnig.setValor(distanciaBLEdge);
			return true;
		}
		else
			return false;
	}
	
	public boolean aceitaSolucao(int f)
	{
		if(iteradorGlobal==0)
			ini=System.currentTimeMillis();
		
		update(f);
		
		iteradorGlobal++;
//		--------------------------------------------
//		exp		
		if(iteradorGlobal%numIterUpdate==0)
		{
			double tempoMaximo=(max);
			double atual=(double)(System.currentTimeMillis()-ini)/1000;
			double porcentagemTempo=atual/tempoMaximo;
			double total=(double)iteradorGlobal/porcentagemTempo;
			
			alfa=Math.pow(etaMin/etaMax, (double) 1/total);
			
		}
		
		eta*=alfa;
		eta=Math.max(eta, etaMin);

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
