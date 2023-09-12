package AjusteOmega;

import java.text.DecimalFormat;
import java.util.Random;

import AjustDist.DistIdeal;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;

public class AODistancia implements AjusteOmega
{
	double omega,omegaMin,omegaMax;
	Media distBLMedia;
	int iterador=0,lastIteradorMF;
	DecimalFormat deci2=new DecimalFormat("0.00");
	double distObtida;
	
	Media omegaMedio;
	
	double omegaReal;
	Random rand=new Random();
	double eb;
	
	int numIterUpdate;
	DistIdeal distIdeal;
	
	public AODistancia(Instancia instancia, Config config, DistIdeal distIdeal, Double max) 
	{
		this.omega = config.getDistM();
		this.numIterUpdate = config.getNumIterUpdate();
		this.omegaMin=1;
		this.omegaMax=instancia.getNumFacilidades();
		this.omegaMedio=new Media(config.getNumIterUpdate());
		this.distBLMedia=new Media(config.getNumIterUpdate());

		this.distIdeal=distIdeal;
	}
	
	public void ajusteOmega()
	{
		distObtida=distBLMedia.getMediaDinam();
		
		omega+=((omega/distObtida*distIdeal.distIdeal)-omega);

		omega=Math.min(omegaMax, Math.max(omega, omegaMin));
		
		omegaMedio.setValor(omega);
		
		iterador=0;
	}
	
	public void setDistancia(double distBL)
	{
		iterador++;
		
		distBLMedia.setValor(distBL);

		if(iterador%numIterUpdate==0)
			ajusteOmega();
	}
	
	public double getOmegaReal() 
	{
		omegaReal=omega;
		omegaReal=Math.min(omegaMax, Math.max(omegaReal, omegaMin));
//		System.out.println("omegaReal: "+omegaReal);
		return omegaReal;
	}

	@Override
	public String toString() {
		return 
		"o"+": " + deci2.format(omega) 
		+ " dBLM"+": " + distBLMedia
		+ " dMI"+": " + deci2.format(distIdeal.distIdeal)
		+ " omegaReal: "+deci2.format(omegaReal)
		+ " distObtida: "+distObtida
		+ " oM"+": " + omegaMedio;
	}
	
	public Media getOmegaMedio() 
	{
		return omegaMedio;
	}

	public void setOmegaReal(double omegaReal) {
		this.omegaReal = omegaReal;
	}

}
