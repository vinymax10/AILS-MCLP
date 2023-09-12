package Elite;

import java.util.Arrays;

import AjustDist.DistIdeal;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Distancia;
import MetaHeuristicas.Solucao;

public class SubConj20 extends SubConj
{
	
	public SubConj20(Instancia instancia,Config config, 
	Distancia distEntreSolucoes,DistIdeal distIdeal)
	{
		super(instancia,config,distEntreSolucoes,distIdeal);
	}
	
//	So atualiza se tiver melhor qualidade que a pior solu��o. 
//	E remove todas as solu��es que s�o piores e que estao proximas de s.
	public void add(Solucao s)
	{
		atualizou=false;
		topRegeitadas++;
		
		if(topElite<size)
		{
			if(!possuiMenorDistMelhorF(s))
			{
				if(qtnMenorDistPiorF>0)
				{
					if(qtnMenorDistPiorF==1)
						distMaisProxima.clone(s);
					else
					{
						removeTodasDistMenor();
						elite[topElite++].clone(s);
					}
				}
				else
				{
					if(elite[topElite]==null)
						elite[topElite]=new Solucao(instancia,config);
					
					elite[topElite].clone(s);
					topElite++;
				}
				atualizou=true;
			}
		}
		else if(s.f>=elite[size-1].f)
		{
			if(!possuiMenorDistMelhorF(s))
			{
				if(qtnMenorDistPiorF>0)
				{
					if(qtnMenorDistPiorF==1)
						distMaisProxima.clone(s);
					else
					{
						removeTodasDistMenor();
						elite[topElite++].clone(s);
					}
				}
				else 
					elite[topElite-1].clone(s);

				atualizou=true;				
			}
		}
		
		if(atualizou)
			Arrays.sort(elite,0,topElite);
	}
	
	protected boolean possuiMenorDistMelhorF(Solucao s)
	{
		menorDist=Integer.MAX_VALUE;
		qtnMenorDistPiorF=0;
		for (int i = 0; i < topElite; i++) 
		{
			dist=distEntreSolucoes.distancia(s, elite[i],distIdeal.distIdeal);
			elite[i].distancia=dist;
			
//			System.out.println("dist: "+dist);
			if(dist<menorDist)
			{
				distMaisProxima=elite[i];
				menorDist=dist;
			}
			
			if(dist<distIdeal.distIdeal)
			{
				if(elite[i].f>s.f)//if(elite[i].f<=s.f)
					return true;
				else
					qtnMenorDistPiorF++;
			}
		}
//		System.out.println("qtnMenorDistPiorF: "+qtnMenorDistPiorF);
		return false;
	}
	
	protected void removeTodasDistMenor() 
	{
		Solucao aux;
		for (int i = 0; i < topElite; i++) 
		{
			if(elite[i].distancia<distIdeal.distIdeal)
			{
				aux=elite[i];
				elite[i]=elite[--topElite];
				elite[topElite]=aux;
				i--;
			}
		}
	}
}
