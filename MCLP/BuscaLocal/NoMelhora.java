package BuscaLocal;

import MetaHeuristicas.NoSol;

public class NoMelhora implements Comparable<NoMelhora>
{
	NoSol noSai;
	NoSol noEntra;
	int custo;
	
	@Override
	public int compareTo(NoMelhora o) 
	{
		return o.custo-custo;
	}

	@Override
	public String toString() {
		return "NoMelhora [indexI=" + noSai.nome+"-"+noSai.posicao + ", indexJ=" + noEntra.nome+"-"+noEntra.posicao + ", custo=" + custo + "]";
	}
	
}
