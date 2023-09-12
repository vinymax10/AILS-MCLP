package Perturbation;

import AjusteOmega.AjusteOmega;
import Data.Instancia;
import Solution.Solucao;

//remocao das facilidade de forma aleatoria.
public class Perturbacao1 extends Perturbacao
{
	public Perturbacao1(Instancia instancia,SearchMethod.Config config,
	AjusteOmega configuradoresOmega)
	{
		super(instancia, config, configuradoresOmega);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao1;
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		for (int i = 0; i < omega; i++)
		{
			noSai=solucao[rand.nextInt((int)(numFacilidades))];
			s.removerFacilidade(noSai);
			
			noEntra=escolheNoAdd(noSai);
			s.addFacilidade(noEntra);
			
			s.trocaPosicao(noSai.posicao, noEntra.posicao);
		}
	}
}
