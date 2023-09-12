package BuscaLocal;

import java.util.Random;

import Dados.Instancia;
import Dados.TipoAdj;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public class BuscaLocalFirst5 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	
	int posicaoI[];
	int posicaoJ[];
	Random rand=new Random();
	int posA,posB;
	int indexJ=0,indexI;
	
	public BuscaLocalFirst5(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.posicaoI=new int[instancia.getNumFacilidades()];
		for (int i = 0; i < posicaoI.length; i++) 
			posicaoI[i]=i;
		
		this.posicaoJ=new int[size-numFacilidades];
		for (int j = 0; j < posicaoJ.length; j++) 
			posicaoJ[j]=numFacilidades+j;
	}
	
	private void mudaOrdem()
	{
		int aux;
		for (int i = 0; i < posicaoI.length; i++) 
		{
			posA=rand.nextInt(posicaoI.length);
			posB=rand.nextInt(posicaoI.length);
			aux=posicaoI[posA];
			posicaoI[posA]=posicaoI[posB];
			posicaoI[posB]=aux;
		}
		
		for (int j = 0; j < posicaoJ.length; j++) 
		{
			posA=rand.nextInt(posicaoJ.length);
			posB=rand.nextInt(posicaoJ.length);
			aux=posicaoJ[posA];
			posicaoJ[posA]=posicaoJ[posB];
			posicaoJ[posB]=aux;
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
		boolean melhorou=true;
		iterador=0;
		mudaOrdem();
		while(melhorou) 
		{
			iterador++;
			melhorou=false;
			for (int i = 0; i < posicaoI.length; i++) 
			{
				indexI=posicaoI[i];
				for (int j = 0; j < posicaoJ.length; j++)
				{
					indexJ=posicaoJ[j];
					
					if(custo(solucao[indexI],solucao[indexJ])>0)
					{
						melhorou=true;
						s.trocaCompleta(indexI, indexJ);
					}
				}
			}
		}
	}
	
	private int custo(NoSol noSai, NoSol noEntra) 
	{
		int perco=0;
		NoSol aux;
		for (int j = 0; j < noSai.possoAtender.length; j++) 
		{
			aux=solucaoFixa[noSai.possoAtender[j]];
			if(aux.numFacilidades==1
			&&instancia.getTipoAdj(aux.nome,noEntra.nome)!=TipoAdj.Raio)
				perco+=aux.demanda;
		}
		
		int ganho=0;
		for (int j = 0; j < noEntra.possoAtender.length; j++) 
		{
			aux=solucaoFixa[noEntra.possoAtender[j]];
			if(aux.numFacilidades==0)
				ganho+=aux.demanda;
		}
		return ganho-perco;
	}
	
}
