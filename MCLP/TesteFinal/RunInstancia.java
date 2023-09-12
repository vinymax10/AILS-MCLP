package TesteFinal;

import java.util.ArrayList;

import Dados.DadosInstancia;
import MetaHeuristicas.Config;

public class RunInstancia 
{
	ArrayList<Amostra> tarefas=new ArrayList<Amostra>();
	Config config;
	int MAXTHREADS;
	int rodadas;
	double tempoLimite;
	ResultMedio resultMedio;
	
	public RunInstancia(Config config,int rodadas,double tempoLimite,
	DadosInstancia dadosInstancia) 
	{
		this.rodadas=rodadas;
		this.tempoLimite=tempoLimite;
		this.resultMedio=new ResultMedio();
		this.config = config.clone();
		
		this.MAXTHREADS=Runtime.getRuntime().availableProcessors();
		
		for (int k = 0; k < rodadas; k++) 
		{
			tarefas.add(new Amostra(config,dadosInstancia,tempoLimite));
		}
	}
	
	public int getMAXTHREADS() {
		return MAXTHREADS;
	}

	public void setMAXTHREADS(int mAXTHREADS) {
		MAXTHREADS = mAXTHREADS;
	}

	public ResultMedio executa() 
	{
		Thread []threads=new Thread[MAXTHREADS];
		int cont=0;
		while(cont<tarefas.size())
		{
			int rodou=0,iterador=0;
			while(iterador<threads.length&&cont<tarefas.size())
			{
				threads[iterador]=new Thread(tarefas.get(cont++));
				threads[iterador].start();
				iterador++;
			}
			rodou=iterador;
			iterador=0;
			while(iterador<rodou)
			{
				try 
				{
					threads[iterador].join();
					resultMedio.setResult(tarefas.get(cont-rodou+iterador).getResult());
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				iterador++;
			}
		}
		return resultMedio;
	}

	public static void main(String[] args) 
	{
//		Run run =new Run(new Config());
//		System.out.println(run.CalculaMedia());
//		System.out.println(run.desvioPadrao());
	}
}
