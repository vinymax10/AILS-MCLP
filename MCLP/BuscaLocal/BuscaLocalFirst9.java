package BuscaLocal;

import java.util.Random;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst9 implements BuscaLocal
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
	
	public BuscaLocalFirst9(Instancia instancia,Config config)
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
		int antF,indexJ=0,indexI,novoF;
		iterador=0;
		mudaOrdem();
//		int contMovimentos=0;
		for (int i = numFacilidades; i < size; i++) 
			solucao[i].setBeneficioGA(solucaoFixa);
		
		for (int i = 0; i < numFacilidades; i++)
		{
			indexI=posicao[i];
			antF=s.f;
			s.removerFacilidade(solucao[indexI]);
			
			solucao[indexI].updateAdjR2(solucaoFixa);
			
			indexJ=getMelhorBeneficioGA(numFacilidades);
			novoF=solucao[indexJ].beneficio+s.f;
			if(indexJ>=numFacilidades&&novoF>antF)
			{
				s.addFacilidade(solucao[indexJ]);
				solucao[indexJ].updateAdjR2(solucaoFixa);
				s.trocaPosicao(indexI, indexJ);
//				contMovimentos++;
			}
			else
			{
				s.addFacilidade(solucao[indexI]);
				solucao[indexI].voltaBeneficioAnterior(solucaoFixa);
			}
		}
//		System.out.println("contMovimentos: "+contMovimentos);

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