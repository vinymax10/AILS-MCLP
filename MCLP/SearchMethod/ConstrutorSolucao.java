package SearchMethod;

import java.util.Random;

import Data.Instance;
import Solution.NoSol;
import Solution.Solucao;

public class ConstrutorSolucao
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];

	protected Random rand = new Random();
	protected int size;
	Instance instancia;
	int beneficio;
	int numFacilidades;

	public ConstrutorSolucao(Instance instancia, Config config)
	{
		this.instancia = instancia;
		this.size = instancia.getSize();
		this.numFacilidades = instancia.getNumFacilidades();
	}

	private void setSolucao(Solucao s)
	{
		this.solucao = s.solucao;
		this.solucaoFixa = s.solucaoFixa;
	}

	public void construir(Solucao s)
	{
		setSolucao(s);
		s.f = 0;

		for(int i = 0; i < size; i++)
		{
			solucao[i].facilidade = false;
			solucao[i].numFacilidades = 0;
			solucao[i].setBeneficioGA(solucaoFixa);
			solucao[i].posicao = i;
		}

		int prox;
		for(int i = 0; i < numFacilidades; i++)
		{
			prox = i + rand.nextInt(size - i);

			s.trocaPosicao(i, prox);
			s.addFacilidade(solucao[i]);
		}

	}

}
