package Elite;

import java.util.Arrays;

import AjustDist.DistIdeal;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Distancia;
import MetaHeuristicas.Solucao;

public class SubConj1 extends SubConj
{
	
	public SubConj1(Instancia instancia,Config config, 
	Distancia distEntreSolucoes,DistIdeal distIdeal)
	{
		super(instancia,config,distEntreSolucoes,distIdeal);
	}
	
//	So atualiza se tiver melhor qualidade que a pior solu��o. 
//	E remove todas as solu��es que s�o piores e que estao proximas de s.
	public void add(Solucao s)
	{
		atualizou=false;
		
		if(topElite<size)
		{
			if(!possuiQualidadeIgual(s))
			{
				if(elite[topElite]==null)
					elite[topElite]=new Solucao(instancia,config);
				
				elite[topElite].clone(s);
				topElite++;
				atualizou=true;
			}
		}
		else if(s.f>=elite[size-1].f)
		{
			if(!possuiQualidadeIgual(s))
				elite[topElite-1].clone(s);
			else
				qualidadeMaisProxima.clone(s);

			atualizou=true;				
		}
		
		if(atualizou)
			Arrays.sort(elite,0,topElite);
	}
	
	protected boolean possuiQualidadeIgual(Solucao s)
	{
		menorDist=Integer.MAX_VALUE;
		qtnMenorDistPiorF=0;
		for (int i = 0; i < topElite; i++) 
		{
			if(elite[i].f==s.f)
			{
				qualidadeMaisProxima=elite[i];
				return true;
			}
		}
		return false;
	}
	
}
