package Perturbation;

import java.util.Arrays;

import Auxiliary.DistanciaCrescente;
import Data.Instance;
import DiversityControl.AjusteOmega;
import Solution.NoSol;
import Solution.Solucao;

//remocao das facilidade de forma concentrica.

public class Concentric extends Perturbacao
{
	DistanciaCrescente distanciaCrescente = new DistanciaCrescente();
	private NoSol facildiades[];

	public Concentric(Instance instancia, SearchMethod.Config config, AjusteOmega configuradoresOmega)
	{
		super(instancia, config, configuradoresOmega);
		this.tipoPerturbacao = TipoPerturbacao.Concentric;
		this.facildiades = new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);

//		---------------------------------------------------------------------
//		int indexSai,indexEntra;

		int indexNoCentral = rand.nextInt((int) (numFacilidades));

		for(int i = 0; i < numFacilidades; i++)
		{
			solucao[i].distancia = instancia.getDist()[solucao[i].nome][solucao[indexNoCentral].nome];
			facildiades[i] = solucao[i];
		}

		Arrays.sort(facildiades, 0, numFacilidades, distanciaCrescente);

		for(int i = 0; i < omega; i++)
		{
			noSai = facildiades[i];
			s.removerFacilidade(noSai);

			noEntra = escolheNoAdd(noSai);
			s.addFacilidade(noEntra);

			s.trocaPosicao(noSai.posicao, noEntra.posicao);
		}
	}
}
