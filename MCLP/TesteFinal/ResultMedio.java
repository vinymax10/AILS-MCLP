package TesteFinal;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class ResultMedio implements Comparable<ResultMedio>
{
	double gap=0;
	double eta=0;
	double mediaF=0;
	double iteradorMF;
	double tempoMF;
	int iterador=0;
	double fluxomedio;
	double DP;
	ArrayList<Double>vetGap=new ArrayList<Double>();
	ArrayList<Double>vetSolutions=new ArrayList<Double>();
	double gapMediaBL;
	double omega,distBL,distC,distHeu;
	double bestF,worstF,medianF,UmQ,TresQ;
	double bestGap,worstGap,medianGap,UmQGap,TresQGap;
	double DistBLEdgeGlobal;
	double distMediaPR=0;
	
	DecimalFormat df=new DecimalFormat("0.0000",new DecimalFormatSymbols(Locale.FRANCE)); 
	DecimalFormat df2=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.FRANCE)); 
	
	public void setResult(Result result)
	{
		vetGap.add(result.gap);
		vetSolutions.add(result.melhorF);
		
		iterador++;
		gap=(gap*(iterador-1)+result.gap)/iterador;
		eta=(eta*(iterador-1)+result.eta)/iterador;
		fluxomedio=(fluxomedio*(iterador-1)+result.fluxomedio)/iterador;
		gapMediaBL=(gapMediaBL*(iterador-1)+result.gapMediaBL)/iterador;

		mediaF=(mediaF*(iterador-1)+result.melhorF)/iterador;
		tempoMF=(tempoMF*(iterador-1)+result.tempoMF)/iterador;
		
		omega=(omega*(iterador-1)+result.omega)/iterador;
		distBL=(distBL*(iterador-1)+result.distBL)/iterador;
		distC=(distC*(iterador-1)+result.distC)/iterador;
		distHeu=(distHeu*(iterador-1)+result.distHeu)/iterador;
		distMediaPR=(distMediaPR*(iterador-1)+result.distMediaPR)/iterador;
	}
	
	@Override
	public int compareTo(ResultMedio x) 
	{
		if(this.gap==x.gap)
			return 0;
		if(this.gap<x.gap)
			return -1;
		return 1;
	}

	@Override
	public String toString() {
		return "gap=" + getGap() + ", DP=" + getDP()  + ", bestGap=" + getBestGap() +", UmQGap=" + get1QGap() + ", medianGap=" + getMedianGap() + 
				", TresQGap=" + get3QGap() +  ", worstGap=" + getWorstGap()+ ", bestF=" + getBestF() +", UmQ=" + get1Q() + ", medianF=" + getMedianF() + 
				", TresQ=" + get3Q() +  ", worstF=" + getWorstF()  + ", eta=" + getEta() + ", mediaF=" + getMediaF() 
				+" getGapMediaBL: "+getGapMediaBL()
				+ ", rodadas="	+ iterador + ", fluxomedio=" + getFluxomedio() + " iteradorMF="+ getIteradorMF() +"\n"
				+ " distIdealMedioGlobal: "+getDistIdealMedioGlobal()
				;
	}

	public String getGap() {
		return df.format(gap);
	}
	
	public double getGapValor() {
		return gap;
	}

	public String getEta() {
		return df.format(eta);
	}

	public String getMediaF() {
		return df2.format(mediaF);
	}

	public String getFluxomedio() {
		return df.format(fluxomedio);
	}

	public String getDP() 
	{
		DP=0;
		for (int i = 0; i <vetGap.size(); i++)
			DP+=Math.pow(gap-vetGap.get(i),2);

		DP=Math.sqrt(DP/(vetGap.size()-1));
		return df.format(DP);
	}

	public String getBestF() 
	{
		Collections.sort(vetSolutions);
		bestF=vetSolutions.get(vetSolutions.size()-1);
		return  ""+df.format(bestF);
	}
	
	public String get1Q() 
	{
		Collections.sort(vetSolutions);
		UmQ=vetSolutions.get(3*vetSolutions.size()/4);
		return  ""+UmQ;
	}

	public String get3Q() 
	{
		Collections.sort(vetSolutions);
		TresQ=vetSolutions.get(1*vetSolutions.size()/4);
		return  ""+TresQ;
	}
	
	public String getWorstF()
	{
		Collections.sort(vetSolutions);
		worstF=vetSolutions.get(0);
		return  ""+df.format(worstF);
	}

	public String getMedianF() 
	{
		Collections.sort(vetSolutions);
		medianF=vetSolutions.get(vetSolutions.size()/2);
		return  ""+df.format(medianF);
	}
	
	public String getBestGap() 
	{
		Collections.sort(vetGap);
		bestGap=vetGap.get(0);
		return  df.format(bestGap);
	}
	
	public String get1QGap() 
	{
		Collections.sort(vetGap);
		UmQGap=vetGap.get(vetGap.size()/4);
		return  df.format(UmQGap);
	}

	public String get3QGap() 
	{
		Collections.sort(vetGap);
		TresQGap=vetGap.get(3*vetGap.size()/4);
		return  df.format(TresQGap);
	}
	
	public String getWorstGap()
	{
		Collections.sort(vetGap);
		worstGap=vetGap.get(vetGap.size()-1);
		return  df.format(worstGap);
	}

	public String getMedianGap() 
	{
		Collections.sort(vetGap);
		medianGap=vetGap.get(vetGap.size()/2);
		return df.format(medianGap);
	}

	public String getIteradorMF() {
		return df.format(iteradorMF);
	}
	
	public String gettempoMF() {
		return df.format(tempoMF);
	}
	
	public String getOmega() {
		return df.format(omega);
	}
	
	public String getdistBL() {
		return df.format(distBL);
	}
	
	public String getdistC() {
		return df.format(distC);
	}
	
	public String getdistHeu() {
		return df.format(distHeu);
	}
	
	public String getDistMediaPR() {
		return df2.format(distMediaPR);
	}
	
	public String getDistIdealMedioGlobal() {
		return df2.format(DistBLEdgeGlobal);
	}
	
	public String getGapMediaBL() {
		return df2.format(gapMediaBL);
	}

}
