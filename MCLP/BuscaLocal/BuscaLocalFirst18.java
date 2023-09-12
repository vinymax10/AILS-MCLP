package BuscaLocal;

import java.util.Random;

import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

//Essa busca local registra todas as possiveis melhoras ao longo de uma passada. 
//Depois atualiza cada uma e verifica se ainda pode e se o custo compensa.
//Atualiza��o dinamica Best full

public class BuscaLocalFirst18 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
	protected int size;
	Instancia instancia;
	Config config;
	int numFacilidades;
	int iterador=0;
	Random rand=new Random();
	NoSol aux;
	
	public BuscaLocalFirst18(Instancia instancia,Config config)
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

	public void buscaLocal(Solucao s)
	{
		setSolucao(s);
		int antF,novoF;
		boolean melhorou=true;
		NoSol bestNo;
		iterador=0;
		s.embaralharFacilidades();
		
		while(melhorou) 
		{
			melhorou=false;
			for (int i = 0; i < numFacilidades; i++)
			{
				antF=s.f;
				s.removerFacilidade(solucao[i]);
				
				bestNo=getMelhorBeneficioGA(solucao[i]);
				novoF=bestNo.beneficio+s.f;
				if(novoF>antF)
				{
					melhorou=true;
					s.addFacilidade(bestNo);
					
					s.trocaPosicao(i, bestNo.posicao);
				}
				else
				{
					s.addFacilidade(solucao[i]);
				}
			}
		}
	}
	
	private NoSol getMelhorBeneficioGA(NoSol no) 
	{
		NoSol bestNo=null;
		int melhorBeneficio=0;
		for (int i = 0; i < instancia.getLimiteKnn(); i++) 
		{
			aux=solucaoFixa[instancia.getKnn()[no.nome][i]];
			if(!aux.facilidade&&(melhorBeneficio<aux.beneficio)||(melhorBeneficio==aux.beneficio&&rand.nextBoolean()))
			{
				bestNo=aux;
				melhorBeneficio=aux.beneficio;
			}
		}
		
		if(bestNo==null)
			return solucao[numFacilidades+rand.nextInt(solucao.length-numFacilidades)];
		
		return bestNo;
	}
}
