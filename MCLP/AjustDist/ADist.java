package AjustDist;

import MetaHeuristicas.Config;

public abstract class ADist 
{
	DistIdeal distIdeal;
	Config config;
	double distMax;
	double distMin;
	OmegaIdeal omegaIdeal;
	
	public ADist(OmegaIdeal omegaIdeal,DistIdeal distIdeal,Config config, int size) 
	{
		this.omegaIdeal=omegaIdeal;
		this.distIdeal = distIdeal;
		this.config=config;
		this.distMax=size;
		this.distMin=1;
	}

	public void ajusteDist(){}
}
