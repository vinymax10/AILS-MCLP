package Dados;

public class DadosInstancia
{
	public String nome;
	public Otimo bestSolution;
	public boolean otimo;
	public int numFacilidades;
	public int raio;
	public boolean jaTemosSolucaoSalva;

	public DadosInstancia(String nome, double bestSolution, boolean otimo, boolean jaTemosSolucaoSalva, int numFacilidades,int raio) 
	{
		super();
		this.nome = nome;
		this.bestSolution = new Otimo(bestSolution);
		this.otimo = otimo;
		this.jaTemosSolucaoSalva = jaTemosSolucaoSalva;
		this.numFacilidades = numFacilidades;
		this.raio = raio;
	}
	
	@Override
	public String toString() {
		return "nome: " + nome + "\tbestSolution: " + bestSolution.getOtimo() 
		+ "\totimmo: " + otimo+"\tnumFacilidades: "+numFacilidades+"\traio: "+raio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Otimo getBestSolution() {
		return bestSolution;
	}
	public void setBestSolution(Otimo bestSolution) {
		this.bestSolution = bestSolution;
	}
	public boolean isOtimmo() {
		return otimo;
	}
	public void setOtimmo(boolean otimmo) {
		this.otimo = otimmo;
	}
	public boolean isOtimo() {
		return otimo;
	}
	public void setOtimo(boolean otimo) {
		this.otimo = otimo;
	}

	public int getNumFacilidades() {
		return numFacilidades;
	}

	public void setNumFacilidades(int numFacilidades) {
		this.numFacilidades = numFacilidades;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public boolean isJaTemosSolucaoSalva() {
		return jaTemosSolucaoSalva;
	}

	public void setJaTemosSolucaoSalva(boolean jaTemosSolucaoSalva) {
		this.jaTemosSolucaoSalva = jaTemosSolucaoSalva;
	}
	
}
