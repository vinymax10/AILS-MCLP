package Dados;

import MetaHeuristicas.Config;

public class ConvertCPLEX 
{
	String nome;
	int p;
	int S;

	public ConvertCPLEX(String nome, int p, int s) 
	{
		this.nome = nome;
		this.p = p;
		this.S = s;
	}

	public ConvertCPLEX(DadosInstancia dadosInstancia) 
	{
		this.nome = dadosInstancia.nome;
		this.p = dadosInstancia.numFacilidades;
		this.S = dadosInstancia.raio;
	}

	public void converter()
	{
		Arquivo arq=new Arquivo(nome+".dat");
		Config config=new Config();
		Instancia instancia=new Instancia("Instancias//"+nome+".txt",config,p,S);
		
		int n=instancia.getSize();
		arq.escrever("locais="+n+";");
		arq.escrever("p="+p+";");
		String w="w = [";
		for (int i = 0; i < n; i++)
		{
			if(i<n-1)
				w+=instancia.getPontos()[i].demanda+",";
			else
				w+=instancia.getPontos()[i].demanda+"];";
		}
		arq.escrever(w);
		float [][]dist=instancia.getDist();
		String conjS="";
		arq.escrever("S = [");
		for (int i = 0; i < n; i++)
		{
			conjS="{";
			for (int j = 0; j < n; j++) 
			{
				if(dist[i][j]<=S)
				{
					if(j<n-1)
						conjS+=(j+1)+" ";
					else
						conjS+=(j+1);
				}
			}
			if(i!=n-1)
				conjS+="}";
			else
				conjS+="}];";
			arq.escrever(conjS);
			System.out.println("Escreveu linha: "+i);
		}
		arq.finalizar();
	}
	
	public static void main(String[] args) 
	{
		Instancias instancias=new Instancias();
		int pos=18;	
		ConvertCPLEX problema=new ConvertCPLEX(instancias.instancias[pos]);
		problema.converter();
	}
}	
