package BuscaLocal;

import java.util.Arrays;

import Comparador.BeneficioDecrescente;
import Dados.Instancia;
import Dados.TipoAdj;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public class BuscaLocalBest4 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	NoSol vetFake[];
	BeneficioDecrescente beneficioDecrescente=new BeneficioDecrescente();
	
	protected int size;
	Instancia instancia;
	Config config;
	int beneficio,beneficioNo;
	int numFacilidades;
	int iterador=0;
	
	int index=0;
	int melhorBeneficio=0;
	NoMelhora melhoras[];
	int topMelhoras;
	NoMelhora auxiliar;
	
	int antF,indexJ,indexI,novoF;
	boolean melhorou=true;
	int melhorF=0;
	NoSol aux;
	boolean achouMovimento=false;
	
	public BuscaLocalBest4(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.melhoras=new NoMelhora[numFacilidades];
		for (int i = 0; i < melhoras.length; i++) 
			melhoras[i]=new NoMelhora();
		
		this.vetFake=new NoSol[size];
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
		for (int i = 0; i < vetFake.length; i++) {
			vetFake[i]=solucao[i];
		}
		
	}


	public void buscaLocal(Solucao s)
	{
		setSolucao(s);

		iterador=0;
		achouMovimento=false;
		
		do
		{
			achouMovimento=false;
			varreduraCompleta(s);
			iterador=0;
//			System.out.println("topMelhoras: "+topMelhoras);
			while(topMelhoras>0) 
			{
				iterador++;

				Arrays.sort(melhoras,0,topMelhoras);
//				System.out.println("-----------------");
//				for (int i = 0; i < topMelhoras; i++) 
//					System.out.println(melhoras[i]);
				
				s.trocaCompleta(melhoras[0].noSai, melhoras[0].noEntra);
				removerMelhora(0);
				for (int i = 0; i < topMelhoras; i++) 
				{
					if(!melhoras[i].noSai.facilidade||melhoras[i].noEntra.facilidade)
					{
						removerMelhora(i);
						i--;
					}
					else
					{
						melhoras[i].custo=custo(melhoras[i].noSai,melhoras[i].noEntra);
						if(melhoras[i].custo<=0)
						{
							removerMelhora(i);
							i--;
						}
					}
				}
			}
//			System.out.println("iterador: "+iterador);
		}
		while(achouMovimento);
	}
	
	private void removerMelhora(int index)
	{
		auxiliar=melhoras[index];
		melhoras[index]=melhoras[--topMelhoras];
		melhoras[topMelhoras]=auxiliar;
	}
	
	private int custo(NoSol sai,NoSol entra)
	{
		int custo=entra.beneficio;
		for (int i = 0; i < sai.possoAtender.length; i++) 
		{
			aux=solucaoFixa[sai.possoAtender[i]];
			if(aux.numFacilidades==1&&instancia.getTipoAdj(aux.nome,entra.nome)!=TipoAdj.Raio)
			{
				custo-=aux.demanda;
			}
		}
		return custo;
	}
	
	private void varreduraCompleta(Solucao s)
	{
		topMelhoras=0;
		melhorF=0;
		for (int i = 0; i < numFacilidades; i++)
		{
			antF=s.f;
			s.removerFacilidade(solucao[i]);
			
			Arrays.sort(vetFake,0,size,beneficioDecrescente);
//			indexJ=getMelhorBeneficioGA(numFacilidades);
			indexJ=numFacilidades;
			novoF=vetFake[numFacilidades].beneficio+s.f;
			
			if(antF<novoF)
			{
				melhoras[topMelhoras].custo=(novoF-antF);
				melhoras[topMelhoras].noSai=solucao[i];
				melhoras[topMelhoras].noEntra=vetFake[numFacilidades];
				topMelhoras++;
				achouMovimento=true;
			}
			s.addFacilidade(solucao[i]);
		}
	}
	
}
