package PathReLinking;

import java.util.Arrays;

import Comparador.VariacaoCrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Solucao;

//FirstImproviment.
public class PathReLinking8 extends PathReLinking
{
	int indexEntra,indexSai;

	VariacaoCrescente variacaoCrescente=new VariacaoCrescente();

	public PathReLinking8(Instancia instancia,MetaHeuristicas.Config config, Estatistica estatistica)
	{
		super(instancia, config, estatistica);
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking2;
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
		
		Arrays.sort(nosSaem,0,topNosSaem,variacaoCrescente);
		
		for (int i = 0; i < topNosSaem; i++) 
		{
			indexSai=i;
			
			no=nosSaem[indexSai];
			solucao.removerFacilidade(no);
			
			indexEntra=getMelhorBeneficioGA();
			
			solucao.addFacilidade(nosEntram[indexEntra]);
			
//			nosSaem[indexSai]=nosSaem[--topNosSaem];
			nosEntram[indexEntra]=nosEntram[--topNosEntram];
			
			if((!achou&&solucao.f>inicial.f&&solucao.f>guia.f)||
			(achou&&solucao.f>inicial.f&&solucao.f>guia.f&&solucao.f>melhorSolucao.f))
			{
				achou=true;
				melhorSolucao.clone(solucao);
			}
		}
		return achou;
	}
	
//	private int getMenorVariacao()
//	{
//		double menorVariacao=Double.MAX_VALUE;
//		int index=0;
//		for (int i = 0; i < topNosSaem; i++) 
//		{
//			if(nosSaem[i].variacao<menorVariacao)
//			{
//				index=i;
//				menorVariacao=nosSaem[i].variacao;
//			}
//		}
//		return index;
//	}
	
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
