package BuscaLocal;

import java.util.Random;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst16 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	Random rand=new Random();
	int melhorBeneficio;
	
	public BuscaLocalFirst16(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
	}
	
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
	}

//	p (n-p) or pn
	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int indexJ=0;
		boolean melhorou=true;
		iterador=0;
		s.embaralharFacilidades();
		
		while(melhorou) 
		{
			melhorou=false;
			for (int i = 0; i < numFacilidades; i++)
			{
//				R
				solucao[i].calcEsclusividade(solucaoFixa);
				
//				n-p
				indexJ=getMelhorBeneficioGA(i);
				if(melhorBeneficio>solucao[i].esclusividade)
				{
					melhorou=true;
//					R
					s.trocaCompleta(i, indexJ);
				}
			}
		}
	}
	
	private int getMelhorBeneficioGA(int indexI) 
	{
		int index=0;
		melhorBeneficio=0;
		int beneficio=0;
		NoSol aux,aux2,noRemove=solucao[indexI];
		
//		2R
		for (int i = 0; i < noRemove.possoAtender.length; i++) 
		{
			aux=solucaoFixa[noRemove.possoAtender[i]];
			if(aux.numFacilidades==1)
			{
				for (int j = 0; j < aux.possoAtender.length; j++) 
				{
					aux2=solucaoFixa[aux.possoAtender[j]];
					if(!aux2.facilidade)
						aux2.acrescimo+=aux.demanda;
				}
			}
		}
		
//		n-p
		for (int j = numFacilidades; j < size; j++) 
		{	
			beneficio=solucao[j].beneficio+solucao[j].acrescimo;
			if((melhorBeneficio<beneficio)||(melhorBeneficio==beneficio&&rand.nextBoolean()))
			{
				index=j;
				melhorBeneficio=beneficio;
			}
		}
		
//		2R
		for (int i = 0; i < noRemove.possoAtenderR2.length; i++) 
		{
			solucaoFixa[noRemove.possoAtenderR2[i]].acrescimo=0;
		}
		
		return index;
	}
}
