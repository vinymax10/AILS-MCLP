package CriterioAceitacao;

public enum TipoCriterioAceitacao 
{
	Fluxo(0),
	Limiar(1),
	Distancia(2),
	KBest(3),
	Stocastic(8),
	Mare(9),
	Best(10),
	FluxoTabu(12),
	Triangulacao(13),
	FluxoLimiar(15),
	FluxoDistancia(16),
	FluxoLimiarMin(17),
	Teta(18),
	SA(19),
	SA2(20),
	SAPeriodico(21),
	Path(22),
	LimiarRestart(23),
	LimiarVariante(24);
	
	final int tipo;
	
	TipoCriterioAceitacao(int tipo)
	{
		this.tipo=tipo;
	}

}
