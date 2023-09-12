package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.IntersecDecrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remover as facilidades dentro da janela com interseccao não negativa 
//adiciona aleatoriamente.

public class Perturbacao2 extends Perturbacao
{
	IntersecDecrescente decrescente=new IntersecDecrescente();
	private NoSol facildiades[];

	public Perturbacao2(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao2;
		this.facildiades=new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		
		for (int i = 0; i < numFacilidades; i++) 
		{
			facildiades[i]=solucao[i];
			solucao[i].calcIntersec(solucaoFixa);
		}
		
		Arrays.sort(facildiades,0,numFacilidades,decrescente);
		
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
