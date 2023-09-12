package AjusteOmega;

public enum TipoAjusteOmega 
{
	Fixo(0),
	Distancia(1),
	Intervalo(2),
	Crescente(3),
	Relativo(4),
	Convergente(5);
	
	final int tipo;
	
	TipoAjusteOmega(int tipo)
	{
		this.tipo=tipo;
	}

}
