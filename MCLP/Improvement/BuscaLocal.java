package Improvement;

import java.util.Random;

import Data.Instance;
import SearchMethod.Config;
import Solution.NoSol;
import Solution.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];

	protected int size;
	Instance instancia;
	Config config;
	int numFacilidades;
	int iterador = 0;
	Random rand = new Random();
//	Media media;

	public BuscaLocal(Instance instancia, Config config)
	{
		this.instancia = instancia;
		this.config = config;
		this.size = instancia.getSize();
		this.numFacilidades = instancia.getNumFacilidades();
//		this.media=new Media(config.getNumIterUpdate());
	}

	private void setSolucao(Solucao s)
	{
		this.solucao = s.solucao;
		this.solucaoFixa = s.solucaoFixa;
	}

	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int antF, indexJ = 0, novoF;
		boolean melhorou = true;
		iterador = 0;
		s.embaralharFacilidades();

		while(melhorou)
		{
			melhorou = false;
			for(int i = 0; i < numFacilidades; i++)
			{
				antF = s.f;
				s.removerFacilidade(solucao[i]);

				indexJ = getMelhorBeneficioGA(numFacilidades);
				novoF = solucao[indexJ].beneficio + s.f;
				if(novoF > antF)
				{
					melhorou = true;
					s.addFacilidade(solucao[indexJ]);
					iterador++;
					s.trocaPosicao(i, indexJ);
				}
				else
				{
					s.addFacilidade(solucao[i]);
				}
			}
		}
//		media.setValor(iterador);
//		System.out.println(media);
	}

	private int getMelhorBeneficioGA(int apartir)
	{
		int index = 0;
		int melhorBeneficio = 0;
		for(int i = apartir; i < size; i++)
		{
			if((melhorBeneficio < solucao[i].beneficio) || (melhorBeneficio == solucao[i].beneficio && rand.nextBoolean()))
			{
				index = i;
				melhorBeneficio = solucao[i].beneficio;
			}
		}
		return index;
	}
}
