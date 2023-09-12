package BuscaLocal;

import java.util.Arrays;
import java.util.Random;

import Comparador.VariacaoCrescente;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst17 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	
	int posA,posB;
	int melhorBeneficio;
	VariacaoCrescente variacaoCrescente=new VariacaoCrescente();
	Random rand=new Random();
	private NoSol facildiades[];
	
	public BuscaLocalFirst17(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.facildiades=new NoSol[numFacilidades];
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
		for (int i = 0; i < numFacilidades; i++)
			facildiades[i]=solucao[i];
	}

//	p (n-p) or pn
	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int indexJ=0;
		boolean melhorou=true;
		iterador=0;
		NoSol entra;
		
		while(melhorou) 
		{
			melhorou=false;
			
			Arrays.sort(facildiades,0,numFacilidades, variacaoCrescente);
			
//			p (n-p)
			for (int indexI = 0; indexI < numFacilidades; indexI++)
			{
//				R
				facildiades[indexI].calcEsclusividade(solucaoFixa);
				
//				n-p
				indexJ=getMelhorBeneficioGA(facildiades[indexI]);
				if(melhorBeneficio>facildiades[indexI].esclusividade)
				{
					melhorou=true;
//					R
					entra=solucao[indexJ];
					s.trocaCompleta(facildiades[indexI], solucao[indexJ]);
					facildiades[indexI]=entra;
				}
			}
		}
	}
	
	private int getMelhorBeneficioGA(NoSol noRemove) 
	{
		int index=0;
		melhorBeneficio=0;
		int beneficio=0;
		NoSol aux,aux2;
		
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
