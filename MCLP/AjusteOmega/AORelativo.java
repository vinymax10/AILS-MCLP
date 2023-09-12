package AjusteOmega;

import java.text.DecimalFormat;

import AjustDist.DistIdeal;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;

public class AORelativo implements AjusteOmega
{
	double omega;
	DecimalFormat deci2=new DecimalFormat("0.00");
	Media distBLMedia;
	int numFacilidades;
	
	public AORelativo(Instancia instancia, Config config, DistIdeal distIdeal, Double max) 
	{
		this.numFacilidades=instancia.getNumFacilidades();
		this.omega = config.getRelative()*numFacilidades;
		this.distBLMedia=new Media(config.getNumIterUpdate());
	}
	
	public void setDistancia(double distBL)
	{
		distBLMedia.setValor(distBL);
	}
	
	public double getOmegaReal() 
	{
		return omega;
	}

	@Override
	public String toString() {
		return 
		"o"+": " + deci2.format(omega) 
		+ " dBLM"+": " + distBLMedia;
	}

}
