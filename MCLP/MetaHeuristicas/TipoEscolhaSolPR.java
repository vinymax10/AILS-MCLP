package MetaHeuristicas;

public enum TipoEscolhaSolPR 
{
	SolElite(0),
	EliteSol(1),
	EliteElite(2),
	AleSolElite(3),
	AleSolEliteEE(4),
	SolEliteEE(5),
	EliteSolEE(6);
	
	final int tipo;
	
	TipoEscolhaSolPR(int tipo)
	{
		this.tipo=tipo;
	}

}
