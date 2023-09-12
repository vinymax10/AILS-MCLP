package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.VariacaoCrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remover as facilidades dentro da janela com interseccao não negativa 
//adiciona aleatoriamente.

public class Perturbacao6 extends Perturbacao
{
	VariacaoCrescente crescente=new VariacaoCrescente();
	private NoSol facildiades[];

	public Perturbacao6(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao6;
		this.facildiades=new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		
		for (int i = 0; i < numFacilidades; i++) 
		{
			facildiades[i]=solucao[i];
			solucao[i].variacao=estatistica.getVariacao()[solucao[i].nome];
		}
		
		Arrays.sort(facildiades,0,numFacilidades,crescente);
		
		for (int i = 0; i < omega; i++)
		{
			noSai=facildiades[i];
			s.removerFacilidade(noSai);
			
			noEntra=escolheNoAdd(noSai);
			s.addFacilidade(noEntra);
			
			s.trocaPosicao(noSai.posicao, noEntra.posicao);
		}
	}
}
