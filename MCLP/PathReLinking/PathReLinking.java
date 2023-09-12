package PathReLinking;

import java.util.Random;

import Dados.Instancia;
import Estatistica.Estatistica;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;
import MetaHeuristicas.NoSol;
import MetaHeuristicas.Solucao;

public abstract class PathReLinking 
{
	protected Solucao melhorSolucao;
	protected Solucao solucao;

	protected int numFacilidades;
	protected NoSol nosSaem[];
	protected int topNosSaem=0;
	int size;
	protected NoSol nosEntram[];
	protected int topNosEntram=0;
	protected Media mTopNosSaem;
	protected Instancia instancia;
	protected MetaHeuristicas.Config config;
	protected NoSol no,aux;
	public TipoPathReLinking tipoPathReLinking;
	protected Random rand=new Random();
	protected Estatistica estatistica;
	
	public PathReLinking(Instancia instancia,Config config, Estatistica estatistica) 
	{
		this.instancia=instancia;
		this.estatistica=estatistica;
		this.numFacilidades=instancia.getNumFacilidades();
		this.melhorSolucao=new Solucao(instancia,config);
		this.solucao=new Solucao(instancia,config);
		this.size=instancia.getSize();
		this.nosSaem=new NoSol[numFacilidades];
		this.nosEntram=new NoSol[numFacilidades];
		this.mTopNosSaem=new Media(config.getNumIterUpdate());
	}

	public TipoPathReLinking getTipoPerturbacao() {
		return tipoPathReLinking;
	}

	public boolean pathReLinking(Solucao inicial,Solucao guia)
	{
		return false;
	}

	public Solucao getMelhorSolucao() {
		return melhorSolucao;
	}
	
	
	
}