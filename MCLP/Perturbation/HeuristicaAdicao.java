package Perturbation;

public enum HeuristicaAdicao 
{
	Aleatorio(0),
	BestCusto(1),
	Raio(2),
	Raio2(3),
	KNN(4),
	BestCustoBlink(5);
	
	final int tipo;
	
	HeuristicaAdicao(int tipo)
	{
		this.tipo=tipo;
	}

}
