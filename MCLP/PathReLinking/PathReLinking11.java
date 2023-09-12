package PathReLinking;

import java.util.Arrays;

import Comparador.VariacaoDecrescente;
import Dados.Instancia;
import Dados.TipoAdj;
import Estatistica.Estatistica;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//variacaoCrescente
public class PathReLinking11 extends PathReLinking
{
	int indexEntra=0, indexSai=0,bestIndexSai=0,bestIndexEntra=0,novoF,bestF;
	VariacaoDecrescente variacaoDecrescente=new VariacaoDecrescente();
	
	public PathReLinking11(Instancia instancia,MetaHeuristicas.Config config, Estatistica estatistica)
	{
		super(instancia, config, estatistica);
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking11;
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
			{
				nosSaem[topNosSaem++]=no;
				no.variacao=estatistica.getVariacao()[no.nome];
			}
			
			no=guia.solucao[i];
			if(!solucao.solucaoFixa[no.nome].facilidade)
				nosEntram[topNosEntram++]=solucao.solucaoFixa[no.nome];
		}
		
		Arrays.sort(nosSaem,0,topNosSaem,variacaoDecrescente);
		
		for (int i = 0; i < topNosSaem; i++) 
		{
			no=nosSaem[i];
			solucao.removerFacilidade(no);
			
			indexEntra=getMelhorBeneficioGA();
			
			no.entra=nosEntram[indexEntra];
			
			nosEntram[indexEntra]=nosEntram[--topNosEntram];
			
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
		for (int i = 0; i < topNosEntram; i++) 
		{
			if(melhorBeneficio<nosEntram[i].beneficio)
			{
				index=i;
				melhorBeneficio=nosEntram[i].beneficio;
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
