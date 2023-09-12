package Dados;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import MetaHeuristicas.Config;

public class Instancia 
{
	private float dist[][];
	private int size;
	private String nome;
	private Config config;
	private String str[];
	private Ponto pontos[];
	private TipoEdgeType tipoEdgeType;
	private double somaDemanda;
	private int numFacilidades,raio;
	private float distancia;
	private int limiteKnn;
	private int knn[][];
	
	public Instancia(String nome,Config config,int numFacilidades,int raio)
	{ 
		this.nome=nome;
		this.config=config;
		this.numFacilidades=numFacilidades;
		this.raio=raio;
		
		BufferedReader in;
		try 
		{
			in = new BufferedReader(new FileReader(nome));
			
			str=in.readLine().split(":");
			while(!str[0].trim().equals("EOF"))
			{
				switch(str[0].trim())
				{
					case "DIMENSION": size=Integer.valueOf(str[1].trim());break;
					case "EDGE_WEIGHT_TYPE":
					case "EDGE_WEIGHT_FORMAT": setTipoCoord(str[1].trim());break;
					case "NODE_COORD_SECTION": leituraCoord(in);break;
					case "DEMAND_SECTION": leituraDemanda(in);break;
				}
				str=in.readLine().split(":");
			}
			
		} 
		catch (IOException e) {
	    	System.out.println("Erro ao Ler Arquivo");
	    }
	}

	private void leituraDemanda(BufferedReader in)
	{
		try {
			somaDemanda=0;
			for (int i = 0; i < size; i++) 
			{
				str=in.readLine().trim().replaceAll("\\s+"," ").split("[' '|'\t']");
				pontos[i].demanda=Integer.valueOf(str[0]);
				somaDemanda+=pontos[i].demanda;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void leituraCoord(BufferedReader in)
	{
//		

		pontos=new Ponto [size];
		for (int i = 0; i < pontos.length; i++) 
			pontos[i]=new Ponto(i);
		
		try 
		{
			for (int i = 0; i < size; i++) 
			{
				str=in.readLine().trim().replaceAll("\\s+"," ").split("[' '|'\t']");
				pontos[i].x=Double.valueOf(str[0].trim());
				pontos[i].y=Double.valueOf(str[1].trim());
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		dist=new float[size][size];
		
		limiteKnn=Math.min(config.getLimiteKnn(), size-1);
		knn=new int[size][limiteKnn];
		
		processaPossoAtenderKnn();
	}
	
	private void processaPossoAtenderKnn()
	{
		int cont=0;
		int sizePossoAtender=0;
		int sizePossoAtenderR2=0;

		NoKnn[]VizKnn=new NoKnn[size-1];
		
		for (int i = 0; i < VizKnn.length; i++) 
			VizKnn[i]=new NoKnn();
		
		for (int i = 0; i < size; i++) 
		{
			cont=0;
			sizePossoAtender=0;
			sizePossoAtenderR2=0;
			for (int j = 0; j < size; j++) 
			{
				if(i==j)
					distancia=0;
				else
					distancia=distancia(pontos[i],pontos[j]);
				
				dist[i][j]=distancia;
				dist[j][i]=distancia;
				
				if(i!=j)
				{
					VizKnn[cont].dist=distancia;
					VizKnn[cont].nome=j;
					cont++;
				}
				
				if(distancia<=(raio*2))
				{
					sizePossoAtenderR2++;
					
					if(distancia<=raio)
						sizePossoAtender++;
				}
			}
			
			Arrays.sort(VizKnn);
			for (int j = 0; j < limiteKnn; j++) 
				knn[i][j]=VizKnn[j].nome;
			
			pontos[i].possoAtender=new int [sizePossoAtender];
			pontos[i].possoAtenderR2=new int [sizePossoAtenderR2];
			
			sizePossoAtender=0;
			sizePossoAtenderR2=0;
			
			for (int j = 0; j < size; j++) 
			{
				if(dist[i][j]<=(raio*2))
				{
					pontos[i].possoAtenderR2[sizePossoAtenderR2++]=j;
					
					if(dist[i][j]<=raio)
						pontos[i].possoAtender[sizePossoAtender++]=j;
				}
			}
		}
	}
	
	public void processaPossoAtender()
	{
		int possoAtender[]=new int[size];
		int possoAtenderR2[]=new int[size];
		
		for (int i = 0; i < size; i++) 
		{
			for (int j = i; j < size; j++) 
			{
				if(i==j)
					distancia=0;
				else
					distancia=distancia(pontos[i],pontos[j]);
				
				dist[i][j]=distancia;
				dist[j][i]=distancia;
				
				if(distancia<=(raio*2))
				{
					possoAtenderR2[i]++;
					if(i!=j)
						possoAtenderR2[j]++;
					
					if(distancia<=raio)
					{
						possoAtender[i]++;
						if(i!=j)
							possoAtender[j]++;
					}
				}
			}
		}
		
		for (int i = 0; i < size; i++) 
		{
			pontos[i].possoAtender=new int [possoAtender[i]];
			possoAtender[i]=0;
			pontos[i].possoAtenderR2=new int [possoAtenderR2[i]];
			possoAtenderR2[i]=0;
		}
		
		for (int i = 0; i < size; i++) 
		{
			for (int j = i; j < size; j++) 
			{
				if(getTipoAdj(i,j)!=TipoAdj.NaoAdj)
				{
					pontos[i].possoAtenderR2[possoAtenderR2[i]++]=j;
					if(i!=j)
						pontos[j].possoAtenderR2[possoAtenderR2[j]++]=i;
					
					if(getTipoAdj(i,j)==TipoAdj.Raio)
					{
						pontos[i].possoAtender[possoAtender[i]++]=j;
						if(i!=j)
							pontos[j].possoAtender[possoAtender[j]++]=i;
					}
				}
			}
		}
	}
	
	public TipoAdj getTipoAdj(int i,int j)
	{
		if(dist[i][j]<=(raio))
			return TipoAdj.Raio;
		
		if(dist[i][j]<=(raio*2))
			return TipoAdj.Raio2;
		
		return TipoAdj.NaoAdj;
	}
	
	
	
//	public void processaPossoAtender2()
//	{
//		int cont=0,contR2=0;
//		for (int i = 0; i < size; i++) 
//		{
//			cont=0;
//			contR2=0;
//			for (int j = 0; j < size; j++) 
//			{
//				if(i==j)
//					dist=0;
//				else
//					dist=distancia(pontos[i],pontos[j]);
////					System.out.println("dist: "+dist);
//				
//				if(dist<=(raio*2))
//				{
//					contR2++;
//					if(dist<=raio)
//					{
//						cont++;
//						hash[i][j]=TipoAdj.Raio;
//					}
//					else
//					{
//						hash[i][j]=TipoAdj.Raio2;
//					}
//				}
//				else
//				{
//					hash[i][j]=TipoAdj.NaoAdj;
//				}
//			}
//			
//			pontos[i].possoAtender=new int [cont];
//			pontos[i].possoAtenderR2=new int [contR2];
//			
//			cont=0;
//			contR2=0;
//			
//			for (int j = 0; j < size; j++) 
//			{
//				if(hash[i][j]!=TipoAdj.NaoAdj)
//				{
//					pontos[i].possoAtenderR2[contR2++]=j;
//					if(hash[i][j]==TipoAdj.Raio)
//						pontos[i].possoAtender[cont++]=j;
//				}
//			}
//			
////			System.out.println("possoAtender: "+i);
////			System.out.println(Arrays.toString(pontos[i].possoAtender));
////			System.out.println("possoAtenderR2: "+i);
////			System.out.println(Arrays.toString(pontos[i].possoAtenderR2));
//		}
//	}
	
	private void setTipoCoord(String str)
	{
		if(str.equals("EUC_2D"))
			tipoEdgeType=TipoEdgeType.EUC_2D;
		else if(str.equals("EXPLICIT"))
			tipoEdgeType=TipoEdgeType.EXPLICIT;
		else if(str.equals("COORD"))
			tipoEdgeType=TipoEdgeType.Coord;
	}
	
	private float distanciaEUC_2D(double x1,double y1,double x2,double y2)
	{
		double b=x1-x2;
		double c=y1-y2;
		return (float)Math.sqrt((b*b)+(c*c));
	}
	
	private float distanciaCoord(double Lat1, double Long1,double Lat2,double Long2)
	{
		// Convers�o de graus pra radianos das latitudes
		double firstLatToRad = Math.toRadians(Lat1);
		double secondLatToRad = Math.toRadians(Lat2);

		// Diferen�a das longitudes
		double deltaLongitudeInRad = Math.toRadians(Long2
		- Long1);

		// C�lcula da dist�ncia entre os pontos
		return (float)Math.acos(Math.cos(firstLatToRad) * Math.cos(secondLatToRad)
		* Math.cos(deltaLongitudeInRad) + Math.sin(firstLatToRad)
		* Math.sin(secondLatToRad))
		* 6378100;
	}
	
	private float distancia(Ponto a, Ponto b)
	{
		if(tipoEdgeType==TipoEdgeType.Coord)
			return distanciaCoord(a.x,a.y,b.x,b.y);
		else
			return distanciaEUC_2D(a.x,a.y,b.x,b.y);
	}
	
//	public void carregarHash()
//	{
//		hash=new boolean[size][size];
//		for (int i = 0; i < size; i++) 
//		{
//			for (int j = i; j < size; j++)
//			{
//				if(distancia(pontos[i],pontos[j])<=raio)
//				{
//					hash[i][j]=true;
//					hash[j][i]=true;
//				}
//				else
//				{
//					hash[i][j]=false;
//					hash[j][i]=false;
//				}
//			}
//		}
//	}
	
//	public void setDistancias()
//	{
//		distancia=new double[size][size];
//		double c;
//		for (int i = 0; i < size; i++) 
//		{
//			for (int j = i+1; j < size; j++)
//			{
//				c=distancia(pontos[i],pontos[j]);
//
//				distancia[i][j] = c; 				
//				distancia[j][i] = c;
//			}
//		}
//	}
	
	public String toString()
	{
		String str="";
		for (int i = 0; i < 10; i++) 
		{
			for (int j = 0; j < 10; j++) 
			{
				str+=dist[i][j]+"\t";				
			}
			str+="\n";
		}
		return str;
	}
	
	public int getSize() {
		return size;
	}

	public float[][] getDist() {
		return dist;
	}

	public String getNome() {
		return nome;
	}

	public Ponto[] getPontos() {
		return pontos;
	}

	public int getNumFacilidades() {
		return numFacilidades;
	}

	public int getRaio() {
		return raio;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public double getSomaDemanda() {
		return somaDemanda;
	}

	public void setSomaDemanda(double somaDemanda) {
		this.somaDemanda = somaDemanda;
	}

	public int[][] getKnn() {
		return knn;
	}

	public int getLimiteKnn() {
		return limiteKnn;
	}

	public static void main(String[] args) 
	{
		Config config=new Config();
		Instancia instancia=new Instancia("Instancias//NewYork.txt",config,50,1000);
		int soma=0;
		for (int i = 0; i < instancia.getPontos()[1].possoAtender.length; i++)
		{
			int ponto=instancia.getPontos()[1].possoAtender[i];
			System.out.println("ponto: "+ponto);
			soma+=instancia.getPontos()[ponto].demanda;
			System.out.println(instancia.getPontos()[ponto].demanda);
		}
		System.out.println("soma: "+soma);
	}
	
}
