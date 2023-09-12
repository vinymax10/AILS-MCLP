package DiversityControl;

import Data.Instancia;
import SearchMethod.Config;
import SearchMethod.Media;
import Solution.Solucao;

public class AcceptanceCriteria 
{
	double limiarF;
	Media mFBL,mFluxo,mEta,mDistLearnig;
	int numIterUpdate;
	double eta;
	double teto=Integer.MAX_VALUE,tetoNovo=Integer.MAX_VALUE;
	double floor=Integer.MIN_VALUE,floorNovo=Integer.MIN_VALUE;

	int qtnPassouReal;
	int iterador=0,ultIterUpdate;
	double fluxoAtual;
	double etaMin,etaMax;
	long ini;
	double max;
	double alfa=1;
	int iteradorGlobal=0;
	
	public AcceptanceCriteria(Instancia instancia, Config config, Double max)
	{
		this.numIterUpdate=config.getNumIterUpdate();
		this.eta=config.getEta();
		this.mFBL=new Media(config.getNumIterUpdate());
		this.mFluxo=new Media(config.getNumIterUpdate());
		this.mEta=new Media(config.getNumIterUpdate());
		this.mDistLearnig=new Media(config.getNumIterUpdate());
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

	public void update(double f)
	{
		iterador++;
		
		if(qtnPassouReal>numIterUpdate)
		{
			fluxoAtual=(double)qtnPassouReal/(double)(iterador-ultIterUpdate);
			mFluxo.setValor(fluxoAtual);
//			System.out.println("mFluxo: "+mFluxo);
			ultIterUpdate=iterador;
			qtnPassouReal=0;
		}
		
		if(iterador%(numIterUpdate)==0)
		{
			teto=tetoNovo;
			tetoNovo=0;
			
			floor=floorNovo;
			floorNovo=Integer.MAX_VALUE;
		}
		
		if(f>tetoNovo)
			tetoNovo=f;
			
		if(f>teto)
			teto=f;
		
		if(f<floorNovo)
			floorNovo=f;
			
		if(f<floor)
			floor=f;
		
		mFBL.setValor(f);
		mEta.setValor(eta);
	}

	public double getEta() {
		return eta;
	}

	public Media getmFluxo() {
		return mFluxo;
	}

	public Media getmEta() {
		return mEta;
	}

	public double getLimiarF() {
		return limiarF;
	}

	public double getFluxoAtual() {
		return fluxoAtual;
	}
	
	public Media getmDistLearnig() {
		return mDistLearnig;
	}
	
	public void setEta(double eta) {
		this.eta = eta;
	}
	
	
	
	@Override
	public String toString() {
		return "CA [limiarF=" + limiarF + ", mFBL=" + mFBL + ", mFluxo=" + mFluxo + ", mEta=" + mEta + ", eta=" + eta
				+ ", teto=" + teto + ", fluxoAtual=" + fluxoAtual + "]";
	}

	public void setFluxoIdeal(double fluxoIdeal) {}
}
