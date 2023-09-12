package Configurador;

import java.text.DecimalFormat;

public class ColetaniaResult 
{
	ResultMedio resultMedio[];
	int top=0;
	DecimalFormat df2=new DecimalFormat("0.00"); 
	int contTerminou=0;
	String nome;
	String valores[];
	Experimento experimento;
	String attribute;
	
	public ColetaniaResult(String valores[],String nome,Experimento experimento,String attribute) 
	{
		this.valores=valores;
		this.resultMedio= new ResultMedio[valores.length];
		this.nome=nome;
		this.experimento=experimento;
		this.attribute=attribute;
	}
	
	public void addResultMedio(ResultMedio x)
	{
		resultMedio[top++]=x;
	}
	
	public synchronized void terminei()
	{
		contTerminou++;
		if(contTerminou==resultMedio.length)
		{
			System.out.println("===============================================================================");
			System.out.println(nome);
			for (int i = 0; i < valores.length; i++) 
				System.out.print(valores[i]+"\t");

			System.out.println(" | "+attribute+"\n-------------------------------------------------------------------------------");

			System.out.println(this);
			
			experimento.terminei();
		}
	}
	
	public void limpar()
	{
		top=0;
	}
	
	public String Learning(String instancia)
	{
		String str="";
		int indexBest=0;
		double bestgap=Double.MAX_VALUE;
		for (int i = 0; i < resultMedio.length; i++) 
		{
			if(resultMedio[i].gap<bestgap)
			{
				indexBest=i;
				bestgap=resultMedio[i].gap;
			}
		}
		for (int i = 0; i < resultMedio.length; i++) 
		{
			if(i<indexBest)
			{
				str+="\n"+instancia+"\t"+resultMedio[i].getEta()+"\t"+resultMedio[i].getFluxomedio()+"\t"+resultMedio[i].getDistLearning()+"\t"+"\tAUMENTA";
			}
			else if(i==indexBest)
				str+="\n"+instancia+"\t"+resultMedio[i].getEta()+"\t"+resultMedio[i].getFluxomedio()+"\t"+resultMedio[i].getDistLearning()+"\t"+"\tOK";
			else
				str+="\n"+instancia+"\t"+resultMedio[i].getEta()+"\t"+resultMedio[i].getFluxomedio()+"\t"+resultMedio[i].getDistLearning()+"\t"+"\tDIMINUI";
		}
		return str;
	}
	
	@Override
	public String toString() 
	{
		String str="";
		
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getGap()+"\t";
		
			str+=" | Gap\n";
		

		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getDP()+"\t";
		
			str+=" | DP\n";

		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getBestGap()+"\t";
		
			str+=" | BestGap\n";
		
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].get1QGap()+"\t";
		
			str+=" | 1QGap\n";
	
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getMedianGap()+"\t";
		
			str+=" | MedianGap\n";
		
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].get3QGap()+"\t";
		
			str+=" | 3QGap\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getWorstGap()+"\t";
		
			str+=" | WorstGap\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getEta()+"\t";
		
			str+=" | Eta\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getFluxomedio()+"\t";
		
			str+=" | fluxomedio\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getDistIdealMedioGlobal()+"\t";
		
			str+=" | distIdealMedioGlobal\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getDistLearning()+"\t";
		
			str+=" | distLearning\n";

		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getIteradorM()+"\t";
		
			str+=" | IteradorM\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getGapMediaC()+"\t";
		
			str+=" | GapMediaC\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getGapMediaBL()+"\t";
		
			str+=" | GapMediaBL\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getMediaF()+"\t";
		
			str+=" | MediaF\n";
		
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getBestF()+"\t";
		
			str+=" | BestF\n";
		
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].get1Q()+"\t";
		
			str+=" | 1Q\n";
	
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getMedianF()+"\t";
		
			str+=" | MedianF\n";
		
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].get3Q()+"\t";
		
			str+=" | 3Q\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getWorstF()+"\t";
		
			str+=" | WorstF\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getOmega()+"\t";
		
			str+=" | Omega\n";	
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getdistC()+"\t";
		
			str+=" | distC\n";

		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getdistHeu()+"\t";
		
			str+=" | distHeu\n";
			
		for (int i = 0; i < resultMedio.length; i++) 
			str+=resultMedio[i].getdistTabu()+"\t";
		
			str+=" | distTabu\n";
				
		str+="\n";
		return str;
	}
	
	
}
