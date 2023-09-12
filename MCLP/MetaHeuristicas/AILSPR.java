package MetaHeuristicas;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Random;

import AjustDist.DistIdeal;
import AjusteOmega.AjusteOmega;
import BuscaLocal.BuscaLocal;
import ConstrutorInicial.ConstrutorSolucao;
import CriterioAceitacao.CA;
import Dados.Instancia;
import Dados.Instancias;
import Elite.SubConj;
import Estatistica.Estatistica;
import PathReLinking.PathReLinking;
import Perturbacao.Perturbacao;

public class AILSPR 
{
	//----------Problema------------
	Solucao solucao,solucaoReferencia;
	Solucao melhorSolucao;
//	Solucao best;

	Instancia instancia;
	Distancia distEntreSolucoes;
	double melhorF;
	double MAX;
	double otimo;
	
	//----------IGAS------------
	Config config;
	
	//----------caculoLimiar------------
	int numIterUpdate;
	double fBL,fConstr,tempoC,tempoBL,tempoElite,tempoPR;
	//----------Metricas------------
	int iterador;
	long inicio,ini;
	double tempoMF;
	Media mFBL,mFC,mtempoC,mtempoBL,mtempoElite,mtempoPR;
	
	Random rand=new Random();
	
	AjusteOmega ajusteOmega;
	
	double distanciaBL;
	
	Perturbacao[] perturbadores;
	Perturbacao perturbacaoEscolhida;
	
	ConstrutorSolucao construtorSolucao;
	
	BuscaLocal buscaLocal;
	PathReLinking pathReLinking;

	boolean aceitoCriterio,isMelhorSolucao;
	CA criterioAceitacao;
//	----------Mare------------
	DistIdeal distIdeal;
	DistIdeal distElite;
//	---------Print----------
	boolean print=true;
	boolean time=true;
	boolean auditoria=true;

	SubConj subConj;
	DecimalFormat deci=new DecimalFormat("0.0000");
	Estatistica estatistica;
	TipoEscolhaSolPR tipoEscolhaSolPR;
	int numPR;
	
	public AILSPR(Instancia instancia,Config config,Double d,Double MAX)
	{
		this.instancia=instancia;
		this.estatistica=new Estatistica(instancia,config);
		this.numPR=config.getNumPR();
		
		this.solucao =new Solucao(instancia,config);
		this.solucaoReferencia =new Solucao(instancia,config);
		this.melhorSolucao =new Solucao(instancia,config);
//		this.best=new Solucao(instancia,config);
//		this.best.carregaSolution1("OtimosFinais//"
//		+instancia.getNome().substring(12,instancia.getNome().length()-4)+"_"
//		+instancia.getNumFacilidades()+"_"+instancia.getRaio()+".txt");
//		System.out.println("best: "+best);
		this.config=config;
		this.otimo=d;
		this.MAX=MAX;
		this.melhorF=0;
		
		this.distIdeal=new DistIdeal();
		this.distIdeal.distIdeal=(int)config.getDistM();
		
		this.distElite=new DistIdeal();
		this.distElite.distIdeal=config.getDistElite();
		
		this.mFBL=new Media(config.getNumIterUpdate());
		this.mFC=new Media(config.getNumIterUpdate());
		this.mtempoC=new Media(config.getNumIterUpdate());
		this.mtempoBL=new Media(config.getNumIterUpdate());
		this.mtempoElite=new Media(config.getNumIterUpdate());
		this.mtempoPR=new Media(config.getNumIterUpdate());
		
		this.distEntreSolucoes=new Distancia();
		
		this.perturbadores=new Perturbacao[config.getPerturbacao().length];
		
		this.tipoEscolhaSolPR=config.getTipoEscolhaSolPR();
		
		try {
			
			this.buscaLocal=(BuscaLocal) Class.forName("BuscaLocal.BuscaLocal"+config.getTipoBL()).
			getConstructor(Instancia.class,Config.class).
			newInstance(instancia,config);
			
			this.pathReLinking=(PathReLinking) Class.forName("PathReLinking."+config.getTipoPathReLinking()).
			getConstructor(Instancia.class,Config.class,Estatistica.class).
			newInstance(instancia,config,estatistica);
			
			this.construtorSolucao=(ConstrutorSolucao) Class.forName("ConstrutorInicial."+config.getTipoConst()).
			getConstructor(Instancia.class,Config.class).
			newInstance(instancia,config);
			
			this.criterioAceitacao=(CA) Class.forName("CriterioAceitacao.CA"+config.getCriterioAceitacao()).
			getConstructor(Instancia.class,Config.class,Double.class).
			newInstance(instancia,config,MAX);
			
			subConj=(SubConj) Class.forName("Elite."+config.getTipoSubConj()).
					getConstructor(Instancia.class,Config.class,Distancia.class,DistIdeal.class).
					newInstance(instancia,config,distEntreSolucoes,distElite);
			
			for (int i = 0; i < perturbadores.length; i++) 
			{
				AjusteOmega ajusteOmega = (AjusteOmega)Class.forName("AjusteOmega.AO"+config.getTipoAjusteOmega()).
						getConstructor(Instancia.class,Config.class,DistIdeal.class,Double.class).
						newInstance(instancia,config,distIdeal,MAX);
				
				this.perturbadores[i]=(Perturbacao) Class.forName("Perturbacao."+config.getPerturbacao()[i]).
				getConstructor(Instancia.class,Config.class,AjusteOmega.class,Estatistica.class).
				newInstance(instancia,config,ajusteOmega,estatistica);
				
			}
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException
				| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void procurar()
	{
		iterador=0;
		inicio=System.currentTimeMillis();
		construtorSolucao.construir(solucaoReferencia);
		
		if(auditoria&&!solucaoReferencia.validarSolucao())
			System.out.println("Erro na solucaoReferenciaFase1 linha 149 ");
		
		buscaLocal.buscaLocal(solucaoReferencia);
		melhorSolucao.clone(solucaoReferencia);

		if(auditoria&&!melhorSolucao.validarSolucao())
			System.out.println("Erro na melhorSolucao linha 155 ");
		
		subConj.add(solucaoReferencia);
		
		while(MAX>(System.currentTimeMillis()-inicio)/1000)
//		while(dimacs||melhorF<otimo&&MAX>(System.currentTimeMillis()-inicio)/1000)
		{
			iterador++;
			
			isMelhorSolucao=false;
			
//			Escolhe referencia
			solucao.clone(solucaoReferencia);
			
//			Perturbacao
			perturbacaoEscolhida=perturbadores[rand.nextInt(perturbadores.length)];
			
			if(time)
				ini=System.nanoTime();
			
			perturbacaoEscolhida.perturbar(solucao);
			
			if(time)
			{
				tempoC=((double)(System.nanoTime()-ini)/1000000);
				mtempoC.setValor(tempoC);
			}
			
			fConstr=solucao.f;
				
//			BL
			if(time)
				ini=System.nanoTime();
			
			buscaLocal.buscaLocal(solucao);
			if(time)
			{
				tempoBL=((double)(System.nanoTime()-ini)/1000000);
				mtempoBL.setValor(tempoBL);
			}

			fBL=solucao.f;
			
			if(auditoria&&!solucao.validarSolucao())
				System.out.println("Erro na solucao linha 199 ");
			
			distanciaBL=distEntreSolucoes.distancia(solucao,solucaoReferencia);
			
//			Analise solucao
			analisaSolucao(solucao,"AILS");
			
//			update
			perturbacaoEscolhida.getAjusteOmega().setDistancia(distanciaBL);
			
			if(time)
				ini=System.nanoTime();
			
			if((distanciaBL>=distIdeal.distIdeal||
			(distanciaBL<distIdeal.distIdeal && solucao.f<solucaoReferencia.f)))
				subConj.add(solucao);
			
			if(time)
			{
				tempoElite=((double)(System.nanoTime()-ini)/1000000);
				mtempoElite.setValor(tempoElite);
			}
//			System.out.println(estatistica);
//			System.out.println("distancia estatistica: "+estatistica.distancia(best));
//			criterio aceitacao
			if(criterioAceitacao.aceitaSolucao(solucao,distanciaBL,false))
			{
				aceitoCriterio=true;
				solucaoReferencia.clone(solucao);

				estatistica.setSolucao(solucao);
			}
			else
				aceitoCriterio=false;
			
			if(print)
				mFC.setValor(fConstr);
			
			mFBL.setValor(fBL);
			
			if(time)
				ini=System.nanoTime();
			
			if(PR())
			{
				buscaLocal.buscaLocal(pathReLinking.getMelhorSolucao());
				
				subConj.add(pathReLinking.getMelhorSolucao());
				analisaSolucao(pathReLinking.getMelhorSolucao(),"PR");
			}
			
			if(time)
			{
				tempoPR=((double)(System.nanoTime()-ini)/1000000);
//				System.out.println("tempoBL: "+tempoBL);
				mtempoPR.setValor(tempoPR);
			}
			
			if(print)
			{
				if(iterador%1000==0)
				{
//					System.out.println("timeBL: "+mtempoBL);
					System.out.println(
					"mtempoC: "+mtempoC
					+" mtempoBL: "+mtempoBL
					+" mtempoPR: "+mtempoPR
					+" mtempoElite: "+mtempoElite);

//					System.out.println(subConj.toString());
				}
				
			}
		}
	}
	
	public void analisaSolucao(Solucao solucao,String metodo)
	{
		if(solucao.f>melhorF)
		{		
			isMelhorSolucao=true;
			melhorF=solucao.f;
			melhorSolucao.clone(solucao);
			
			if(auditoria&&!melhorSolucao.validarSolucao())
				System.out.println("Erro na melhorSolucao");
			
			tempoMF=(double)(System.currentTimeMillis()-inicio)/1000;
			
			if(print)
			{
				System.out.println("melhorF: "+melhorF
//				+" distancia: "+distEntreSolucoes.distancia(best,melhorSolucao)
				+" metodo: "+metodo
				+" gap: "+getGap()
				+" eta: "+criterioAceitacao.getEta()
				+" fluxo: "+criterioAceitacao.getFluxoAtual()
				+" omega: "+perturbacaoEscolhida.omega
				);
			}
		}
	}
	
	private boolean PR()
	{
		boolean achou=false;
		switch(tipoEscolhaSolPR)
		{
			case AleSolElite:
				switch(rand.nextInt(2))
				{
					case 0: achou=pathReLinking.pathReLinking(subConj.getSolucao(), solucao);break;
					case 1: achou=pathReLinking.pathReLinking(solucao,subConj.getSolucao());break;
				}
				break;
				
			case AleSolEliteEE: 
				switch(rand.nextInt(3))
				{
					case 0: achou=pathReLinking.pathReLinking(subConj.getSolucao(), solucao);break;
					case 1: achou=pathReLinking.pathReLinking(solucao,subConj.getSolucao());break;
					case 2: achou=pathReLinking.pathReLinking(subConj.getSolucao(),subConj.getSolucao());break;
				}
				break;
				
			case EliteElite: 
				pathReLinking.pathReLinking(subConj.getSolucao(),subConj.getSolucao()); 
				break;
				
			case EliteSol: 
				pathReLinking.pathReLinking(subConj.getSolucao(), solucao); 
				break;
				
			case SolElite: 
				pathReLinking.pathReLinking(solucao,subConj.getSolucao()); 
				break;
			
			case SolEliteEE: 
				switch(rand.nextInt(2))
				{
					case 0: achou=pathReLinking.pathReLinking(subConj.getSolucao(), subConj.getSolucao());break;
					case 1: achou=pathReLinking.pathReLinking(solucao,subConj.getSolucao());break;
				}
				break;
				
			case EliteSolEE: 
				switch(rand.nextInt(2))
				{
					case 0: achou=pathReLinking.pathReLinking(subConj.getSolucao(), subConj.getSolucao());break;
					case 1: achou=pathReLinking.pathReLinking(subConj.getSolucao(),solucao);break;
				}
				break;
		}
		return achou;
	}
	
	public static void main(String[] args) 
	{
		Instancias instancias=new Instancias();
		
		int pos=5;	
		
		Config config =new Config();
		long inicio=System.currentTimeMillis();
		Instancia instancia=new Instancia(
		"Instancias//"+instancias.instancias[pos].nome+".txt",config,
		instancias.instancias[pos].getNumFacilidades(),instancias.instancias[pos].getRaio());
		
		double tempo=(double)(System.currentTimeMillis()-inicio)/1000;

		System.out.println("instancia criada tempo: "+tempo);
		
		for (int i = 0; i < 1000; i++)
		{
			System.out.println(instancias.instancias[pos].nome
			+" otimo: "+instancias.instancias[pos].bestSolution.getOtimo());
			System.out.println("i: "+i);
			AILSPR igas=new AILSPR(instancia,config,instancias.instancias[pos].bestSolution.getOtimo(),(double) 300);
			igas.procurar();
			if(igas.melhorF>instancias.instancias[pos].bestSolution.getOtimo())
				System.out.println("\n--------------------NAO CONSEGUIU--------------------\n");
		}
	}
	
	public Solucao getMelhorSolucao() {
		return melhorSolucao;
	}

	public double getMelhorF() {
		return melhorF;
	}

	public Media getMediaBL() {
		return mFBL;
	}

	public double getGap()
	{
		return 100*((otimo-melhorF)/otimo);
	}
	
	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public Solucao getSolucao() {
		return solucao;
	}

	public int getIterador() {
		return iterador;
	}

	public CA getCriterioAceitacao() {
		return criterioAceitacao;
	}

	public Perturbacao[] getPerturbadores() {
		return perturbadores;
	}

	public boolean isTime() {
		return time;
	}

	public void setTime(boolean time) {
		this.time = time;
	}
	
	public boolean isAuditoria() {
		return auditoria;
	}

	public void setAuditoria(boolean auditoria) {
		this.auditoria = auditoria;
	}

	public double getTempoMF() {
		return tempoMF;
	}
	
}
