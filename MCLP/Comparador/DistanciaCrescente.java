package Comparador;

import java.util.Comparator;

import MetaHeuristicas.NoSol;

public class DistanciaCrescente implements Comparator<NoSol>
{
	public int compare(NoSol a, NoSol b) 
	{
		if(a.isFacilidade()&&!b.isFacilidade())
			return -1;
		if(!a.isFacilidade()&&b.isFacilidade())
			return 1;
		
		if(a.distancia==b.distancia)
			return 0;
		
		if(a.distancia>b.distancia)
			return 1;
		
		return -1;
	}
}
