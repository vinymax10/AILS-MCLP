package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.EsclusividadeCrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remover as facilidades com maior prob as menores esclusividade 
public class Perturbacao3 extends Perturbacao
{
	EsclusividadeCrescente crescente=new EsclusividadeCrescente();
	private NoSol facildiades[];
	
	public Perturbacao3(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao3;
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
			solucao[i].calcEsclusividade(solucaoFixa);
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
