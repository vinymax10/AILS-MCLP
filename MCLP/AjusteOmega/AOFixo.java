package AjusteOmega;

import java.text.DecimalFormat;

import AjustDist.DistIdeal;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;

public class AOFixo implements AjusteOmega
{
	double omega;
	DecimalFormat deci2=new DecimalFormat("0.00");
	Media distBLMedia;

	public AOFixo(Instancia instancia, Config config, DistIdeal distIdeal, Double max) 
	{
		this.omega = config.getOmegaFixo();
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
