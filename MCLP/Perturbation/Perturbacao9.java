package Perturbation;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Auxiliary.DistanciaCrescente;
import Data.Instancia;
import Solution.NoSol;
import Solution.Solucao;

//remocao das facilidade de forma concentrica.

public class Perturbacao9 extends Perturbacao
{
	DistanciaCrescente distanciaCrescente=new DistanciaCrescente();
	private NoSol facildiades[];

	public Perturbacao9(Instancia instancia,SearchMethod.Config config,
	AjusteOmega configuradoresOmega)
	{
		super(instancia, config, configuradoresOmega);
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
