package Comparador;

import java.util.Comparator;

import MetaHeuristicas.NoSol;

public class IntersecDecrescente implements Comparator<NoSol>
{
	public int compare(NoSol a, NoSol b) 
	{
		if(a.isFacilidade()&&!b.isFacilidade())
			return -1;
		if(!a.isFacilidade()&&b.isFacilidade())
			return 1;
		return (int)(b.intersec-a.intersec);
	}
}
