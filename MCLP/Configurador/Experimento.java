package Configurador;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import AjusteOmega.TipoAjusteOmega;
import BuscaLocal.TipoBL;
import ConstrutorInicial.TipoConst;
import CriterioAceitacao.TipoCriterioAceitacao;
import Dados.DadosInstancia;
import Dados.Instancias;
import MetaHeuristicas.Config;
import MetaHeuristicas.TipoEscolhaSolPR;
import PathReLinking.TipoPathReLinking;
import Perturbacao.HeuristicaAdicao;
import Perturbacao.TipoPerturbacao;

public class Experimento
{
	List <ResultMedio> resultados;
	
	ArrayList<Amostra> tarefas=new ArrayList<Amostra>();
	
	private Config config=new Config();
	private String attribute;
	private String valores[];
	DecimalFormat df=new DecimalFormat("0.00",new DecimalFormatSymbols(Locale.FRANCE)); 
	ColetaniaResult coletaniaResults[];
	int contColetaniasTerminaram=0;
	int threads;
	
	public void addExperimento(Config config,int rodadas,double tempoLimite,
	DadosInstancia dadosInstancia,ResultMedio resultMedio)
	{
		for (int k = 0; k < rodadas; k++) 
		{
			tarefas.add(new Amostra(config,dadosInstancia,tempoLimite,resultMedio,this));
		}
	}
	
	public void executar(String valores[],String attribute,int rodadas,
	double tempoLimite, int threads, int inicioInstancias,int fimInstancias)
	{
		this.valores=valores;
		this.attribute=attribute;
		Instancias instancias=new Instancias();
		this.threads=threads;
		System.out.println("MAXTHREADS: "+threads);
		System.out.println("Instancia variando "+attribute+"\n");
		System.out.println("MCLP AILSPR com BL no PR");
		
		System.out.println(config);

		System.out.println("rodadas: "+rodadas+" tempoLimite: "+tempoLimite);
		
		coletaniaResults=new ColetaniaResult[fimInstancias-inicioInstancias];
		for (int k = inicioInstancias; k < fimInstancias; k++) 
		{
			coletaniaResults[k-inicioInstancias]=new ColetaniaResult(valores,
			instancias.instancias[k].nome+" "+instancias.instancias[k].numFacilidades+" "+instancias.instancias[k].raio
			,this,attribute);
			
			for (int i = 0; i < valores.length; i++) 
			{
				configAttribute(i);
				ResultMedio resultMedio=new ResultMedio(rodadas,coletaniaResults[k-inicioInstancias]);
				coletaniaResults[k-inicioInstancias].addResultMedio(resultMedio);
				addExperimento(config,rodadas,tempoLimite,instancias.instancias[k],resultMedio);
			}
		}
	}
	
	public synchronized void terminei()
	{
		contColetaniasTerminaram++;
		if(contColetaniasTerminaram==coletaniaResults.length)
			imprimirTbFinal();
	}
	
	public static void main(String[] args) 
	{
		int rodadas=Integer.valueOf(args[0]);
		double tempoLimite=Double.valueOf(args[1]);
		int threads=Integer.valueOf(args[2]);
		int inicioInstancias=Integer.valueOf(args[3]);
		int fimInstancias=Integer.valueOf(args[4]);
		String attribute=args[5];
		String valores[]=args[6].split(",");

//		int rodadas=1;
//		double tempoLimite=0.1;
//		int threads=2;
//		int inicioInstancias=0;
//		int fimInstancias=2;
//		String valores[]= {"0.1","0.2","0.3","0.4"};
//		String attribute="eta";
		
		Experimento experimento=new Experimento();
		experimento.executar(valores,attribute, rodadas,
		tempoLimite, threads, inicioInstancias, fimInstancias);
		experimento.iniciar();
	}
	
	public void iniciar()
	{
		int MAXTHREADS=Math.min(tarefas.size(),this.threads);
		for (int i = 0; i < MAXTHREADS; i++) 
		{
			Thread thread=new Thread(tarefas.remove(0));
			thread.start();
		}
	}
	
	public synchronized void avisarTerminou() 
	{
		if(tarefas.size()>0)
		{
			Thread thread=new Thread(tarefas.remove(0));
			thread.start();
		}
	}
	
	public void imprimirTbFinal()
	{
		String tbFinal="\t";
		for (int i = 0; i < valores.length; i++) 
			tbFinal+=valores[i]+"\t";
		
		tbFinal+="\n";
		for (int i = 0; i < coletaniaResults.length; i++)
		{
			tbFinal+=coletaniaResults[i].nome+"\t";
			for (int j = 0; j < coletaniaResults[i].resultMedio.length; j++)
			{
				tbFinal+=coletaniaResults[i].resultMedio[j].getGap()+"\t";
			}
			tbFinal+="\n";
			
		}
		
		System.out.println(tbFinal);
	}
	
	public void configAttribute(int index)
	{
		switch(attribute)
		{
		case "fluxoIdeal": config.setFluxoIdeal(Double.valueOf(valores[index]));break;
		case "sizeElite": config.setSizeElite(Integer.valueOf(valores[index]));break;
		case "numIterUpdate": config.setNumIterUpdate(Integer.valueOf(valores[index]));break;
		case "criterioAceitacao": config.setCriterioAceitacao(TipoCriterioAceitacao.valueOf(valores[index]));break;
		case "tipoAjusteOmega": config.setTipoAjusteOmega(TipoAjusteOmega.valueOf(valores[index]));break;
		case "eta": config.setEta(Double.valueOf(valores[index]));break;
		case "relative": config.setRelative(Double.valueOf(valores[index]));break;
		case "distLearning": config.setDistLearning(Double.valueOf(valores[index]));break;
		case "prob": config.setProb(Double.valueOf(valores[index]));break;
		case "etaMin": config.setEtaMin(Double.valueOf(valores[index]));break;
		case "etaMax": config.setEtaMax(Double.valueOf(valores[index]));break;
		case "omegaFixo": config.setOmegaFixo(Integer.valueOf(valores[index]));break;
		case "omegaMin": config.setOmegaMin(Integer.valueOf(valores[index]));break;
		case "omegaMax": config.setOmegaMax(Integer.valueOf(valores[index]));break;
		case "limiteKnn": config.setLimiteKnn(Integer.valueOf(valores[index]));break;
		case "numChilds": config.setNumChilds(Integer.valueOf(valores[index]));break;
		case "periodicidade": config.setPeriodicidade(Integer.valueOf(valores[index]));break;
		case "sizeJanelaGA": config.setSizeJanelaGA(Integer.valueOf(valores[index]));break;
//		case "tipoAjusteDist": config.setTipoAjusteDist(TipoAjusteDist.valueOf(valores[index]));break;
		case "distM": config.setDistM(Integer.valueOf(valores[index]));break;
		case "numPR": config.setNumPR(Integer.valueOf(valores[index]));break;
		case "iteracaoSemMelhora": config.setIteracaoSemMelhora(Integer.valueOf(valores[index]));break;
		case "distM2": config.setDistM2(Integer.valueOf(valores[index]));break;
		case "distElite": config.setDistElite(Integer.valueOf(valores[index]));break;
		case "tipoBL": config.setTipoBL(TipoBL.valueOf(valores[index]));break;
		case "tipoPathReLinking": config.setTipoPathReLinking(TipoPathReLinking.valueOf(valores[index]));break;
		case "tipoEscolhaSolPR": config.setTipoEscolhaSolPR(TipoEscolhaSolPR.valueOf(valores[index]));break;
		case "heuristicaAdicao": config.setHeuristicaAdicao(getHeuristicaAdicao(valores[index]));break;
		case "perturbacao": config.setPerturbacao(getTipoPerturbacao(valores[index]));break;
		case "tipoConst": config.setTipoConst(TipoConst.valueOf(valores[index]));break; 
		}
	}
	
	private TipoPerturbacao[] getTipoPerturbacao(String str)
	{
		String vet[]=str.split("-");
		TipoPerturbacao[] tipoPerturbacoes=new TipoPerturbacao[vet.length];
		for (int i = 0; i < tipoPerturbacoes.length; i++) 
			tipoPerturbacoes[i]=TipoPerturbacao.valueOf(vet[i]);
		
		return tipoPerturbacoes;
	}
	
	private HeuristicaAdicao[] getHeuristicaAdicao(String str)
	{
		String vet[]=str.split("-");
		HeuristicaAdicao[] heuristicasAdicao=new HeuristicaAdicao[vet.length];
		for (int i = 0; i < heuristicasAdicao.length; i++) 
			heuristicasAdicao[i]=HeuristicaAdicao.valueOf(vet[i]);
		
		return heuristicasAdicao;
	}
	
}
