package TesteFinal;

import Dados.Instancias;
import MetaHeuristicas.Config;

public class Datasets 
{
	public static void main(String[] args) 
	{
		Config config=new Config();
		
		System.out.println(config);
		
		System.out.println("MAXTHREADS: "+Runtime.getRuntime().availableProcessors());
		System.out.println("Instancia DataSets MCLP AILSPR"
		);
		
		int rodadas=Integer.valueOf(args[0]);
		double tempoLimite=Double.valueOf(args[1]);
		int MAXTHREADS=Integer.valueOf(args[2]);
		int inicioInstancias=Integer.valueOf(args[3]);
		int fimInstancias=Integer.valueOf(args[4]);

//		int rodadas=9;
//		int tempoLimite=1;
//		int MAXTHREADS=1;
//		int inicioInstancias=0;
//		int fimInstancias=25; 	
		
		RunInstancia run;
		System.out.println("rodadas: "+rodadas+" tempoLimite: "+tempoLimite);
		ResultMedio resultMedio;
		Instancias instancias=new Instancias();
		System.out.print("Instancia\tp\tr\tMediaF\tGap\tBestF\tTempo\tMedian\tWorst\n");
		for (int k = inicioInstancias; k < fimInstancias; k++) 
		{
			System.out.print(instancias.instancias[k].nome+"\t"+instancias.instancias[k].numFacilidades+"\t"+instancias.instancias[k].raio+"\t");
			
			run=new RunInstancia(config,rodadas,tempoLimite,instancias.instancias[k]);
			
			run.setMAXTHREADS(MAXTHREADS);
			resultMedio=run.executa();
			System.out.print(resultMedio.getMediaF()+"\t"+resultMedio.getGap()
			+"\t"+resultMedio.getBestF()+"\t"+resultMedio.gettempoMF()
			+"\t"+resultMedio.getMedianF()+"\t"+resultMedio.getWorstF()+"\n");
			
		}
	}

}
