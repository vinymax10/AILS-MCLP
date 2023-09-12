package Elite;

import java.text.DecimalFormat;
import java.util.Random;

import AjustDist.DistIdeal;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Distancia;
import MetaHeuristicas.Solucao;

public abstract class SubConj 
{
	int NumRotas;
	int size;
	Solucao elite[];
	int topElite=0;
	Instancia instancia;
	Config config;
	Random rand=new Random();
	Solucao qualidadeMaisProxima,distMaisProxima,qualidadeMaisProxPior;
	int menorDist;
	boolean executouDist;
	int ordem[];
	int topOrdem=0;
	public double aceitas;
	int topRegeitadas=0;
	DecimalFormat deci=new DecimalFormat("0.00");
	int qtnMenorDistPiorF,qtnMenorDistMelhorF;
	int indexQualityIgualIni,indexQualityIgualFim;
	int qtnQualityIgual;
	int valor;
	int numIterUpdate;
	int posMenorDistMelhorF;
	int pos,lin,col;
	Distancia distEntreSolucoes;
	int indexQualidadeMaisProxima,indexQualidadeMaisProxPior;
	DistIdeal distIdeal;
	boolean atualizou;
	int dist;
	
	public SubConj(Instancia instancia,Config config, 
	Distancia distEntreSolucoes,DistIdeal distIdeal)
	{
		this.instancia=instancia;
		this.config=config;
		this.size = config.getSizeElite();
		this.elite=new Solucao[size];
		this.distIdeal=distIdeal;
		this.distEntreSolucoes=distEntreSolucoes;
		this.ordem=new int[size];
		this.numIterUpdate=config.getNumIterUpdate();

		for (int i = 0; i < ordem.length; i++) 
			ordem[i]=i;
	}
	
	protected void removeTodasDistMenor() 
	{
		Solucao aux;
		for (int i = 0; i < topElite; i++) 
		{
			if(elite[i].distancia<=distIdeal.distIdeal)
			{
				aux=elite[i];
				elite[i]=elite[--topElite];
				elite[topElite]=aux;
				i--;
			}
		}
	}
	
	public void add(Solucao s)
	{
	}
	
	public void iniciaOrdem()
	{
		topOrdem=topElite;
	}
	
	public Solucao getSolucaoDistinta()
	{
		if(topOrdem==0)
			topOrdem=topElite;

		int posicao=rand.nextInt(topOrdem);
		valor=ordem[posicao];
		
		ordem[posicao]=ordem[topOrdem-1];
		ordem[topOrdem-1]=valor;
		topOrdem--;
		
		return elite[valor];
	}
	
	public Solucao getSolucao()
	{
		return elite[rand.nextInt(topElite)];
	}
	
	public Solucao getSolucaoProb()
	{
		pos=rand.nextInt(topElite*topElite);
		lin=pos/topElite;
		col=pos%topElite;
		
		if(lin>col)
			pos=col;
		else
			pos=lin;
		
		return elite[pos];
	}
	
	public boolean isCheio() 
	{
		return topElite==elite.length;
	}
	
	public int getSize() {
		return topElite;
	}

	public Solucao getSolucao(int index)
	{
		return elite[index];
	}
	
	public Solucao getLastSolucao()
	{
		return elite[topElite-1];
	}
	
	public int menorDist(Solucao s,int index)
	{
		int menorDist=Integer.MAX_VALUE;
		int dist;
		
		for (int i = 0; i < topElite; i++) 
		{
			if(i!=index)
			{
				dist=distEntreSolucoes.distancia(s, elite[i]);
				if(dist<menorDist)
					menorDist=dist;
			}
		}
		return menorDist;
	}
	
	public String toString()
	{
		String str="size: "+size+" topElite: "+topElite+"\n";
		for (int i = 0; i < topElite; i++) 
			str+=""+i+": "+elite[i].f+" d: "+menorDist(elite[i],i)+"\n";
		return str;
	}

	public int getValor() {
		return valor;
	}

	public void removeSolucao()
	{
		if(topElite>5)
		{
			Solucao aux;
			int pos=5+rand.nextInt(topElite-5);
			aux=elite[pos];
			elite[pos]=elite[--topElite];
			elite[topElite]=aux;
		}
	}

}
