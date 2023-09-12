package BuscaLocal;

import java.util.Arrays;

import Dados.Instancia;
import Dados.TipoAdj;
import MetaHeuristicas.Config;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public class BuscaLocalBest3 implements BuscaLocal
{
	private NoSol solucao[];
	public NoSol solucaoFixa[];
	
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
	NoSol possiveisEntrar[];
	int topPossiveisEntrar;
	
	public BuscaLocalBest3(Instancia instancia,Config config)
	{
		this.instancia=instancia;
		this.config=config;
		this.size=instancia.getSize();
		this.numFacilidades=instancia.getNumFacilidades();
		this.melhoras=new NoMelhora[numFacilidades];
		this.possiveisEntrar=new NoSol[numFacilidades];
		
		for (int i = 0; i < melhoras.length; i++) 
			melhoras[i]=new NoMelhora();
	}
	
	private void setSolucao(Solucao s) 
	{
		this.solucao=s.solucao;
		this.solucaoFixa=s.solucaoFixa;
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
//		int cont=0;
		topPossiveisEntrar=0;
		for (int i = 0; i < numFacilidades; i++)
		{
			antF=s.f;
			s.removerFacilidade(solucao[i]);
			if(topPossiveisEntrar==0||!verifiarPossiveisEntrar(s.f))
			{
//				cont++;
				indexJ=getMelhorBeneficioGA(numFacilidades);
				possiveisEntrar[topPossiveisEntrar++]=solucao[indexJ];
			}
			
			novoF=solucao[indexJ].beneficio+s.f;
			
			if(antF<novoF)
			{
				melhoras[topMelhoras].custo=(novoF-antF);
				melhoras[topMelhoras].noSai=solucao[i];
				melhoras[topMelhoras].noEntra=solucao[indexJ];
				topMelhoras++;
				achouMovimento=true;
			}
			s.addFacilidade(solucao[i]);
		}
//		System.out.println("cont: "+cont);
	}
	
	private boolean verifiarPossiveisEntrar(int f)
	{
		boolean temCustoPositivo=false;
		for (int i = 0; i < topPossiveisEntrar; i++)
		{
			if((possiveisEntrar[i].beneficio+f-antF)>0)
				temCustoPositivo=true;
		}
		return temCustoPositivo;
	}
	
	private int getMelhorBeneficioGA(int apartir) 
	{
		int index=0;
		int melhorBeneficio=0;
		for (int i = apartir; i < size; i++) 
		{
			if(melhorBeneficio<solucao[i].beneficio)
			{
				index=i;
				melhorBeneficio=solucao[i].beneficio;
			}
		}
		return index;
	}
	
}
