package AjustDist;

import MetaHeuristicas.Config;
import MetaHeuristicas.Media;

public class SemAjust extends ADist
{
	public SemAjust(OmegaIdeal omegaIdeal,DistIdeal distIdeal,Config config, int size,Media mFluxoAceitas) 
	{
		super(omegaIdeal,distIdeal,config,size);
	}

	public void ajusteDist(boolean alta){}
}
