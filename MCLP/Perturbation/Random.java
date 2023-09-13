package Perturbation;

import Data.Instance;
import DiversityControl.AjusteOmega;
import Solution.Solucao;

public class Random extends Perturbacao
{
	public Random(Instance instancia, SearchMethod.Config config, AjusteOmega configuradoresOmega)
	{
		super(instancia, config, configuradoresOmega);
		this.tipoPerturbacao = TipoPerturbacao.Random;
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);

//		---------------------------------------------------------------------
		for(int i = 0; i < omega; i++)
		{
			noSai = solucao[rand.nextInt((int) (numFacilidades))];
			s.removerFacilidade(noSai);

			noEntra = escolheNoAdd(noSai);
			s.addFacilidade(noEntra);

			s.trocaPosicao(noSai.posicao, noEntra.posicao);
		}
	}
}
