package TesteFinal;

import java.text.DecimalFormat;

import MetaHeuristicas.Algorithm;

public class Result implements Comparable<Result>
{
	double gap;
	double eta;
	double melhorF;
	double fluxomedio;
	double omega,distBL,distC,distHeu;
	double gapMediaBL;
	DecimalFormat df=new DecimalFormat("0.0000"); 
	double distMediaPR=0;
	double tempoMF;
	
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
		distMediaPR=0;
		tempoMF=algorithm.getTempoMF();
	}

	@Override
	public String toString() {
		return "Result [gap=" + gap 
				+ ", eta=" + eta + ", melhorF=" + melhorF + ", fluxomedio=" + fluxomedio 
				+" distMediaPR: "+distMediaPR+"]";
	}

}
