package BuscaLocal;

import java.util.Random;

import Dados.Instancia;
import Dados.TipoAdj;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst8 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	
	int posicao[];
	Random rand=new Random();
	int posA,posB;
	
	public BuscaLocalFirst8(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.posicao=new int[instancia.getNumFacilidades()];
		for (int i = 0; i < posicao.length; i++) 
			posicao[i]=i;
	}
	
	private void mudaOrdem()
	{
		int aux;
		for (int i = 0; i < posicao.length; i++) 
		{
			posA=rand.nextInt(posicao.length);
			posB=rand.nextInt(posicao.length);
			aux=posicao[posA];
			posicao[posA]=posicao[posB];
			posicao[posB]=aux;
		}
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
	}

	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int antF,indexJ=0,indexJ2,indexI,indexI2,novoF;
		boolean melhorou=true;
		iterador=0;
		
		for (int i = numFacilidades; i < size; i++) 
			solucao[i].setBeneficioGA(solucaoFixa);
		
		while(melhorou) 
		{
			melhorou=false;
			mudaOrdem();
			for (int i = 0; i < numFacilidades-1; i++)
			{
				indexI=posicao[i];
				indexI2=posicao[i+1];
				antF=s.f;
				
				s.removerFacilidade(solucao[indexI]);
				s.removerFacilidade(solucao[indexI2]);

				solucao[indexI].updateAdjR2(solucaoFixa);
				solucao[indexI2].updateAdjR2(solucaoFixa);

				indexJ=getMelhorBeneficioGA(numFacilidades);
				indexJ2=getMelhorBeneficioGA2(numFacilidades,indexJ);
				if(indexJ==indexJ2)
					System.out.println("indexJ: "+indexJ+" indexJ2: "+indexJ2);
				
				novoF=solucao[indexJ].beneficio
				+calcIntersec(solucaoFixa,solucao[indexJ],solucao[indexJ2])
				+s.f;
				if(indexJ>=numFacilidades&&novoF>antF)
				{
					melhorou=true;
					s.addFacilidade(solucao[indexJ]);
					s.addFacilidade(solucao[indexJ2]);
					
					solucao[indexJ].updateAdjR2(solucaoFixa);
					solucao[indexJ2].updateAdjR2(solucaoFixa);

					s.trocaPosicao(indexI, indexJ);
					s.trocaPosicao(indexI2, indexJ2);

					if(s.f!=novoF)
						System.out.println("novoF: "+novoF+" antF: "+antF+" f: "+s.f);
				}
				else
				{
					s.addFacilidade(solucao[indexI]);
					s.addFacilidade(solucao[indexI2]);
					
					solucao[indexI].updateAdjR2(solucaoFixa);
					solucao[indexI2].updateAdjR2(solucaoFixa);
//					solucao[indexI].voltaBeneficioAnterior(solucaoFixa);
					
//					solucao[indexI2].voltaBeneficioAnterior(solucaoFixa);
				}
			}
		}
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
	
	private int getMelhorBeneficioGA2(int apartir,int indexJ) 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = apartir; i < size; i++) 
		{
			if(indexJ!=i&&melhorBeneficio<solucao[i].beneficio
			&&instancia.getTipoAdj(solucao[indexJ].nome,solucao[i].nome)!=TipoAdj.Raio)
			{
				index=i;
				melhorBeneficio=solucao[i].beneficio;
			}
		}
		return index;
	}
	
	public int calcIntersec(NoSol solucaoFixa[],NoSol no1,NoSol no2)
	{
		int intersec=0;
		NoSol aux;
		for (int i = 0; i < no2.possoAtender.length; i++) 
		{
			aux=solucaoFixa[no2.possoAtender[i]];
			if(aux.numFacilidades==0&&instancia.getTipoAdj(aux.nome,no1.nome)!=TipoAdj.Raio)
				intersec+=aux.demanda;
		}
		return intersec;
	}
}
