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
public class Perturbacao5 extends Perturbacao
{
	VariacaoCrescente crescente=new VariacaoCrescente();
	private NoSol facildiades[];
	
	public Perturbacao5(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao5;
		this.facildiades=new NoSol[numFacilidades];
	}

	public void perturbar(Solucao s)
	{
		setSolucao(s);
		
//		---------------------------------------------------------------------
		
		int pos,lin,col;
		for (int i = 0; i < numFacilidades; i++) 
		{
			facildiades[i]=solucao[i];
			solucao[i].variacao=estatistica.getVariacao()[solucao[i].nome];
		}
		
		Arrays.sort(facildiades,0,numFacilidades,crescente);

		for (int i = 0; i < omega; i++)
		{
			pos=rand.nextInt(numFacilidades*numFacilidades);
			lin=pos/numFacilidades;
			col=pos%numFacilidades;
			
			if(lin>col)
				pos=col;
			else
				pos=lin;
			
			noSai=facildiades[pos];
			s.removerFacilidade(noSai);
			
			noEntra=escolheNoAdd(noSai);
			s.addFacilidade(noEntra);
			
			s.trocaPosicao(noSai.posicao, noEntra.posicao);
			facildiades[pos]=noEntra;
		}
	}
}
