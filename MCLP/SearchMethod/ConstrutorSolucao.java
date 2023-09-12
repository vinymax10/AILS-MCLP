package SearchMethod;

import java.util.Random;

import Data.Instancia;
import Solution.NoSol;
import Solution.Solucao;


public class ConstrutorSolucao 
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];

	protected Random rand=new Random();
	protected int size;
	Instancia instancia;
	int beneficio;
	int sizeJanelaGA;
	int numFacilidades;
	
	public ConstrutorSolucao(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.size=instancia.getSize();
		this.sizeJanelaGA=config.getSizeJanelaGA();
		this.numFacilidades=instancia.getNumFacilidades();
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
	}

	public void construir(Solucao s)
	{
		setSolucao(s);
		s.f=0;
		
		for (int i = 0; i < size; i++) 
		{
			solucao[i].facilidade=false;
			solucao[i].numFacilidades=0;
			solucao[i].setBeneficioGA(solucaoFixa);
			solucao[i].posicao=i;
		}

		int prox;
//		setBeneficioGA(0);
		for (int i = 0; i < numFacilidades; i++) 
		{
//			Arrays.sort(solucao,i,size,CBGA);
			
			prox=i+rand.nextInt(size-i);
			
			s.trocaPosicao(i,prox);
			s.addFacilidade(solucao[i]);

//			for (int t = 0; t < solucao[i].possoAtenderR2.length; t++) 
//				setBeneficioGA(solucao[i].possoAtenderR2[t]);
		}
		
//		for (int t = 0; t < size; t++) 
//			solucao[t].verificaBeneficioGA(solucaoFixa);
		
	}
	
	public static void main(String[] args) 
	{
		Config config=new Config();
		Instancia instancia=new Instancia("Instancias//NewYork.txt",config,50,400);
		ConstrutorSolucao const1=new ConstrutorSolucao(instancia,config);
		Solucao solucao =new Solucao(instancia,config);

		const1.construir(solucao);
		
		System.out.println(solucao);

	}
	
}
