package Data;

public class Ponto 
{
	public int nome;
	public double x;
	public double y;
	public int demanda;
	
	public int possoAtender[];
	public int possoAtenderR2[];
	
	public Ponto(int nome) 
	{
		super();
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Ponto [nome=" + nome + ", x=" + x + ", y=" + y + ", demanda=" + demanda + "]";
	}

	public int[] getPossoAtender() {
		return possoAtender;
	}

	public void setPossoAtender(int[] possoAtender) {
		this.possoAtender = possoAtender;
	}

	public int[] getPossoAtenderR2() {
		return possoAtenderR2;
	}

	public void setPossoAtenderR2(int[] possoAtenderR2) {
		this.possoAtenderR2 = possoAtenderR2;
	}
	
}
