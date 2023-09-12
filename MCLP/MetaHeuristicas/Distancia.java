package MetaHeuristicas;

public class Distancia 
{
	int dist=0;
	
	public int distancia(Solucao a, Solucao x)
	{
		dist=0;
		for (int i = 0; i < a.numFacilidades; i++) 
		{
			if(!x.solucaoFixa[a.solucao[i].nome].facilidade)
				dist++;
		}
		return dist;
	}
	
	
	public int distancia(Solucao a, Solucao x, double distMin)
	{
		dist=0;
		for (int i = 0; i < a.numFacilidades&&dist<distMin; i++) 
		{
			if(!x.solucaoFixa[a.solucao[i].nome].facilidade)
				dist++;
		}
		return dist;
	}
	
	public int distanciaAntigo(Solucao a, Solucao x)
	{
		dist=0;
		for (int i = 0; i < a.solucaoFixa.length; i++) 
		{
			if(a.solucaoFixa[i].facilidade&&!x.solucaoFixa[i].facilidade)
				dist++;
		}
		return dist;
	}
	
	public int distanciaAntigo(Solucao a, Solucao x, double distMin)
	{
		dist=0;
		for (int i = 0; i < a.solucaoFixa.length&&dist<distMin; i++) 
		{
			if(a.solucaoFixa[i].facilidade&&!x.solucaoFixa[i].facilidade)
				dist++;
		}
		return dist;
	}
	
}
