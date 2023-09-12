package Perturbacao;

import AjusteOmega.AjusteOmega;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Solucao;

//remocao das facilidade de forma aleatoria.
public class Perturbacao1 extends Perturbacao
{
	public Perturbacao1(Instancia instancia,MetaHeuristicas.Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
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
