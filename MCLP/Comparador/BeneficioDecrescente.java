package Comparador;

import java.util.Comparator;

import MetaHeuristicas.NoSol;

public class BeneficioDecrescente implements Comparator<NoSol>
{
	public int compare(NoSol a, NoSol b) 
	{
		if(a.isFacilidade()&&!b.isFacilidade())
			return -1;
		if(!a.isFacilidade()&&b.isFacilidade())
			return 1;
		return (int)(b.beneficio-a.beneficio);
	}
}
