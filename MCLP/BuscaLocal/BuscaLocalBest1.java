package BuscaLocal;

import java.util.Random;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public class BuscaLocalBest1 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int beneficio,beneficioNo;
	int numFacilidades;
	int iterador=0;
	
	int index=0;
	int melhorBeneficio=0;
	Random rand=new Random();
//	Media media;
	
	public BuscaLocalBest1(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
//		this.media=new Media(config.getNumIterUpdate());
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
	}


	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int antF,indexJ=0,novoF;
		int bestI=0,bestJ=0;
		boolean melhorou=true;
		int melhorF=0;

		iterador=0;

		while(melhorou) 
		{
			iterador++;
			melhorou=false;
			melhorF=0;
			for (int i = 0; i < numFacilidades; i++)
			{
				antF=s.f;
				s.removerFacilidade(solucao[i]);
				
				indexJ=getMelhorBeneficioGA(numFacilidades);
				novoF=solucao[indexJ].beneficio+s.f;
				
				if(antF<novoF&&melhorF<novoF||(melhorF==novoF&&rand.nextBoolean()))
				{
					melhorou=true;
					bestI=i;
					bestJ=indexJ;
					melhorF=novoF;
				}
				s.addFacilidade(solucao[i]);
			}
			
			if(melhorou)
			{
				s.trocaCompleta(bestI, bestJ);
				iterador++;
			}
		}
//		media.setValor(iterador);
//		System.out.println(media);
	}
	
	private int getMelhorBeneficioGA(int apartir) 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = apartir; i < size; i++) 
		{
			if(melhorBeneficio<solucao[i].beneficio)
			{
				index=i;
				melhorBeneficio=solucao[i].beneficio;
			}
		}
		return index;
	}
	
}
