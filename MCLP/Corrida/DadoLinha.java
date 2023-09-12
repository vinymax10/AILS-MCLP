package Corrida;

public class DadoLinha implements Cloneable
{
	int f;
	double time;
	double gap;
	public DadoLinha(int f, double time, double gap) {
		super();
		this.f = f;
		this.time = time;
		this.gap = gap;
	}
	@Override
	public String toString() {
		return "DadoLinha [f=" + f + ", time=" + time + ", gap=" + gap + "]";
	}
	public int getF() {
		return f;
	}
	public void setF(int f) {
		this.f = f;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getGap() {
		return gap;
	}
	public void setGap(double gap) {
		this.gap = gap;
	}
	
	
	protected DadoLinha clone() 
	{
		try {
			return (DadoLinha)super.clone();
		} catch (Exception e) {
			return this;
		}
	}

	
}
