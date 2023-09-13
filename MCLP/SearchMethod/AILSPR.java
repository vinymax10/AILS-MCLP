package SearchMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import Data.Instance;
import DiversityControl.AcceptanceCriteria;
import DiversityControl.AjusteOmega;
import DiversityControl.DistIdeal;
import Improvement.BuscaLocal;
import PathReLinking.PathReLinking;
import PathReLinking.SubConj;
import Perturbation.Perturbacao;
import Solution.Solucao;

public class AILSPR
{
	Solucao solucao, solucaoReferencia;
	Solucao melhorSolucao;

	Instance instancia;
	Distancia distEntreSolucoes;
	double melhorF;
	double MAX;
	double otimo;

	Config config;

	int numIterUpdate;
	int iterador;
	long inicio;
	double tempoMF;

	Random rand = new Random();

	AjusteOmega ajusteOmega;

	double distanciaBL;

	Perturbacao[] perturbadores;
	Perturbacao perturbacaoEscolhida;

	ConstrutorSolucao construtorSolucao;

	BuscaLocal buscaLocal;
	PathReLinking pathReLinking;

	AcceptanceCriteria criterioAceitacao;

	DistIdeal distIdeal;

	boolean print = true;

	SubConj subConj;

	public AILSPR(Instance instancia, InputParameters leitor)
	{
		this.instancia = instancia;

		this.solucao = new Solucao(instancia, config);
		this.solucaoReferencia = new Solucao(instancia, config);
		this.melhorSolucao = new Solucao(instancia, config);
		this.config = leitor.getConfig();
		this.otimo = leitor.getBest();
		this.MAX = leitor.getTimeLimit();
		this.melhorF = 0;

		this.distIdeal = new DistIdeal();
		this.distIdeal.distIdeal = (int) config.getDBeta();

		this.distEntreSolucoes = new Distancia();

		this.perturbadores = new Perturbacao[config.getPerturbacao().length];

		try
		{

			this.buscaLocal = new BuscaLocal(instancia, config);

			this.pathReLinking = new PathReLinking(instancia, config);

			this.construtorSolucao = new ConstrutorSolucao(instancia, config);

			this.criterioAceitacao = new AcceptanceCriteria(instancia, config, MAX);

			this.subConj = new SubConj(instancia, config, distEntreSolucoes, distIdeal);

			for(int i = 0; i < perturbadores.length; i++)
			{
				AjusteOmega ajusteOmega = new AjusteOmega(instancia, config, distIdeal, MAX);

				this.perturbadores[i] = (Perturbacao) Class.forName("Perturbation." + config.getPerturbacao()[i])
				.getConstructor(Instance.class, Config.class, AjusteOmega.class).newInstance(instancia, config, ajusteOmega);

			}

		}
		catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException
		| ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void search()
	{
		iterador = 0;
		inicio = System.currentTimeMillis();
		construtorSolucao.construir(solucaoReferencia);

		buscaLocal.buscaLocal(solucaoReferencia);
		melhorSolucao.clone(solucaoReferencia);

		subConj.add(solucaoReferencia);

		while(MAX > (System.currentTimeMillis() - inicio) / 1000)
		{
			iterador++;

			solucao.clone(solucaoReferencia);

			perturbacaoEscolhida = perturbadores[rand.nextInt(perturbadores.length)];
			perturbacaoEscolhida.perturbar(solucao);

			buscaLocal.buscaLocal(solucao);
			distanciaBL = distEntreSolucoes.distancia(solucao, solucaoReferencia);

			analisaSolucao(solucao, "AILS");

			perturbacaoEscolhida.getAjusteOmega().setDistancia(distanciaBL);

			if((distanciaBL >= distIdeal.distIdeal || (distanciaBL < distIdeal.distIdeal && solucao.f < solucaoReferencia.f)))
				subConj.add(solucao);

			if(criterioAceitacao.aceitaSolucao(solucao, distanciaBL, false))
				solucaoReferencia.clone(solucao);

			if(PR())
			{
				buscaLocal.buscaLocal(pathReLinking.getMelhorSolucao());

				subConj.add(pathReLinking.getMelhorSolucao());
				analisaSolucao(pathReLinking.getMelhorSolucao(), "PR");
			}

		}
	}

	public void analisaSolucao(Solucao solucao, String metodo)
	{
		if(solucao.f > melhorF)
		{
			melhorF = solucao.f;
			melhorSolucao.clone(solucao);

			tempoMF = (double) (System.currentTimeMillis() - inicio) / 1000;

			if(print)
			{
				System.out.println("melhorF: " + melhorF + " metodo: " + metodo + " gap: " + getGap() + " eta: " + criterioAceitacao.getEta() + " omega: "
				+ perturbacaoEscolhida.omega);
			}
		}
	}

	private boolean PR()
	{
		boolean achou = false;
		switch(rand.nextInt(3)) {
		case 0:
			achou = pathReLinking.pathReLinking(subConj.getSolucao(), solucao);
			break;
		case 1:
			achou = pathReLinking.pathReLinking(solucao, subConj.getSolucao());
			break;
		case 2:
			achou = pathReLinking.pathReLinking(subConj.getSolucao(), subConj.getSolucao());
			break;
		}

		return achou;
	}

	public static void main(String[] args)
	{
		InputParameters reader = new InputParameters();
		reader.readingInput(args);
		Instance instance = new Instance(reader);

		AILSPR ails = new AILSPR(instance, reader);

		ails.search();
	}

	public Solucao getMelhorSolucao()
	{
		return melhorSolucao;
	}

	public double getMelhorF()
	{
		return melhorF;
	}

	public double getGap()
	{
		return 100 * ((otimo - melhorF) / otimo);
	}

	public boolean isPrint()
	{
		return print;
	}

	public void setPrint(boolean print)
	{
		this.print = print;
	}

	public Solucao getSolucao()
	{
		return solucao;
	}

	public int getIterador()
	{
		return iterador;
	}

	public AcceptanceCriteria getCriterioAceitacao()
	{
		return criterioAceitacao;
	}

	public Perturbacao[] getPerturbadores()
	{
		return perturbadores;
	}

	public double getTempoMF()
	{
		return tempoMF;
	}

}
