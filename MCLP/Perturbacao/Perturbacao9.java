package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.DistanciaCrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remocao das facilidade de forma concentrica.

public class Perturbacao9 extends Perturbacao
{
	DistanciaCrescente distanciaCrescente=new DistanciaCrescente();
	private NoSol facildiades[];

	public Perturbacao9(Instancia instancia,MetaHeuristicas.Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao9;
		this.facildiades=new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
//		int indexSai,indexEntra;
		
		int indexNoCentral=rand.nextInt((int)(numFacilidades));
		
		for (int i = 0; i < numFacilidades; i++)
		{
			solucao[i].distancia=instancia.getDist()[solucao[i].nome][solucao[indexNoCentral].nome];
			facildiades[i]=solucao[i];
		}

		Arrays.sort(facildiades,0,numFacilidades,distanciaCrescente);
		
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
