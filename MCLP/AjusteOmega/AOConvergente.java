package AjusteOmega;

import java.text.DecimalFormat;

import AjustDist.DistIdeal;
import Data.Instancia;
import SearchMethod.Config;
import SearchMethod.Media;

public class AOConvergente implements AjusteOmega
{
	private double omega,omegaMin,omegaMax;
	private DecimalFormat deci2=new DecimalFormat("0.00");
	private Media distBLMedia;
	int iteradorGlobal=0;
	long ini;
	int numIterUpdate;
	double max;

	public AOConvergente(Instancia instancia, Config config, DistIdeal distIdeal, Double max) 
	{
		this.omegaMin=config.getOmegaMin();
		this.omegaMax=config.getOmegaMax();
		this.omega=config.getOmegaMax();
		this.max=max;
		this.distBLMedia=new Media(config.getNumIterUpdate());
		this.numIterUpdate=config.getNumIterUpdate();
	}
	
	public void setDistancia(double distBL)
	{
		if(iteradorGlobal==0)
			ini=System.currentTimeMillis();
		
		iteradorGlobal++;
		
		if(iteradorGlobal%numIterUpdate==0)
		{
			double tempoMaximo=(max);
			double atual=(double)(System.currentTimeMillis()-ini)/1000;
			double porcentagemTempo=atual/tempoMaximo;			
			omega=omegaMin+(omegaMax-omegaMin)*(1-porcentagemTempo);
		}
		
		distBLMedia.setValor(distBL);
	}
	
	public double getOmegaReal() 
	{
//		System.out.println("omega: "+omega);
		return omega;
	}

	@Override
	public String toString() {
		return 
		"o"+": " + deci2.format(omega) 
		+ " dBLM"+": " + distBLMedia;
	}

}
