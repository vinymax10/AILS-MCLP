package SearchMethod;

import java.text.DecimalFormat;
import java.util.Arrays;

import Perturbation.HeuristicaAdicao;
import Perturbation.TipoPerturbacao;

public class Config implements Cloneable
{
	DecimalFormat deci = new DecimalFormat("0.000");
	TipoPerturbacao perturbacao[];
	int dBeta;
	int gamma;
	double etaMin;
	int sigma;

	HeuristicaAdicao heuristicaAdicao[];

	public Config()
	{
//		----------------------------Main----------------------------
		this.perturbacao = new TipoPerturbacao[2];
		this.perturbacao[0] = TipoPerturbacao.Random;
		this.perturbacao[1] = TipoPerturbacao.Concentric;

		this.gamma = 30;
		this.dBeta = 10;

		this.etaMin = 0.1;

		this.sigma = 40;

		this.heuristicaAdicao = new HeuristicaAdicao[2];
		this.heuristicaAdicao[0] = HeuristicaAdicao.Aleatorio;
		this.heuristicaAdicao[1] = HeuristicaAdicao.Raio;

	}

	public Config clone()
	{
		try
		{
			return (Config) super.clone();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String toString()
	{
		return "Config " + "\n----------------------------Main----------------------------" + "\nperturbacao: " + Arrays.toString(perturbacao) + "\ngamma: "
		+ gamma + "\ndBeta: " + dBeta + "\netaMin: " + deci.format(etaMin) + "\nsigma: " + sigma + "\nheuristicaAdicao: " + Arrays.toString(heuristicaAdicao);
	}

	// ---------gets and sets-----------

	public TipoPerturbacao[] getPerturbacao()
	{
		return perturbacao;
	}

	public void setPerturbacao(TipoPerturbacao[] perturbacao)
	{
		this.perturbacao = perturbacao;
	}

	public int getDBeta()
	{
		return dBeta;
	}

	public void setDBeta(int dBeta)
	{
		this.dBeta = dBeta;
	}

	public int getGamma()
	{
		return gamma;
	}

	public void setGamma(int gamma)
	{
		this.gamma = gamma;
	}

	public double getEtaMin()
	{
		return etaMin;
	}

	public void setEtaMin(double etaMin)
	{
		this.etaMin = etaMin;
	}

	public int getSigma()
	{
		return sigma;
	}

	public void setSigma(int sigma)
	{
		this.sigma = sigma;
	}

	public HeuristicaAdicao[] getHeuristicaAdicao()
	{
		return heuristicaAdicao;
	}

	public void setHeuristicaAdicao(HeuristicaAdicao[] heuristicaAdicao)
	{
		this.heuristicaAdicao = heuristicaAdicao;
	}

}
