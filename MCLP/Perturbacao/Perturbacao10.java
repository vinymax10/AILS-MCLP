package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.DistanciaCrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remocao das facilidade de forma concentrica.

public class Perturbacao10 extends Perturbacao
{
	DistanciaCrescente distanciaCrescente=new DistanciaCrescente();
	private NoSol facildiades[];

	public Perturbacao10(Instancia instancia,MetaHeuristicas.Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao10;
		this.facildiades=new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		int indexNoCentral=rand.nextInt((int)(numFacilidades));
		
		for (int i = 0; i < numFacilidades; i++)
		{
			facildiades[i]=solucao[i];
			facildiades[i].distancia=instancia.getDist()[solucao[i].nome][solucao[indexNoCentral].nome];
		}

		Arrays.sort(facildiades,0,numFacilidades,distanciaCrescente);
		
		int numFacilidadesAtual=numFacilidades;

		for (int i = 0; i < omega; i++)
		{
			noSai=facildiades[i];
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
