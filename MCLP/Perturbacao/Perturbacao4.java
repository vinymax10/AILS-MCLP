package Perturbacao;

import java.util.Arrays;

import AjusteOmega.AjusteOmega;
import Comparador.IntersecDecrescente;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//remover as facilidades com maior prob as menores esclusividade 
public class Perturbacao4 extends Perturbacao
{
	IntersecDecrescente decrescente=new IntersecDecrescente();
	private NoSol facildiades[];
	
	public Perturbacao4(Instancia instancia,MetaHeuristicas.Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica)
	{
		super(instancia, config, configuradoresOmega,estatistica);
		this.tipoPerturbacao=TipoPerturbacao.Perturbacao4;
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
			solucao[i].calcIntersec(solucaoFixa);
		}
		
		Arrays.sort(facildiades,0,numFacilidades,decrescente);

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
