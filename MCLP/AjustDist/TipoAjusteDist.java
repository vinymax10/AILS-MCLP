package AjustDist;

public enum TipoAjusteDist 
{

	ADistFluxo(1),
	ADistAlfa(2),
	ADistMare(3),
	ADistTabu(4),
	SemAjust(5),
	ADistInterFCFBL(6),
	AOmegaTabu(7),
	ADistTriangulacao(8),
	ADistLinear(9),
	ADistExp(10);
	
	final int tipo;
	
	TipoAjusteDist(int tipo)
	{
		this.tipo=tipo;
	}

}
