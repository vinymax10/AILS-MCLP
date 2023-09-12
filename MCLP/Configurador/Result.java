package Configurador;

import java.text.DecimalFormat;

import MetaHeuristicas.Algorithm;

public class Result implements Comparable<Result>
{
	double gap;
	double eta;
	double melhorF;
	double fluxomedio;
	double omega,distC,distHeu,distTabu;
	double distLearning;
	double gapMediaC,gapMediaBL;
	DecimalFormat df=new DecimalFormat("0.0000"); 
	double iterador;
	
	@Override
	public int compareTo(Result x) 
	{
		if(this.gap==x.gap)
			return 0;
		if(this.gap<x.gap)
			return -1;
		return 1;
	}

	public void setDados(Algorithm algorithm) 
	{
		gap=algorithm.getGap();
		eta=algorithm.getCriterioAceitacao().getmEta().getMediaGlobal();
		fluxomedio=algorithm.getCriterioAceitacao().getmFluxo().getMediaGlobal();
		melhorF=algorithm.getMelhorF();
		iterador=algorithm.getIterador();
	}

	@Override
	public String toString() {
		return "Result [gap=" + gap 
				+ ", eta=" + eta + ", melhorF=" + melhorF + ", fluxomedio=" + fluxomedio 
				+" distLearning: "+distLearning+" iterador: "+iterador+"]";
	}

}
