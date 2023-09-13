package Perturbation;

public enum HeuristicaAdicao
{
	Aleatorio(0), BestCusto(1), Raio(2), Raio2(3);

	final int tipo;

	HeuristicaAdicao(int tipo)
	{
		this.tipo = tipo;
	}

}
