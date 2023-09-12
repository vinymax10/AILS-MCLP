package BuscaLocal;

import java.util.Arrays;

import Comparador.VariacaoCrescente;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst11 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	VariacaoCrescente crescente=new VariacaoCrescente();

	public BuscaLocalFirst11(Instancia instancia,Config config)
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
		int antF,indexJ=0,indexI,novoF;
		iterador=0;
		boolean melhorou=false;
//		int rodadas=0;
		for (int i = numFacilidades; i < size; i++) 
			solucao[i].setBeneficioGA(solucaoFixa);
		
		do
		{
			melhorou=false;
//			rodadas++;
			Arrays.sort(solucao,0,numFacilidades,crescente);

			for (int i = 0; i < numFacilidades; i++)
			{
				indexI=i;
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
					melhorou=true;
				}
				else
				{
					s.addFacilidade(solucao[indexI]);
					solucao[indexI].voltaBeneficioAnterior(solucaoFixa);
				}
			}
		}
		while(melhorou);
//		System.out.println("rodadas: "+rodadas);

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
