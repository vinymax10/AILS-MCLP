package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import Data.Arquivo;
import Data.Instancia;
import Data.Instancias;
import SearchMethod.Config;

public class Solucao implements Comparable<Solucao>
{
	public int size;
	public NoSol solucao[];
	public NoSol solucaoFixa[];
	public int numFacilidades,raio;
	Random rand=new Random();
	public int f;
	int contNumberFacilite[];
	public int distancia;
	
	public Solucao(Instancia instancia, Config config)
	{
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.raio=instancia.getRaio();
		this.contNumberFacilite=new int[size];
		
		solucao=new NoSol [size];
		solucaoFixa=new NoSol [size];
		
		for (int i = 0; i < size; i++) 
		{
			solucao[i]=new NoSol(instancia.getPontos()[i]);
			solucaoFixa[i]=solucao[i];
		}
	}
	
	public void clone(Solucao x) 
	{
		this.f=x.f;
		for (int i = 0; i < solucaoFixa.length; i++) 
		{
			this.solucaoFixa[i].facilidade=x.solucaoFixa[i].facilidade;
			this.solucaoFixa[i].numFacilidades=x.solucaoFixa[i].numFacilidades;
			this.solucaoFixa[i].escolhido=false;
			this.solucaoFixa[i].beneficio=x.solucaoFixa[i].beneficio;
			this.solucaoFixa[i].acrescimo=0;
			
			this.solucao[i]=this.solucaoFixa[x.solucao[i].nome];
			this.solucao[i].posicao=i;
		}
//		Arrays.sort(solucao,crescente);
//		if(!validarSolucao())
//			System.out.println("Erro no clone ");
		
//		System.out.println("--------------------------");
//		System.out.println(this);
	}
	
	public void embaralharFacilidades()
	{
		NoSol aux;
		int a,b;
		for (int i = 0; i < numFacilidades; i++)
		{
			a=rand.nextInt(numFacilidades);
			b=rand.nextInt(numFacilidades);

			aux=solucao[a];
			solucao[a]=solucao[b];
			solucao[b]=aux;
			
			solucao[a].posicao=a;
			solucao[b].posicao=b;
		}
	}
	
	public boolean validarSolucao()
	{
		int QntP=0;
		int soma=0;
		
		for (int i = 0; i < size; i++) 
			contNumberFacilite[i]=0;
		
		for (int i = 0; i < size; i++) 
		{
			if(solucao[i].facilidade)
			{
				for (int j = 0; j < solucao[i].possoAtender.length; j++)
					contNumberFacilite[solucao[i].possoAtender[j]]++;
				
				QntP++;
			}
			
			if(solucao[i].numFacilidades>=1)
				soma+=solucao[i].demanda;
		}
		
		for (int i = 0; i < size; i++)
		{
			if(contNumberFacilite[i]!=solucaoFixa[i].numFacilidades)
			{
				System.out.println("contNumberFacilite[i]: "+contNumberFacilite[i]
				+" solucaoFixa[i].numFacilidades: "+solucaoFixa[i].numFacilidades);

				return false;
			}
			
			if(solucaoFixa[i].nome!=i)
			{
				System.out.println("solucaoFixa[i].nome: "+solucaoFixa[i].nome
				+" i: "+i);

				return false;
			}
			
			if(solucao[i].posicao!=i)
			{
				System.out.println("solucao[i].posicao: "+solucao[i].posicao
				+" i: "+i);

				return false;
			}
		}
		
		if(QntP!=numFacilidades||soma!=f)
		{
			System.out.println("QntP: "+QntP+" numFacilidades: "+numFacilidades);
			System.out.println("soma: "+soma+" f: "+f);
			return false;
		}
		return true;
	}
	
	public void addFacilidade(NoSol no)
	{
		NoSol aux,aux2;
		no.facilidade=true;
		no.beneficio=0;
		for (int i = 0; i < no.possoAtender.length; i++) 
		{
			aux=solucaoFixa[no.possoAtender[i]];
			if(aux.numFacilidades==0)
			{
				f+=aux.demanda;
				
//				update beneficio
				for (int j = 0; j < aux.possoAtender.length; j++) 
				{
					aux2=solucaoFixa[aux.possoAtender[j]];
					if(!aux2.facilidade)
						aux2.beneficio-=aux.demanda;
				}
			}
			
			aux.numFacilidades++;
		}


//		for (int i = 0; i < no.possoAtender.length; i++) 
//		{
//			aux=solucaoFixa[no.possoAtender[i]];
//			if(aux.numFacilidades==1)
//			{
//				for (int j = 0; j < aux.possoAtender.length; j++) 
//				{
//					aux2=solucaoFixa[aux.possoAtender[j]];
//					if(!aux2.facilidade)
//						aux2.beneficio-=aux.demanda;
//				}
//			}
//		}
	}
	
	public void removerFacilidade(NoSol no)
	{
		NoSol aux,aux2;
		no.facilidade=false;
		for (int i = 0; i < no.possoAtender.length; i++) 
		{
			aux=solucaoFixa[no.possoAtender[i]];
			if(aux.numFacilidades==1)
			{
				f-=aux.demanda;
				
//				update beneficio
				for (int j = 0; j < aux.possoAtender.length; j++) 
				{
					aux2=solucaoFixa[aux.possoAtender[j]];
					if(!aux2.facilidade)
						aux2.beneficio+=aux.demanda;
				}
			}
			
			aux.numFacilidades--;
		}

//		for (int i = 0; i < no.possoAtender.length; i++) 
//		{
//			aux=solucaoFixa[no.possoAtender[i]];
//			
//			if(aux.numFacilidades==0)
//			{
//				for (int j = 0; j < aux.possoAtender.length; j++) 
//				{
//					aux2=solucaoFixa[aux.possoAtender[j]];
//					if(!aux2.facilidade)
//						aux2.beneficio+=aux.demanda;
//				}
//			}
//		}
	}
	
	public void trocaCompleta(int indexSai, int indexEntra)
	{
		removerFacilidade(solucao[indexSai]);
		addFacilidade(solucao[indexEntra]);
		trocaPosicao(indexSai, indexEntra);
	}
	
	public void trocaCompleta(NoSol sai, NoSol entra)
	{
		removerFacilidade(sai);
		addFacilidade(entra);
		trocaPosicao(sai.posicao, entra.posicao);
	}
	
	public void trocaPosicao(int i, int j)
	{
		NoSol aux=solucao[i];
		solucao[i]=solucao[j];
		solucao[j]=aux;
		solucao[i].posicao=i;
		solucao[j].posicao=j;
	}
	
	public int carregaSolution1(String nome)
	{
		BufferedReader in;
		int fInformado = 0;
		NoSol aux;
		
		for (int i = 0; i < size; i++) 
		{
			solucao[i].facilidade=false;
			solucao[i].numFacilidades=0;
			solucao[i].setBeneficioGA(solucaoFixa);
			solucao[i].posicao=i;
		}
		
		try 
		{
			in = new BufferedReader(new FileReader(nome));
			String str []= null;
			str=in.readLine().split(" ");
			fInformado=Integer.valueOf(str[1]);
			str=in.readLine().split(" ");
			f=0;
			int index;
			int cont=0;
			for (int i = 1; i < str.length; i++) 
			{
				index=Integer.valueOf(str[i]);
				aux=solucaoFixa[index];
				if(aux.facilidade)
					System.out.println("Erro ao carregar. Facilidade repetida.");
				else
				{
					addFacilidade(aux);
					trocaPosicao(cont,index);
					cont++;
				}
			}
		} 
		catch (IOException e) {
	    	System.out.println("Erro ao Ler Arquivo");
	    }
//		Arrays.sort(solucao,crescente);

		return fInformado;
	}
	
	public String mostraSolucao()
	{
		String str= "f: "+f+"\nyIni = [";
		for (int i = 0; i < solucaoFixa.length; i++) 
		{
			if(i!=solucaoFixa.length-1)
			{
				if(solucaoFixa[i].facilidade)
					str+="1,";
				else
					str+="0,";
			}
			else
			{
				if(solucaoFixa[i].facilidade)
					str+="1];";
				else
					str+="0];";
			}
		}
		return str;
	}
	
	public void escrecerSolucao(String end)
	{
		Arquivo arq=new Arquivo(end);
		arq.escrever(mostraFacilidades());
		arq.finalizar();
	}
	
	public String mostraFacilidades()
	{
		String str= "f: "+f+"\nFacilidade: ";
		for (int i = 0; i < solucaoFixa.length; i++) 
		{
			if(solucaoFixa[i].facilidade)
				str+=i+" ";
		}
		return str;
	}
	
	public String toString()
	{
		String str= "Solucao "
		+ "\nf=" + f 
		+ "\nsize=" + size 
		
		+ "\nsolucao=\n";
		for (int i = 0; i < size; i++) 
		{
			str+=solucao[i]+"\n";
		}
		return str+", p=" + numFacilidades + ", s=" + raio + "]";
	}
	
	@Override
	public int compareTo(Solucao x)
	{
		return x.f-this.f;
	}

	public int getF() {
		return f;
	}
	
	public void setF(int f) {
		this.f = f;
	}

	public NoSol[] getSolucao() {
		return solucao;
	}

	public static void main(String[] args) 
	{
		Instancias instancias=new Instancias();
		
		int pos=9;	

		Config config =new Config();
		Instancia instancia=new Instancia(
		"Instancias//"+instancias.instancias[pos].nome+".txt",config,
		instancias.instancias[pos].getNumFacilidades(),instancias.instancias[pos].getRaio());
		
		Solucao solucao=new Solucao(instancia,config);
		solucao.carregaSolution1("OtimosFinais//"+instancias.instancias[pos].nome+"_"
		+instancias.instancias[pos].getNumFacilidades()+"_"+instancias.instancias[pos].getRaio()+".txt");
		System.out.println(solucao);
		System.out.println("valida solucao: "+solucao.validarSolucao());
	}

	
//	public Solucao(Instancia g, Demandas d,int p, int s,boolean guia[])
//	{
//		this.size=g.getSize();
//		double distancia[][]=g.getDistancia();
//		int demanda[]=d.getDemandas();
//		this.numFacilidades=p;
//		this.raio=s;
//		this.facilidades=new int [p];
//		solucao=new NoSol [size];
//		solucaoFixa=new NoSol [size];
//		for (int i = 0; i < size; i++) 
//		{
//			solucao[i]=new NoSol(demanda[i],i,distancia,s,size);
//			solucaoFixa[i]=solucao[i];
//		}
//		
//		for (int i = 0; i < size; i++) 
//			solucao[i].SetPossoAtender(solucaoFixa, distancia, s,size);
//		
//		for (int i = 0; i < size; i++) 
//		{
//			if(guia[i])
//				addFacilidade(solucaoFixa[i]);
//		}
//	}
//	
//	public void Construir4(Neuronio n,double alfa,double phi,double omega)
//	{
//		int prox,sizeLC;
//		f=0;
//		for (int i = 0; i < size; i++) 
//		{
//			solucaoFixa[i].facilidade=false;
//			solucaoFixa[i].beneficioGNG=(int)(1000*n.getPesos()[i]);
//			solucaoFixa[i].beneficioGA=0;
//			solucaoFixa[i].numFacilidades=0;
//		}
//		
//		Arrays.sort(solucao,CBGNG);
//		int limite=(int)(numFacilidades*omega);
//		sizeLC=(int)phi;
//		for (int i = 0; i < limite; i++)
//		{
//			if((size-i)<sizeLC)
//				sizeLC=(size-i);
//			if(sizeLC==0)
//				prox=i;
//			else
//				prox=i+rand.nextInt(sizeLC);
//			
//			trocaPosicao(i,prox);
//			addFacilidade(solucao[i]);
//		}
//
//		Construir(limite,alfa);
//	}
//	
//	public void Construir5(Neuronio n,double phi,double omega)
//	{
//		int prox,sizeLC;
//		f=0;
//		for (int i = 0; i < size; i++) 
//		{
//			solucaoFixa[i].facilidade=false;
//			solucaoFixa[i].beneficioGNG=(int)(1000*n.getPesos()[i]);
//			solucaoFixa[i].beneficioGA=0;
//			solucaoFixa[i].numFacilidades=0;
//		}
//		
//		Arrays.sort(solucao,CBGNG);
//		int limite=(int)(numFacilidades*omega);
//		sizeLC=(int)phi;
//		for (int i = 0; i < limite; i++)
//		{
//			if((size-i)<sizeLC)
//				sizeLC=(size-i);
//			if(sizeLC==0)
//				prox=i;
//			else
//				prox=i+rand.nextInt(sizeLC);
//			
//			trocaPosicao(i,prox);
//			addFacilidade(solucao[i]);
//		}
//		for (int i = limite; i < numFacilidades; i++)
//		{
//			prox=i+rand.nextInt(size-i);
//			trocaPosicao(i,prox);
//			addFacilidade(solucao[i]);
//		}
//	}
//	
//	public void Construir(int apartir,double alfa)
//	{
//		int prox,sizeLC;
//		setBeneficioGA(apartir);
//		for (int i = apartir; i < numFacilidades; i++) 
//		{
//			Arrays.sort(solucao,i,size,CBGA);
//			
//			sizeLC=(int)(alfa*(size-i));
//			if(sizeLC==0)
//				prox=i;
//			else
//				prox=i+rand.nextInt(sizeLC);
//			
//			trocaPosicao(i,prox);
//			addFacilidade(solucao[i]);
//
//			for (int t = 0; t < solucao[i].possoAtenderR2.length; t++) 
//				setBeneficioGA(solucao[i].possoAtenderR2[t]);
//		}
//	}
//	
//	public void Construir(double alfa)
//	{
//		f=0;
//		int prox;
//		for (int i = 0; i < size; i++) 
//		{
//			solucao[i].facilidade=false;
//			solucao[i].numFacilidades=0;
//			solucao[i].beneficioGA=0;
//		}
//
//		prox=rand.nextInt(size);
//		trocaPosicao(0,prox);
//		addFacilidade(solucao[0]);
//
//		Construir(1,alfa);
//	}
//	
//	public void setBeneficioGA(int index)
//	{
//		int beneficio;
//		for (int i = index; i < size; i++) 
//		{
//			beneficio=0;
//			no=solucao[i];
//			for (int j = 0; j < no.possoAtender.length; j++) 
//			{
//				aux=no.possoAtender[j];
//				if(aux.numFacilidades==0)
//					beneficio+=aux.demanda;
//			}
//			no.beneficioGA=beneficio;
//		}
//	}
//
//	private void setBeneficioGA(NoSol no)
//	{
//		int beneficio=0;
//		for (int j = 0; j < no.possoAtender.length; j++) 
//		{
//			aux=no.possoAtender[j];
//			if(aux.numFacilidades==0)
//				beneficio+=aux.demanda;
//		}
//		no.beneficioGA=beneficio;
//	}
//	
//	public int beneficio(NoSol no1,NoSol no2)
//	{
//		int beneficio=0;
//		for (int j = 0; j < no1.possoAtender.length; j++) 
//		{
//			aux=no1.possoAtender[j];
//			if(aux.numFacilidades==0)
//			{
//				beneficio+=aux.demanda;
//				aux.numFacilidades=-1;
//			}
//		}
//		for (int j = 0; j < no2.possoAtender.length; j++) 
//		{
//			aux=no2.possoAtender[j];
//			if(aux.numFacilidades==0)
//				beneficio+=aux.demanda;
//		}
//		//corrigindo o 1� laco
//		for (int j = 0; j < no1.possoAtender.length; j++) 
//		{
//			aux=no1.possoAtender[j];
//			if(aux.numFacilidades==-1)
//				aux.numFacilidades=0;
//		}
//		return beneficio;
//	}
//	
//	
//	
//	
//	public void trocaCompleta(NoSol index, NoSol jindex) 
//	{
//		if(!index.facilidade&&jindex.facilidade)
//		{
//			removerFacilidade(jindex);
//			addFacilidade(index);
//		}
//		else if(index.facilidade&&!jindex.facilidade)
//		{
//			removerFacilidade(index);
//			addFacilidade(jindex);
//		}
//	}
//	
	
//	
//	public void removerFacilidade(NoSol no)
//	{
//		no.facilidade=false;
//		for (int i = 0; i < no.possoAtender.length; i++) 
//		{
//			if(no.possoAtender[i].numFacilidades==1)
//			{
//				f-=no.possoAtender[i].demanda;
//			}
//			no.possoAtender[i].numFacilidades--;
//		}
//	}
//	
//	public int buscaLocal()
//	{
//		int k=0;
//		int antF,indexj=0;
//		boolean melhorou=true;
//		
//		setBeneficioGA(numFacilidades);
//		while(melhorou) 
//		{
//			melhorou=false;
//			for (int i = numFacilidades-1; i >=0; i--)
//			{
//				antF=f;
//				removerFacilidade(solucao[i]);
//				
//				for (int t = 0; t < solucao[i].possoAtenderR2.length; t++) 
//					setBeneficioGA(solucao[i].possoAtenderR2[t]);
//				
//				indexj=getMelhorBeneficioGA(numFacilidades);
//				
//				if(indexj>=numFacilidades&&solucao[indexj].beneficioGA+f>antF)
//				{
//					melhorou=true;
//					k++;
//					addFacilidade(solucao[indexj]);
//
//					for (int t = 0; t < solucao[indexj].possoAtenderR2.length; t++) 
//						setBeneficioGA(solucao[indexj].possoAtenderR2[t]);
//					
//					trocaPosicao(i, indexj);
//					
//				}
//				else
//				{
//					addFacilidade(solucao[i]);
//					for (int t = 0; t < solucao[i].possoAtenderR2.length; t++) 
//						setBeneficioGA(solucao[i].possoAtenderR2[t]);
//				}
//			}
//		}
//		return k;
//	}
//	
//	public int buscaLocalBest()
//	{
//		int k=0;
//		int indexj=0;
//		int bestI=0,bestJ=0;
//		boolean melhorou=true;
//		int melhorF=0;
//		setBeneficioGA(numFacilidades);
//		
//		while(melhorou) 
//		{
//			melhorou=false;
//			melhorF=f;
//			for (int i = numFacilidades-1; i >=0; i--)
//			{
//				removerFacilidade(solucao[i]);
//				
//				for (int t = 0; t < solucao[i].possoAtenderR2.length; t++) 
//					setBeneficioGA(solucao[i].possoAtenderR2[t]);
//				
//				indexj=getMelhorBeneficioGA(numFacilidades);
//				
//				if(indexj>=numFacilidades&&solucao[indexj].beneficioGA+f>melhorF)
//				{
//					melhorou=true;
//					k++;
//					bestI=i;
//					bestJ=indexj;
//					melhorF=solucao[indexj].beneficioGA+f;
//				}
//				addFacilidade(solucao[i]);
//				for (int t = 0; t < solucao[i].possoAtenderR2.length; t++) 
//					setBeneficioGA(solucao[i].possoAtenderR2[t]);
//			}
//			if(melhorF>f)
//			{
//				k++;
//				melhorou=true;
//				trocaCompleta(solucao[bestI],solucao[bestJ]);
//				trocaPosicao(bestI, bestJ);	
//				
//				for (int t = 0; t < solucao[bestI].possoAtenderR2.length; t++) 
//					setBeneficioGA(solucao[bestI].possoAtenderR2[t]);
//					
//				for (int t = 0; t < solucao[bestJ].possoAtenderR2.length; t++) 
//						setBeneficioGA(solucao[bestJ].possoAtenderR2[t]);
//			}
//		}
//		return k;
//	}
//	
//	private int getMelhorBeneficioGA(int apartir) 
//	{
//		int index=0;
//		int melhorBeneficio=0;
//		for (int i = apartir; i < size; i++) 
//		{
//			if(melhorBeneficio<solucao[i].beneficioGA)
//			{
//				index=i;
//				melhorBeneficio=solucao[i].beneficioGA;
//			}
//		}
//		return index;
//	}
//
//	public int beneficioGA(NoSol no)
//	{
//		int beneficio=0;
//		for (int j = 0; j < no.possoAtender.length; j++) 
//		{
//			if(no.possoAtender[j].numFacilidades==0)
//			{
//				beneficio+=no.possoAtender[j].demanda;
//			}
//		}
//		return beneficio;
//	}
//	
//	public int buscaLocal2DL(Neuronio n,double omega)
//	{
//		NoSol noI1,noI2,noJ1=null,noJ2=null;
//		int k=0;
//		int antF,melhorBeneficio=0,beneficio;
//		boolean melhorou=true,achouJ=false;
//		for (int i = 0; i < size; i++) 
//			solucaoFixa[i].beneficioGA=(int)(1000*n.getPesos()[i]);
//		
//		Arrays.sort(solucao,CBGA);
////		for (int i = 0; i < size; i++) 
////		{
////			System.out.println(solucao[i].facilidade+" "+solucao[i].beneficio);
////		}
//		int inicio=(int)(omega*numFacilidades);
////		System.out.println(inicio);
//		while(melhorou) 
//		{
//			k++;
//			melhorou=false;
//			for (int i1 = inicio; i1 < numFacilidades-1; i1++)
//			{
//				for (int i2 = i1+1; i2 < numFacilidades; i2++)
//				{
//					antF=f;
////					System.out.println("i1: "+i1+" i2: "+i2);
//					removerFacilidade(solucao[i1]);
//					removerFacilidade(solucao[i2]);
////					System.out.println("beneficio i1+i2: "+beneficio(i1,i2));
////					System.out.println("antF: "+antF+" f: "+f);
//					achouJ=false;
//					melhorBeneficio=0;
//					noI1=solucao[i1];
//					noI2=solucao[i2];
//					for (int j1 = 0; j1 <  noI1.possoAtender.length; j1++)
//					{
//						for (int j2 = 0; j2 <  noI2.possoAtender.length; j2++)
//						{
//							
//							beneficio=beneficio(noI1.possoAtender[j1],noI2.possoAtender[j2]);
////							System.out.println("beneficio: "+beneficio);
//							if(beneficio+f>antF&&beneficio>melhorBeneficio)
//							{
//								achouJ=true;
//								melhorou=true;
//								noJ1=noI1.possoAtender[j1];
//								noJ2=noI2.possoAtender[j2];
//								melhorBeneficio=beneficio;
////								System.out.println("j1: "+j1+" j2: "+j2);
////								System.out.println("melhorBeneficio: "+melhorBeneficio);
//							}
//						}
//					}
//					if(achouJ)
//					{
//						addFacilidade(noJ1);
//						addFacilidade(noJ2);
//						Arrays.sort(solucao,CBGA);
//					}
//					else
//					{
//						addFacilidade(noI1);
//						addFacilidade(noI2);
//					}
//				}
//			}
//		}
//		return k;
//	}
//	
//	public void ordenarSolucao() 
//	{
//		Arrays.sort(solucao,CBGA);
//	}
//	
//	public int buscaLocal2GA(Neuronio n,double omega)
//	{
//		int indexJ1=0,indexJ2=0;
//		int k=0;
//		int antF,melhorBeneficio=0,beneficio;
//		boolean melhorou=true,achouJ=false;
//		int janela = (int)(omega*numFacilidades);
//		for (int i = 0; i < size; i++) 
//			solucaoFixa[i].beneficioGNG=(int)(1000*n.getPesos()[i]);
//		
//		Arrays.sort(solucao,CBGNG);
////		for (int i = 0; i < size; i++) 
////		{
////			System.out.println(solucao[i].facilidade+" "+solucao[i].beneficioGNG);
////		}
//		int inicio=(int)(omega*numFacilidades);
////		System.out.println(inicio);
////		System.out.println("Inicio f: "+f);
//		
//		setBeneficioGA(0);
//		while(melhorou) 
//		{
//			melhorou=false;
//
//			for (int i1 = inicio; i1 < numFacilidades-1; i1++)
//			{
//				for (int i2 = i1+1; i2 < numFacilidades; i2++)
//				{
//					antF=f;
////					System.out.println("i1: "+i1+" i2: "+i2);
//					removerFacilidade(solucao[i1]);
//					removerFacilidade(solucao[i2]);
////					System.out.println("beneficio i1+i2: "+beneficio(i1,i2));
////					System.out.println("antF: "+antF+" f: "+f);
//					achouJ=false;
//					melhorBeneficio=0;
//					
////					setBeneficioGA(0);
//					for (int i = 0; i < solucao[i1].possoAtenderR2.length; i++) 
//						setBeneficioGA(solucao[i1].possoAtenderR2[i]);
//					
//					for (int i = 0; i < solucao[i2].possoAtenderR2.length; i++) 
//						setBeneficioGA(solucao[i2].possoAtenderR2[i]);
//					
//					Arrays.sort(solucao,numFacilidades,size,CBGA);
//
////					for (int i = 0; i < 200; i++) 
////					{
////						System.out.println("i: "+i+" "+solucaoEntrada[i]);
////					}
//					
//					for (int j1 = numFacilidades; j1 <  numFacilidades+janela; j1++)
//					{
//						for (int j2 = j1+1; j2 <  numFacilidades+janela; j2++)
//						{
//							
//							beneficio=beneficio(solucao[j1],solucao[j2]);
////							System.out.println("beneficio: "+beneficio);
//							if(beneficio+f>antF&&beneficio>melhorBeneficio)
//							{
//								k++;
//								achouJ=true;
//								melhorou=true;
//								melhorBeneficio=beneficio;
//								indexJ1=j1;
//								indexJ2=j2;
////								System.out.println("j1: "+j1+" j2: "+j2);
////								System.out.println("melhorBeneficio: "+melhorBeneficio);
//							}
//						}
//					}
//					if(achouJ)
//					{
//						addFacilidade(solucao[indexJ1]);
//						addFacilidade(solucao[indexJ2]);
//						
//						for (int i = 0; i < solucao[indexJ1].possoAtenderR2.length; i++) 
//							setBeneficioGA(solucao[indexJ1].possoAtenderR2[i]);
//						
//						for (int i = 0; i < solucao[indexJ2].possoAtenderR2.length; i++) 
//							setBeneficioGA(solucao[indexJ2].possoAtenderR2[i]);
//						
//						trocaPosicao(i1, indexJ1);
//						trocaPosicao(i2, indexJ2);
//						
//						Arrays.sort(solucao,0,numFacilidades,CBGNG);
//
////						System.out.println("Novo f: "+f);
//					}
//					else
//					{
//						addFacilidade(solucao[i1]);
//						addFacilidade(solucao[i2]);
//						
//						for (int i = 0; i < solucao[i1].possoAtenderR2.length; i++) 
//							setBeneficioGA(solucao[i1].possoAtenderR2[i]);
//						
//						for (int i = 0; i < solucao[i2].possoAtenderR2.length; i++) 
//							setBeneficioGA(solucao[i2].possoAtenderR2[i]);
//					}
//				}
//			}
//		}
//		return k;
//	}
//	
//	public void pertubaInteligent(Neuronio n,double omega,int k)
//	{
//		int inicio=(int)(omega*numFacilidades);
//		int indexSai,indexEntra;
//		for (int i = 0; i < size; i++) 
//			solucaoFixa[i].beneficioGNG=(int)(1000*n.getPesos()[i]);
//		
//		Arrays.sort(solucao,CBGNG);
//		for (int i = 0; i < numFacilidades; i++) 
//		{
//			System.out.println(solucao[i]);
//		}
//		for (int i = 0; i < k; i++)
//		{
//			indexSai=inicio+rand.nextInt(numFacilidades-inicio);
//			removerFacilidade(solucao[indexSai]);
//			
//			indexEntra=numFacilidades+rand.nextInt(solucao.length-numFacilidades);
//			addFacilidade(solucao[indexEntra]);
//			
//			trocaPosicao(indexSai, indexEntra);
////			System.out.println("indexSai: "+indexSai+" indexEntra: "+indexEntra);
//		}
//	}
//	
//	public void pertubaInterseccao(int k,double janela)
//	{
//		int indexSai,indexEntra;
//		for (int i = 0; i < numFacilidades; i++) 
//			solucao[i].sizeIntersec=calcIntersec(solucao[i]);
//		
//		Arrays.sort(solucao,CIntersec);
//		for (int i = 0; i < k; i++)
//		{
//			indexSai=rand.nextInt((int)(numFacilidades*janela));
//			removerFacilidade(solucao[indexSai]);
//			
//			indexEntra=numFacilidades+rand.nextInt(solucao.length-numFacilidades);
//			addFacilidade(solucao[indexEntra]);
//			
//			trocaPosicao(indexSai, indexEntra);
////			System.out.println("indexSai: "+indexSai+" indexEntra: "+indexEntra);
//		}
//	}
//	
//	public int calcIntersec(NoSol no)
//	{
//		int soma=0;
//		for (int i = 0; i < no.possoAtender.length; i++) 
//		{
//			if(no.possoAtender[i].numFacilidades>1)
//				soma+=no.possoAtender[i].demanda;
//		}
//		return soma;
//	}
//	
//	public void setFacilidades()
//	{
//		int cont=0;
//		for (int i = 0; i < solucaoFixa.length; i++) 
//		{
//			if(solucaoFixa[i].facilidade)
//				facilidades[cont++]=i;
//		}
//	}
//	
	
//	
//	public void buscaPathReLink(Solucao guia, int melhorF) 
//	{
//		NoSol sai,entra;
//		int posEntra,posSai;
//		int indexVizSai=0,indexVizEntra=0,BestJndexEntra=0,BestIndexSai=0;
//		int maiorF=0;
//		int BestEntra=0,BestSai=0,vizEntra=0,vizSai=0;
//		boolean melhorou=false,troquei=true;
//
//		int maiorBeneficio,beneficio;
////		int inicialF=f;
////		int lastImproviment=1;
////		System.out.println("---------------inicio-----------------");
//		while(!melhorou&&troquei)
//		{
//			troquei=false;
//			maiorF=0;
//			for (int i = 0; i < facilidades.length; i++) 
//			{
//				posSai=facilidades[i];
//				sai=solucaoFixa[posSai];
//				indexVizSai=i;
//				if(!guia.solucaoFixa[posSai].facilidade)
//				{
//					//escolhendo quem sai
//					removerFacilidade(sai);
//
//					maiorBeneficio=0;
//					for (int j = 0; j < guia.facilidades.length; j++) 
//					{
//						posEntra=guia.facilidades[j];
//						if(!solucaoFixa[posEntra].facilidade)
//						{
//							beneficio=beneficioGA(solucaoFixa[posEntra]);
//							if(maiorBeneficio<beneficio)
//							{
//								//escolhendo quem entra
//								maiorBeneficio=beneficio;
//								indexVizEntra=j;
//								vizEntra=posEntra;
//								vizSai=posSai;
//							}
//						}
//					}
//					//definindo melhor par
//					if(maiorF<f+maiorBeneficio)
//					{
//						troquei=true;
//						maiorF=f+maiorBeneficio;
//						BestEntra=vizEntra;
//						BestSai=vizSai;
//						BestIndexSai=indexVizSai;
//						BestJndexEntra=indexVizEntra;
//						
//					}
//					addFacilidade(sai);
//				}
//			}
//			//simplesmente caminha
//			if(troquei)
//			{
//				sai=solucaoFixa[BestSai];
//				entra=solucaoFixa[BestEntra];
//				removerFacilidade(sai);
//				addFacilidade(entra);
//				facilidades[BestIndexSai]=guia.facilidades[BestJndexEntra];
//				
////				if(f>guia.f&&f>inicialF&&f>lastImproviment)
////				{
////					lastImproviment=f;
//////					System.out.println("f: "+f+" inicialF: "+inicialF+" guia.f: "+guia.f+" qtdeApGNG: "+algoritmo.qtdeApGNG);
////					if(algoritmo.melhorF<f)
////					{
////						algoritmo.freqMelhora[pos]++;
////						algoritmo.totalMelhora++;
////						algoritmo.gng.ApresentaPadrao(this);
////						algoritmo.qtdeApGNG++;
////						algoritmo.analisaPR(this,"PathRelink");
////					}
////				}
//			}
//			if(f>melhorF)
//				melhorou=true;
//		}
//	}
//	
//	public int F()
//	{
//		int novoF=0;
//		for (int i = 0; i < size; i++) 
//			solucao[i].numFacilidades=0;
//		for (int i = 0; i < size; i++) 
//		{
//			if(solucao[i].facilidade)
//			{
//				no=solucao[i];
//				for (int j = 0; j < no.possoAtender.length; j++) 
//				{
//					aux=no.possoAtender[j];
//					if(aux.numFacilidades==0)
//					{
//						novoF+=aux.demanda;
//						aux.numFacilidades++;
//					}
//				}
//			}
//		}
//		return novoF;
//	}
//	
//	public void MostraValidacao()
//	{
//		int QntP=0;
//		for (int i = 0; i < size; i++) 
//		{
//			if(solucao[i].facilidade)
//				QntP++;
//		}
//		System.out.print("QntP: "+QntP);
//		int soma=0;
//		for (int i = 0; i < size; i++) 
//		{
//			if(solucao[i].numFacilidades>=1)
//				soma+=solucao[i].demanda;
//		}
//		System.out.print(" F: "+soma+" Freal: "+f+"\n");
//	}
//	
//	//gerar vizinho para o ILS
//	public void gerarViz(int k) 
//	{
//		int a,b;
//		Arrays.sort(solucao,CBGA);
//		for (int l = 0; l < k; l++) 
//		{
//			a=rand.nextInt(numFacilidades);
//			b=numFacilidades+rand.nextInt(size-numFacilidades);
//			trocaCompleta(solucao[a],solucao[b]);
//			trocaPosicao(a, b);							
//		}
//	}
//	

}

