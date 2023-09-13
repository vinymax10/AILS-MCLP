package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import Data.Arquivo;
import Data.Instance;
import SearchMethod.Config;

public class Solucao implements Comparable<Solucao>
{
	public int size;
	public NoSol solucao[];
	public NoSol solucaoFixa[];
	public int numFacilidades, raio;
	Random rand = new Random();
	public int f;
	int contNumberFacilite[];
	public int distancia;

	public Solucao(Instance instancia, Config config)
	{
		this.size = instancia.getSize();
		this.numFacilidades = instancia.getNumFacilidades();
		this.raio = instancia.getRaio();
		this.contNumberFacilite = new int[size];

		solucao = new NoSol[size];
		solucaoFixa = new NoSol[size];

		for(int i = 0; i < size; i++)
		{
			solucao[i] = new NoSol(instancia.getPontos()[i]);
			solucaoFixa[i] = solucao[i];
		}
	}

	public void clone(Solucao x)
	{
		this.f = x.f;
		for(int i = 0; i < solucaoFixa.length; i++)
		{
			this.solucaoFixa[i].facilidade = x.solucaoFixa[i].facilidade;
			this.solucaoFixa[i].numFacilidades = x.solucaoFixa[i].numFacilidades;
			this.solucaoFixa[i].escolhido = false;
			this.solucaoFixa[i].beneficio = x.solucaoFixa[i].beneficio;
			this.solucaoFixa[i].acrescimo = 0;

			this.solucao[i] = this.solucaoFixa[x.solucao[i].nome];
			this.solucao[i].posicao = i;
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
		int a, b;
		for(int i = 0; i < numFacilidades; i++)
		{
			a = rand.nextInt(numFacilidades);
			b = rand.nextInt(numFacilidades);

			aux = solucao[a];
			solucao[a] = solucao[b];
			solucao[b] = aux;

			solucao[a].posicao = a;
			solucao[b].posicao = b;
		}
	}

	public boolean validarSolucao()
	{
		int QntP = 0;
		int soma = 0;

		for(int i = 0; i < size; i++)
			contNumberFacilite[i] = 0;

		for(int i = 0; i < size; i++)
		{
			if(solucao[i].facilidade)
			{
				for(int j = 0; j < solucao[i].possoAtender.length; j++)
					contNumberFacilite[solucao[i].possoAtender[j]]++;

				QntP++;
			}

			if(solucao[i].numFacilidades >= 1)
				soma += solucao[i].demanda;
		}

		for(int i = 0; i < size; i++)
		{
			if(contNumberFacilite[i] != solucaoFixa[i].numFacilidades)
			{
				System.out.println("contNumberFacilite[i]: " + contNumberFacilite[i] + " solucaoFixa[i].numFacilidades: " + solucaoFixa[i].numFacilidades);

				return false;
			}

			if(solucaoFixa[i].nome != i)
			{
				System.out.println("solucaoFixa[i].nome: " + solucaoFixa[i].nome + " i: " + i);

				return false;
			}

			if(solucao[i].posicao != i)
			{
				System.out.println("solucao[i].posicao: " + solucao[i].posicao + " i: " + i);

				return false;
			}
		}

		if(QntP != numFacilidades || soma != f)
		{
			System.out.println("QntP: " + QntP + " numFacilidades: " + numFacilidades);
			System.out.println("soma: " + soma + " f: " + f);
			return false;
		}
		return true;
	}

	public void addFacilidade(NoSol no)
	{
		NoSol aux, aux2;
		no.facilidade = true;
		no.beneficio = 0;
		for(int i = 0; i < no.possoAtender.length; i++)
		{
			aux = solucaoFixa[no.possoAtender[i]];
			if(aux.numFacilidades == 0)
			{
				f += aux.demanda;

//				update beneficio
				for(int j = 0; j < aux.possoAtender.length; j++)
				{
					aux2 = solucaoFixa[aux.possoAtender[j]];
					if(!aux2.facilidade)
						aux2.beneficio -= aux.demanda;
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
		NoSol aux, aux2;
		no.facilidade = false;
		for(int i = 0; i < no.possoAtender.length; i++)
		{
			aux = solucaoFixa[no.possoAtender[i]];
			if(aux.numFacilidades == 1)
			{
				f -= aux.demanda;

//				update beneficio
				for(int j = 0; j < aux.possoAtender.length; j++)
				{
					aux2 = solucaoFixa[aux.possoAtender[j]];
					if(!aux2.facilidade)
						aux2.beneficio += aux.demanda;
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
		NoSol aux = solucao[i];
		solucao[i] = solucao[j];
		solucao[j] = aux;
		solucao[i].posicao = i;
		solucao[j].posicao = j;
	}

	public int carregaSolution1(String nome)
	{
		BufferedReader in;
		int fInformado = 0;
		NoSol aux;

		for(int i = 0; i < size; i++)
		{
			solucao[i].facilidade = false;
			solucao[i].numFacilidades = 0;
			solucao[i].setBeneficioGA(solucaoFixa);
			solucao[i].posicao = i;
		}

		try
		{
			in = new BufferedReader(new FileReader(nome));
			String str[] = null;
			str = in.readLine().split(" ");
			fInformado = Integer.valueOf(str[1]);
			str = in.readLine().split(" ");
			f = 0;
			int index;
			int cont = 0;
			for(int i = 1; i < str.length; i++)
			{
				index = Integer.valueOf(str[i]);
				aux = solucaoFixa[index];
				if(aux.facilidade)
					System.out.println("Erro ao carregar. Facilidade repetida.");
				else
				{
					addFacilidade(aux);
					trocaPosicao(cont, index);
					cont++;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Erro ao Ler Arquivo");
		}
//		Arrays.sort(solucao,crescente);

		return fInformado;
	}

	public String mostraSolucao()
	{
		String str = "f: " + f + "\nyIni = [";
		for(int i = 0; i < solucaoFixa.length; i++)
		{
			if(i != solucaoFixa.length - 1)
			{
				if(solucaoFixa[i].facilidade)
					str += "1,";
				else
					str += "0,";
			}
			else
			{
				if(solucaoFixa[i].facilidade)
					str += "1];";
				else
					str += "0];";
			}
		}
		return str;
	}

	public void escrecerSolucao(String end)
	{
		Arquivo arq = new Arquivo(end);
		arq.escrever(mostraFacilidades());
		arq.finalizar();
	}

	public String mostraFacilidades()
	{
		String str = "f: " + f + "\nFacilidade: ";
		for(int i = 0; i < solucaoFixa.length; i++)
		{
			if(solucaoFixa[i].facilidade)
				str += i + " ";
		}
		return str;
	}

	public String toString()
	{
		String str = "Solucao " + "\nf=" + f + "\nsize=" + size

		+ "\nsolucao=\n";
		for(int i = 0; i < size; i++)
		{
			str += solucao[i] + "\n";
		}
		return str + ", p=" + numFacilidades + ", s=" + raio + "]";
	}

	@Override
	public int compareTo(Solucao x)
	{
		return x.f - this.f;
	}

	public int getF()
	{
		return f;
	}

	public void setF(int f)
	{
		this.f = f;
	}

	public NoSol[] getSolucao()
	{
		return solucao;
	}

}
