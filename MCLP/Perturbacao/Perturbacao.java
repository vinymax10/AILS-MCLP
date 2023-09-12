package Perturbacao;

import java.util.Random;

import AjusteOmega.AjusteOmega;
import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public abstract class Perturbacao 
{
	protected NoSol solucao[];
	protected NoSol solucaoFixa[];
	protected int numFacilidades;

	protected int size;
	protected Instancia instancia;
	protected Config config;
	protected NoSol no,aux;
	protected AjusteOmega ajusteOmega;
	public TipoPerturbacao tipoPerturbacao;
	public double omega;
	protected Random rand=new Random();
	protected HeuristicaAdicao heuristicaAdicao[];
	protected HeuristicaAdicao heuristicaAdicaoEscolhida;
	int pos, cont;
	protected NoSol noSai,noEntra;
	protected Estatistica estatistica;
	private int limiteKnn;
	double prob;
	
	public Perturbacao(Instancia instancia,Config config,
	AjusteOmega configuradoresOmega, Estatistica estatistica) 
	{
		this.instancia=instancia;
		this.estatistica=estatistica;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.ajusteOmega=configuradoresOmega;
		this.heuristicaAdicao=config.getHeuristicaAdicao();
		this.limiteKnn=instancia.getLimiteKnn();
		this.prob=config.getProb();
	}

	protected void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
		this.omega=ajusteOmega.getOmegaReal();
		this.heuristicaAdicaoEscolhida=heuristicaAdicao[rand.nextInt(heuristicaAdicao.length)];
	}

	public AjusteOmega getAjusteOmega() {
		return ajusteOmega;
	}
	
	public TipoPerturbacao getTipoPerturbacao() {
		return tipoPerturbacao;
	}

	public void perturbar(Solucao s){}
	
	protected NoSol escolheNoAdd(NoSol noReferencia)
	{
		switch(heuristicaAdicaoEscolhida)
		{
			case Aleatorio: return solucao[numFacilidades+rand.nextInt(solucao.length-numFacilidades)];
			case Raio: return getNoRaio(noReferencia);
			case BestCusto: return getMelhorBeneficioGA();
			case Raio2: return getNoRaio2(noReferencia);
			case KNN: return getNoKNN(noReferencia);
			case BestCustoBlink: return getMelhorBeneficioGABlink();
		}
		return null;
	}
	
	private NoSol getMelhorBeneficioGA() 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = numFacilidades; i < size; i++) 
		{
			if((melhorBeneficio<solucao[i].beneficio)||(melhorBeneficio==solucao[i].beneficio&&rand.nextBoolean()))
			{
				index=i;
				melhorBeneficio=solucao[i].beneficio;
			}
		}
		return solucao[index];
	}
	
	private NoSol getMelhorBeneficioGABlink() 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = numFacilidades; i < size; i++) 
		{
			if(melhorBeneficio<solucao[i].beneficio&&rand.nextDouble()>prob)
			{
				index=i;
				melhorBeneficio=solucao[i].beneficio;
			}
		}
		return solucao[index];
	}
	
	private NoSol getNoRaio(NoSol no)
	{
		cont=0;
		do
		{
			pos=rand.nextInt(no.possoAtender.length);
			cont++;
			if(!solucaoFixa[no.possoAtender[pos]].facilidade)
				return solucaoFixa[no.possoAtender[pos]];
		}
		while(cont<no.possoAtender.length);
		
//		cont=0;
//		do
//		{
//			pos=rand.nextInt(no.possoAtenderR2.length);
//			cont++;
//			if(!solucaoFixa[no.possoAtenderR2[pos]].facilidade)
//				return solucaoFixa[no.possoAtenderR2[pos]];
//		}
//		while(cont<no.possoAtenderR2.length);
//		
//		cont=0;
//		do
//		{
//			pos=rand.nextInt(solucaoFixa.length);
//			cont++;
//			if(!solucaoFixa[pos].facilidade)
//				return solucaoFixa[pos];
//		}
//		while(cont<solucaoFixa.length);
//			
//		for (int i = 0; i < solucao.length; i++) 
//		{
//			if(!solucaoFixa[i].facilidade)
//				return solucaoFixa[i];
//		}
		return null;
	}
	
	private NoSol getNoRaio2(NoSol no)
	{
		cont=0;
		do
		{
			pos=rand.nextInt(no.possoAtenderR2.length);
			cont++;
			if(!solucaoFixa[no.possoAtenderR2[pos]].facilidade)
				return solucaoFixa[no.possoAtenderR2[pos]];
		}
		while(cont<no.possoAtenderR2.length);
		
		cont=0;
		do
		{
			pos=rand.nextInt(solucaoFixa.length);
			cont++;
			if(!solucaoFixa[pos].facilidade)
				return solucaoFixa[pos];
		}
		while(cont<solucaoFixa.length);
			
		for (int i = 0; i < solucao.length; i++) 
		{
			if(!solucaoFixa[i].facilidade)
				return solucaoFixa[i];
		}
		return null;
	}
	
	private NoSol getNoKNN(NoSol no)
	{
		int pos=0;
		int cont=0;
		
		do
		{
			pos=rand.nextInt(limiteKnn);
			cont++;
			if(!solucaoFixa[instancia.getKnn()[no.nome][pos]].facilidade)
				return solucaoFixa[instancia.getKnn()[no.nome][pos]];
		}
		while(cont<limiteKnn);
		
		cont=0;
		do
		{
			pos=rand.nextInt(solucaoFixa.length);
			cont++;
			if(!solucaoFixa[pos].facilidade)
				return solucaoFixa[pos];
		}
		while(cont<solucaoFixa.length);
			
		for (int i = 0; i < solucao.length; i++) 
		{
			if(!solucaoFixa[i].facilidade)
				return solucaoFixa[i];
		}
		
		return null;
	}
}