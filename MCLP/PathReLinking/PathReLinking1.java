package PathReLinking;

import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Solucao;

//FirstImproviment.
//quem sai=escolha aleatoria
//quem entra= the best
public class PathReLinking1 extends PathReLinking
{
	int indexEntra, indexSai;

	public PathReLinking1(Instancia instancia,MetaHeuristicas.Config config, Estatistica estatistica)
	{
		super(instancia, config, estatistica);
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking1;
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
			indexSai=rand.nextInt(topNosSaem);
			
			no=nosSaem[indexSai];
			solucao.removerFacilidade(no);
			
			indexEntra=getMelhorBeneficioGA();
			
			solucao.addFacilidade(nosEntram[indexEntra]);
			
			nosSaem[indexSai]=nosSaem[--topNosSaem];
			nosEntram[indexEntra]=nosEntram[--topNosEntram];
			
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
	
//	public void pathReLink2(Solucao inicial, Solucao guia) 
//	{
//		
//		
//		
//		NoSol sai,entra;
//		int posEntra,posSai;
//		int indexVizSai=0,indexVizEntra=0,BestJndexEntra=0,BestIndexSai=0;
//		int maiorF=0;
//		int BestEntra=0,BestSai=0,vizEntra=0,vizSai=0;
//		boolean melhorou=false,troquei=true;
//		
//		int maiorBeneficio,beneficio;
////		int inicialF=f;
////		int lastImproviment=1;
////		System.out.println("---------------inicio-----------------");
//		while(!melhorou&&troquei)
//		{
//			troquei=false;
//			maiorF=0;
//			for (int i = 0; i < facilidades.length; i++) 
//			{
//				posSai=facilidades[i];
//				sai=solucaoFixa[posSai];
//				indexVizSai=i;
//				if(!guia.solucaoFixa[posSai].facilidade)
//				{
//					//escolhendo quem sai
//					removerFacilidade(sai);
//					
//					maiorBeneficio=0;
//					for (int j = 0; j < guia.facilidades.length; j++) 
//					{
//						posEntra=guia.facilidades[j];
//						if(!solucaoFixa[posEntra].facilidade)
//						{
//							beneficio=beneficioGA(solucaoFixa[posEntra]);
//							if(maiorBeneficio<beneficio)
//							{
//								//escolhendo quem entra
//								maiorBeneficio=beneficio;
//								indexVizEntra=j;
//								vizEntra=posEntra;
//								vizSai=posSai;
//							}
//						}
//					}
//					//definindo melhor par
//					if(maiorF<f+maiorBeneficio)
//					{
//						troquei=true;
//						maiorF=f+maiorBeneficio;
//						BestEntra=vizEntra;
//						BestSai=vizSai;
//						BestIndexSai=indexVizSai;
//						BestJndexEntra=indexVizEntra;
//						
//					}
//					addFacilidade(sai);
//				}
//			}
//			//simplesmente caminha
//			if(troquei)
//			{
//				sai=solucaoFixa[BestSai];
//				entra=solucaoFixa[BestEntra];
//				removerFacilidade(sai);
//				addFacilidade(entra);
//				facilidades[BestIndexSai]=guia.facilidades[BestJndexEntra];
//				
////				if(f>guia.f&&f>inicialF&&f>lastImproviment)
////				{
////					lastImproviment=f;
//////					System.out.println("f: "+f+" inicialF: "+inicialF+" guia.f: "+guia.f+" qtdeApGNG: "+algoritmo.qtdeApGNG);
////					if(algoritmo.melhorF<f)
////					{
////						algoritmo.freqMelhora[pos]++;
////						algoritmo.totalMelhora++;
////						algoritmo.gng.ApresentaPadrao(this);
////						algoritmo.qtdeApGNG++;
////						algoritmo.analisaPR(this,"PathRelink");
////					}
////				}
//			}
//		}
//	}
}
