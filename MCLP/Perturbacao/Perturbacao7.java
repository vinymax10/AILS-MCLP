package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.VariacaoCrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remover as facilidades com maior prob as menores esclusividade 
public class Perturbacao7 extends Perturbacao
{
	VariacaoCrescente crescente=new VariacaoCrescente();
	private NoSol facildiades[];
	
	public Perturbacao7(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao7;
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
