package PathReLinking;

import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Solucao;

//BestImproviment.
public class PathReLinking13 extends PathReLinking
{
	int indexEntra=0, indexSai=0;

	public PathReLinking13(Instancia instancia,MetaHeuristicas.Config config, Estatistica estatistica)
	{
		super(instancia, config, estatistica);
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking13;
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
		
		while(topNosSaem>0)
		{
			indexSai=rand.nextInt(topNosSaem);
			
			no=nosSaem[indexSai];
			solucao.removerFacilidade(no);
			
			indexEntra=getMelhorBeneficioGA();
			
			solucao.addFacilidade(solucao.solucao[indexEntra]);
			solucao.trocaPosicao(no.posicao, solucao.solucao[indexEntra].posicao);
			
			nosSaem[indexSai]=nosSaem[--topNosSaem];
			
			if((!achou&&solucao.f>inicial.f&&solucao.f>guia.f)||
			(achou&&solucao.f>inicial.f&&solucao.f>guia.f&&solucao.f>melhorSolucao.f))
			{
				achou=true;
				melhorSolucao.clone(solucao);
			}
		}
		return achou;
	}
	
//	private int getMelhorBeneficioGA() 
//	{
//		int index=0;
//		int melhorBeneficio=0;
//		for (int i = 0; i < topNosEntram; i++) 
//		{
//			if(melhorBeneficio<nosEntram[i].beneficio)
//			{
//				index=i;
//				melhorBeneficio=nosEntram[i].beneficio;
//			}
//		}
//		return index;
//	}
	
	private int getMelhorBeneficioGA() 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = 0; i < size; i++) 
		{
			if(no.nome!=solucao.solucao[i].nome&&!solucao.solucao[i].facilidade
			&&(melhorBeneficio<solucao.solucao[i].beneficio)||(melhorBeneficio==solucao.solucao[i].beneficio&&rand.nextBoolean()))
			{
				index=i;
				melhorBeneficio=solucao.solucao[i].beneficio;
			}
		}
		return index;
	}
	
}
