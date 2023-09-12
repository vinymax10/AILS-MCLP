package BuscaLocal;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst3 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	
	public BuscaLocalFirst3(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
	}

	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int antF,indexJ=0;
		boolean melhorou=true;
		iterador=0;
		int novoF;
		
		for (int i = numFacilidades; i < size; i++) 
			solucao[i].setBeneficioGA(solucaoFixa);
		
		while(melhorou) 
		{
			melhorou=false;
			for (int i = 0; i < numFacilidades; i++)
			{
				antF=s.f;
				s.removerFacilidade(solucao[i]);
				
				solucao[i].updateAdjR2(solucaoFixa);
				
				indexJ=getMelhorBeneficioGA(numFacilidades);
				novoF=solucao[indexJ].beneficio+s.f;
				if(indexJ>=numFacilidades&&novoF>antF)
				{
					melhorou=true;
					s.addFacilidade(solucao[indexJ]);
					
					solucao[indexJ].updateAdjR2(solucaoFixa);
					
					s.trocaPosicao(i, indexJ);
				}
				else
				{
					s.addFacilidade(solucao[i]);
					solucao[i].voltaBeneficioAnterior(solucaoFixa);
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
}
