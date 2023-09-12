package Solution;

import java.util.Arrays;

import Data.Ponto;

public class NoSol  
{
	public boolean facilidade;
	public int demanda;
	public int beneficio;
	public int intersec;
	public int esclusividade;
	public int acrescimo;
	
	public double variacao;
	public int beneficioAnt;
	public boolean escolhido;
	public int nome;
	public int possoAtender[];
	public int possoAtenderR2[];
	public NoSol entra,sai;
	public boolean podeEscolher;
	public float distancia;
	public int numFacilidades;
	public int posicao;

	public NoSol(Ponto ponto)
	{
		this.demanda = ponto.demanda;
		this.nome = ponto.nome;
		this.numFacilidades=0;
		this.possoAtender=ponto.possoAtender;
		this.possoAtenderR2=ponto.possoAtenderR2;
	}

	public void setBeneficioGA(NoSol solucaoFixa[])
	{
		beneficioAnt=beneficio;
		beneficio=0;
		NoSol aux;
		for (int j = 0; j < possoAtender.length; j++) 
		{
			aux=solucaoFixa[possoAtender[j]];
			if(aux.numFacilidades==0)
				beneficio+=aux.demanda;
		}
	}
	
	public void verificaBeneficioGA(NoSol solucaoFixa[])
	{
		int beneficioAux=0;
		NoSol aux;
		for (int j = 0; j < possoAtender.length; j++) 
		{
			aux=solucaoFixa[possoAtender[j]];
			if(aux.numFacilidades==0)
				beneficioAux+=aux.demanda;
		}
		
		if(beneficioAux!=beneficio)
			System.out.println("Erro beneficioAux: "+beneficioAux+" beneficio: "+beneficio);
	}
	
	public void calcIntersec(NoSol solucaoFixa[])
	{
		intersec=0;
		NoSol aux;
		for (int i = 0; i < possoAtender.length; i++) 
		{
			aux=solucaoFixa[possoAtender[i]];
			if(aux.numFacilidades>1)
				intersec+=aux.demanda;
		}
	}
	
	public void calcEsclusividade(NoSol solucaoFixa[])
	{
		esclusividade=0;
		NoSol aux;
		for (int i = 0; i < possoAtender.length; i++) 
		{
			aux=solucaoFixa[possoAtender[i]];
			if(aux.numFacilidades==1)
				esclusividade+=aux.demanda;
		}
	}
	
	public void updateAdjR2(NoSol solucaoFixa[])
	{
		for (int t = 0; t < possoAtenderR2.length; t++) 
		{
			solucaoFixa[possoAtenderR2[t]].setBeneficioGA(solucaoFixa);
		}
	}
	
	public void updateAdjRemocao(NoSol solucaoFixa[])
	{
		NoSol aux,aux2;
		for (int t = 0; t < possoAtender.length; t++) 
		{
			aux=solucaoFixa[possoAtender[t]];
			if(aux.numFacilidades==0)
			{
				for (int i = 0; i < aux.possoAtender.length; i++) 
				{
					aux2=solucaoFixa[aux.possoAtender[i]];
					if(!aux2.facilidade)
						aux2.beneficio+=aux.demanda;
				}
			}
		}
	}
	
	public void updateAdjAdicao(NoSol solucaoFixa[])
	{
		NoSol aux,aux2;
		for (int t = 0; t < possoAtender.length; t++) 
		{
			aux=solucaoFixa[possoAtender[t]];
			if(aux.numFacilidades==1)
			{
				for (int i = 0; i < aux.possoAtender.length; i++) 
				{
					aux2=solucaoFixa[aux.possoAtender[i]];
					if(!aux2.facilidade)
						aux2.beneficio-=aux.demanda;
				}
			}
		}
	}
	
	
//	public void setBeneficioGAConsiderandoRemocao(NoSol solucaoFixa[], NoSol remove)
//	{
//		beneficioAnt=beneficio;
//		beneficio=0;
//		NoSol aux;
//		for (int j = 0; j < possoAtender.length; j++) 
//		{
//			aux=solucaoFixa[possoAtender[j]];
//			if(aux.numFacilidades==0||
//			(aux.numFacilidades==1&&hash[remove.nome][aux.nome]==TipoAdj.Raio))
//				beneficio+=aux.demanda;
//		}
//	}
//	
//	public void updateAdjR2ConsiderandoRemocao(NoSol solucaoFixa[])
//	{
//		for (int t = 0; t < possoAtenderR2.length; t++) 
//		{
//			solucaoFixa[possoAtenderR2[t]].setBeneficioGAConsiderandoRemocao(solucaoFixa,this);
//		}
//	}
	
	public void voltaBeneficioAnterior(NoSol solucaoFixa[])
	{
		NoSol aux;
		for (int t = 0; t < possoAtenderR2.length; t++) 
		{
			aux=solucaoFixa[possoAtenderR2[t]];
			aux.beneficio=aux.beneficioAnt;
		}
	}
	
	public boolean verificarAdj()
	{
		Arrays.sort(possoAtender);
		boolean contemOProprio=false;
		for (int i = 0; i < possoAtender.length-1; i++) 
		{
			if(possoAtender[i]==nome)
				contemOProprio=true;
			
			if(possoAtender[i]==possoAtender[i+1])
			{
				System.out.println("possoAtender[i]: "+possoAtender[i]
				+" possoAtender[i+1]: "+possoAtender[i+1]);
				return false;
			}
		}
		
		if(possoAtender[possoAtender.length-1]==nome)
			contemOProprio=true;
		
		if(!contemOProprio)
		{
			System.out.println("nao contem o proprio");
			System.out.println("nome: "+nome+" possoAtender: "+Arrays.toString(possoAtender));
		}
		
		if(possoAtender.length>possoAtenderR2.length)
		{
			System.out.println("possoAtender.length>possoAtenderR2.length");
			return false;
		}
//		System.out.println("this: "+this);
//		System.out.println("possoAtender");
//		System.out.println(Arrays.toString(possoAtender));
//		System.out.println("possoAtenderR2");
//		System.out.println(Arrays.toString(possoAtenderR2));

		
		return true;
	}
	
	public int getNumFacilidades() {
		return numFacilidades;
	}

	public void setNumFacilidades(int numFacilidades) {
		this.numFacilidades = numFacilidades;
	}

	public boolean isFacilidade() {
		return facilidade;
	}

	public int getDemanda() {
		return demanda;
	}

	public double getBeneficio() {
		return beneficio;
	}

	public int getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "NoSol [facilidade=" + facilidade + ", demanda=" + demanda
				+ ", beneficio=" + beneficio +" intersec="+intersec
				+" esclusividade="+esclusividade+", variacao=" + variacao +", nome="
				+ nome + ", numFacilidades=" + numFacilidades + "]";
	}


}
