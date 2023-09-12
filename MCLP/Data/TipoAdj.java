package Data;

public enum TipoAdj 
{

	NaoAdj((short) 0),
	Raio((short)1),
	Raio2((short)2);
	
	final short tipo;
	
	TipoAdj(short tipo)
	{
		this.tipo=tipo;
	}

}
