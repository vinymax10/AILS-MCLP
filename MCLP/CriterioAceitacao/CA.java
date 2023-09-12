package CriterioAceitacao;

import MetaHeuristicas.Config;
import MetaHeuristicas.Media;
import MetaHeuristicas.Solucao;

public abstract class CA 
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
	
	public CA(Config config)
	{
		this.numIterUpdate=config.getNumIterUpdate();
		this.eta=config.getEta();
		this.mFBL=new Media(config.getNumIterUpdate());
		this.mFluxo=new Media(config.getNumIterUpdate());
		this.mEta=new Media(config.getNumIterUpdate());
		this.mDistLearnig=new Media(config.getNumIterUpdate());
	}
	
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		return false;
	}
	
	public boolean aceitaSolucao(int f)
	{
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
