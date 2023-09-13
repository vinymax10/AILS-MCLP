package DiversityControl;

import Data.Instance;
import SearchMethod.Config;
import SearchMethod.Media;
import Solution.Solucao;

public class AcceptanceCriteria
{
	double limiarF;
	Media mFBL;
	int numIterUpdate;
	double eta;
	double teto = Integer.MAX_VALUE, tetoNovo = Integer.MAX_VALUE;

	int iterador = 0;
	double etaMin;
	long ini;
	double max;
	double alfa = 1;
	int iteradorGlobal = 0;

	public AcceptanceCriteria(Instance instancia, Config config, Double max)
	{
		this.numIterUpdate = config.getGamma();
		this.mFBL = new Media(config.getGamma());
		this.eta = 1;
		this.max = max;
		this.etaMin = config.getEtaMin();
	}

	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(iteradorGlobal == 0)
			ini = System.currentTimeMillis();

		if(!fase2)
			update(solucao.f);

		iteradorGlobal++;
//		--------------------------------------------
//		exp		
		if(iteradorGlobal % numIterUpdate == 0)
		{
			double tempoMaximo = (max);
			double atual = (double) (System.currentTimeMillis() - ini) / 1000;
			double porcentagemTempo = atual / tempoMaximo;
			double total = (double) iteradorGlobal / porcentagemTempo;

			alfa = Math.pow(etaMin, (double) 1 / total);
		}

		eta *= alfa;
		eta = Math.max(eta, etaMin);

		limiarF = (int) (mFBL.getMediaDinam() + ((1 - eta) * (teto - mFBL.getMediaDinam())));
		if(!fase2 && solucao.f > limiarF)
		{
			return true;
		}
		else
			return false;
	}

	public boolean aceitaSolucao(int f)
	{
		if(iteradorGlobal == 0)
			ini = System.currentTimeMillis();

		update(f);

		iteradorGlobal++;
//		--------------------------------------------
//		exp		
		if(iteradorGlobal % numIterUpdate == 0)
		{
			double tempoMaximo = (max);
			double atual = (double) (System.currentTimeMillis() - ini) / 1000;
			double porcentagemTempo = atual / tempoMaximo;
			double total = (double) iteradorGlobal / porcentagemTempo;

			alfa = Math.pow(etaMin, (double) 1 / total);

		}

		eta *= alfa;
		eta = Math.max(eta, etaMin);

		limiarF = (int) (mFBL.getMediaDinam() + ((1 - eta) * (teto - mFBL.getMediaDinam())));
		if(f > limiarF)
		{
			return true;
		}
		else
			return false;
	}

	public void update(double f)
	{
		iterador++;

		if(iterador % (numIterUpdate) == 0)
		{
			teto = tetoNovo;
			tetoNovo = 0;
		}

		if(f > tetoNovo)
			tetoNovo = f;

		if(f > teto)
			teto = f;

		mFBL.setValor(f);
	}

	public double getEta()
	{
		return eta;
	}

	public double getLimiarF()
	{
		return limiarF;
	}

	public void setEta(double eta)
	{
		this.eta = eta;
	}

	@Override
	public String toString()
	{
		return "AcceptanceCriteria [limiarF=" + limiarF + ", mFBL=" + mFBL + ", numIterUpdate=" + numIterUpdate + ", eta=" + eta + ", teto=" + teto
		+ ", tetoNovo=" + tetoNovo + ", iterador=" + iterador + ", etaMin=" + etaMin + ", ini=" + ini + ", max=" + max + ", alfa=" + alfa + ", iteradorGlobal="
		+ iteradorGlobal + "]";
	}

	public void setFluxoIdeal(double fluxoIdeal)
	{
	}
}
