package PathReLinking;

import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Solucao;

//BestImproviment.
//quem sai=the best
//quem entra= the best
public class PathReLinking3 extends PathReLinking
{
	int indexEntra=0, indexSai=0,bestIndexSai=0,bestIndexEntra=0,novoF,bestF;
	
	public PathReLinking3(Instancia instancia,MetaHeuristicas.Config config, Estatistica estatistica)
	{
		super(instancia, config, estatistica);
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking3;
	}
	
	public boolean pathReLinking(Solucao inicial, Solucao guia) 
	{
		boolean achou=false;
		solucao.clone(inicial);
		
		if(inicial.f>guia.f)
			melhorSolucao.clone(guia);
		else
			melhorSolucao.clone(inicial);
		
		topNosEntram=0;
		topNosSaem=0;
		
		for (int i = 0; i < numFacilidades; i++)
		{
			no=solucao.solucao[i];
			if(!guia.solucaoFixa[no.nome].facilidade)
				nosSaem[topNosSaem++]=no;
			
			no=guia.solucao[i];
			if(!solucao.solucaoFixa[no.nome].facilidade)
				nosEntram[topNosEntram++]=solucao.solucaoFixa[no.nome];
		}
		
		while(topNosSaem>0)
		{
			bestF=0;
			for (int i = 0; i < topNosSaem; i++) 
			{
				indexSai=i;
				
				no=nosSaem[indexSai];
				solucao.removerFacilidade(no);
				
				indexEntra=getMelhorBeneficioGA();
				novoF=nosEntram[indexEntra].beneficio+solucao.f;
				if(novoF>bestF)
				{
					bestIndexEntra=indexEntra;
					bestIndexSai=indexSai;
					bestF=novoF;
				}
				solucao.addFacilidade(no);
			}
			
			solucao.removerFacilidade(nosSaem[bestIndexSai]);
			solucao.addFacilidade(nosEntram[bestIndexEntra]);
			
			nosSaem[bestIndexSai]=nosSaem[--topNosSaem];
			nosEntram[bestIndexEntra]=nosEntram[--topNosEntram];
			
			if(solucao.f>melhorSolucao.f)
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
	
}
