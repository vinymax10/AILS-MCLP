package PathReLinking;

import Dados.Instancia;
import Dados.TipoAdj;
import Estatistica.Estatistica;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//BestImproviment.
public class PathReLinking14 extends PathReLinking
{
	int indexEntra=0, indexSai=0,bestIndexSai=0,bestIndexEntra=0,novoF,bestF;
	
	public PathReLinking14(Instancia instancia,MetaHeuristicas.Config config, Estatistica estatistica)
	{
		super(instancia, config, estatistica);
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking14;
	}
	
	public boolean pathReLinking(Solucao inicial, Solucao guia) 
	{
		boolean achou=false;
		solucao.clone(inicial);
		
		topNosEntram=0;
		topNosSaem=0;
		
		for (int i = 0; i < numFacilidades; i++)
		{
			no=solucao.solucao[i];
			if(!guia.solucaoFixa[no.nome].facilidade)
				nosSaem[topNosSaem++]=no;
		}
		
		for (int i = 0; i < size; i++) 
			solucao.solucao[i].podeEscolher=true;
		
		for (int i = 0; i < topNosSaem; i++) 
		{
			no=nosSaem[i];
			solucao.removerFacilidade(no);
			
			indexEntra=getMelhorBeneficioGA();
			solucao.solucao[indexEntra].podeEscolher=false;
			no.entra=solucao.solucao[indexEntra];
			
			solucao.addFacilidade(no);
		}
		
		while(topNosSaem>0)
		{
			bestF=Integer.MIN_VALUE;
			for (int i = 0; i < topNosSaem; i++) 
			{
				novoF=custo(nosSaem[i],nosSaem[i].entra);
				if(novoF>bestF)
				{
					bestIndexSai=i;
					bestF=novoF;
				}
			}
			
			solucao.trocaCompleta(nosSaem[bestIndexSai],nosSaem[bestIndexSai].entra);
			
			nosSaem[bestIndexSai]=nosSaem[--topNosSaem];

			if((!achou&&solucao.f>inicial.f&&solucao.f>guia.f)||
			(achou&&solucao.f>inicial.f&&solucao.f>guia.f&&solucao.f>melhorSolucao.f))
			{
				achou=true;
				melhorSolucao.clone(solucao);
			}
		}
		return achou;
	}
	
	private int getMelhorBeneficioGA() 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = 0; i < size; i++) 
		{
			if(no.nome!=solucao.solucao[i].nome&&solucao.solucao[i].podeEscolher&&!solucao.solucao[i].facilidade
			&&(melhorBeneficio<solucao.solucao[i].beneficio)||(melhorBeneficio==solucao.solucao[i].beneficio&&rand.nextBoolean()))
			{
				index=i;
				melhorBeneficio=solucao.solucao[i].beneficio;
			}
		}
		return index;
	}
	
	private int custo(NoSol sai,NoSol entra)
	{
		int custo=entra.beneficio;
		for (int i = 0; i < sai.possoAtender.length; i++) 
		{
			aux=solucao.solucaoFixa[sai.possoAtender[i]];
			if(aux.numFacilidades==1&&instancia.getTipoAdj(aux.nome,entra.nome)!=TipoAdj.Raio)
			{
				custo-=aux.demanda;
			}
		}
		return custo;
	}
}
