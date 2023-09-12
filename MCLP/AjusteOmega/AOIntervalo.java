package AjusteOmega;

import java.text.DecimalFormat;
import java.util.Random;

import AjustDist.DistIdeal;
import Data.Instancia;
import SearchMethod.Config;
import SearchMethod.Media;

public class AOIntervalo implements AjusteOmega
{
	private double omega,omegaMin,omegaMax;
	private DecimalFormat deci2=new DecimalFormat("0.00");
	private Media distBLMedia;
	private Random rand=new Random();

	public AOIntervalo(Instancia instancia, Config config, DistIdeal distIdeal, Double max) 
	{
		this.omegaMin=config.getOmegaMin();
		this.omegaMax=config.getOmegaMax();
		this.distBLMedia=new Media(config.getNumIterUpdate());
	}
	
	public void setDistancia(double distBL)
	{
		distBLMedia.setValor(distBL);
	}
	
	public double getOmegaReal() 
	{
		
		omega=omegaMin;
		if((omegaMax-omegaMin)>0)
			omega+=rand.nextInt((int)(omegaMax-omegaMin));
		
		return omega;
	}

	@Override
	public String toString() {
		return 
		"o"+": " + deci2.format(omega) 
		+ " dBLM"+": " + distBLMedia;
	}

}
