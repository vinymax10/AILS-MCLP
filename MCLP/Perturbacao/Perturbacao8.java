package Perturbacao;

import AjusteOmega.AjusteOmega;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remover as facilidades com maior prob as menores esclusividade 
public class Perturbacao8 extends Perturbacao
{
	private NoSol facildiades[];

	public Perturbacao8(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao8;
		this.facildiades=new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		
		int numFacilidadesAtual=numFacilidades;
		
		for (int i = 0; i < omega; i++)
		{
			noSai=solucao[rand.nextInt((int)(numFacilidadesAtual))];
			facildiades[i]=noSai;
			s.removerFacilidade(noSai);
			s.trocaPosicao(noSai.posicao, --numFacilidadesAtual);
		}
		
		for (int i = 0; i < omega; i++)
		{
			noSai=facildiades[i];
			noEntra=escolheNoAdd(noSai);
			s.addFacilidade(noEntra);
			
			s.trocaPosicao(numFacilidadesAtual++, noEntra.posicao);
			
		}
	}
}
