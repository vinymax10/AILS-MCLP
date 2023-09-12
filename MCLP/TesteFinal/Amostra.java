package TesteFinal;

import java.lang.reflect.InvocationTargetException;

import Dados.DadosInstancia;
import Dados.Instancia;
import MetaHeuristicas.Algorithm;
import MetaHeuristicas.Config;

public class Amostra implements Runnable
{
	String nome;
	double limite;
	Result result;
	Config config;
	DadosInstancia dadosInstancia;
	boolean rounded;

	public Amostra(Config config,DadosInstancia dadosInstancia, double limite) {
		this.nome = dadosInstancia.nome;
		this.dadosInstancia = dadosInstancia;
		this.limite = limite;
		this.config=new Config();
		this.config=config.clone();
	}
	
	@Override
	public void run() 
	{
		Instancia instancia=new Instancia("Instancias//"+nome+".txt",config,
		dadosInstancia.getNumFacilidades(),dadosInstancia.getRaio());
		
		Algorithm algorithm=null;
		try {
			algorithm = (Algorithm) Class.forName("MetaHeuristicas."+config.getTipoAlgorithm()).
					getConstructor(Instancia.class,Config.class,Double.class,Double.class).
					newInstance(instancia,config,dadosInstancia.getBestSolution().getOtimo(),limite);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		algorithm.setTime(false);
		algorithm.setPrint(false);
		algorithm.setAuditoria(false);
		
		algorithm.procurar();
		if(algorithm.getMelhorF()>dadosInstancia.getBestSolution().getOtimo()||
		(algorithm.getMelhorF()>=dadosInstancia.getBestSolution().getOtimo()&&!dadosInstancia.jaTemosSolucaoSalva))
		{
			System.out.println("Encontrei uma melhor Solucao: "+algorithm.getMelhorF()
			+" otimo: "+dadosInstancia.getBestSolution().getOtimo());
			
			dadosInstancia.getBestSolution().setOtimo(algorithm.getMelhorF());
			
			algorithm.getMelhorSolucao().escrecerSolucao("Otimos//"+nome+"_"+dadosInstancia.getNumFacilidades()+"_"+dadosInstancia.getRaio()+".txt");
			System.out.println(this);
			
			dadosInstancia.setJaTemosSolucaoSalva(true);
		}
		result=new Result();
		result.setDados(algorithm);
	}
	
	public Result getResult() 
	{
		return result;
	}
}
