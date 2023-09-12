package Estatistica;

import java.text.DecimalFormat;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public class Estatistica 
{
	double variacao[];
	int numFacilidades;
	NoSol aux;
	int numIterUpdate;
	int iteracoes;
	DecimalFormat deci=new DecimalFormat("0.00");

	public Estatistica(Instancia instancia,Config config)
	{
		this.variacao=new double[instancia.getSize()];
		this.numFacilidades=instancia.getNumFacilidades();
		this.numIterUpdate=config.getNumIterUpdate();
	}
	
	public void setSolucao(Solucao solucao)
	{
		for (int i = 0; i < numFacilidades; i++) 
		{
			setValor(1,solucao.solucao[i].nome);
		}
		for (int i = numFacilidades; i < variacao.length; i++) 
		{
			setValor(0,solucao.solucao[i].nome);
		}
	}
	
	public double distancia(Solucao solucao)
	{
		double distancia=0;
		for (int i = 0; i < numFacilidades; i++) 
		{
			distancia+=variacao[solucao.solucao[i].nome];
		}
		return distancia/numFacilidades;
	}

	public void setValor(double valor,int position)
	{
		iteracoes++;
		if(iteracoes<numIterUpdate)
			variacao[position]=(variacao[position]*(iteracoes-1)+valor)/iteracoes;
		else
			variacao[position]=((variacao[position]*(1-((double)1/numIterUpdate)))+((valor)*((double)1/numIterUpdate)));
	}

//	public void passaVariacaoSolucao(Solucao solucao)
//	{
//		for (int i = 0; i < variacao.length; i++) 
//		{
//			solucao.solucaoFixa[i].variacao=variacao[i];
//		}
//	}
	
	@Override
	public String toString() 
	{
		String str="";
		for (int i = 0; i < variacao.length; i++) {
			str+=deci.format(variacao[i])+" ";
		}
		return "Estatistica "+str;
	}

	public double[] getVariacao() {
		return variacao;
	}
	
	
}
