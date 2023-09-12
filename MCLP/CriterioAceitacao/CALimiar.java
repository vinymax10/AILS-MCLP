package CriterioAceitacao;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;
import MetaHeuristicas.Solucao;

public class CALimiar extends CA
{
	public CALimiar(Instancia instancia, Config config, Double max)
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
//			System.out.println(this);
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
