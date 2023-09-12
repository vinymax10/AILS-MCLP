package SearchMethod;

import java.text.DecimalFormat;
import java.util.Arrays;

import AjusteOmega.TipoAjusteOmega;
import DiversityControl.TipoCriterioAceitacao;
import Improvement.TipoBL;
import PathReLinking.TipoPathReLinking;
import Perturbation.HeuristicaAdicao;
import Perturbation.TipoPerturbacao;

public class Config implements Cloneable
{
	DecimalFormat deci=new DecimalFormat("0.000");
	int sizeJanelaGA;
	TipoPerturbacao perturbacao[];
	int distM,distM2;
	int distElite;
	int numIterUpdate;
	double eta,etaMin,etaMax;
	TipoCriterioAceitacao criterioAceitacao;
	TipoBL tipoBL;
	double fluxoIdeal;
	int numChilds;
	int sizeElite;
	int numPR;
	double prob;
	TipoPathReLinking tipoPathReLinking;

	double distLearning;
	int iteracaoSemMelhora;
	TipoEscolhaSolPR tipoEscolhaSolPR;
	int omegaFixo,omegaMin,omegaMax;
	TipoAjusteOmega tipoAjusteOmega;
	int periodicidade;
	double relative;
	int limiteKnn;
	HeuristicaAdicao heuristicaAdicao[];
	
	public Config() 
	{
//		----------------------------Main----------------------------
		this.sizeJanelaGA=20;
		this.perturbacao=new TipoPerturbacao[2];
		this.perturbacao[0]=TipoPerturbacao.Perturbacao1;
		this.perturbacao[1]=TipoPerturbacao.Perturbacao9;

		this.tipoAjusteOmega=TipoAjusteOmega.Distancia;
		
		this.limiteKnn=0;
		
		this.numIterUpdate=30; 
//		this.distM=16; //First17;
		this.distM=10; //First16;
		
		this.relative=0.1;
		
		this.distLearning=0.5;
		this.iteracaoSemMelhora=5000;
		
		this.numPR=1;
		
		this.omegaFixo=6;
		this.omegaMin=2;
		this.omegaMax=8;
		
		this.distM2=10; 
		this.eta=0.4; 
		this.etaMin=0.1;
//		this.etaMin=0.01;
		this.etaMax=1;
		this.periodicidade=10000;
		
		this.fluxoIdeal=0.4;
		this.sizeElite=40;
		this.distElite=10;
		this.numChilds=5;
		this.prob=0.9;
		
		this.tipoPathReLinking=TipoPathReLinking.PathReLinking9;
		this.tipoEscolhaSolPR=TipoEscolhaSolPR.AleSolEliteEE;

		
		this.heuristicaAdicao=new HeuristicaAdicao[2];
		this.heuristicaAdicao[0]=HeuristicaAdicao.Aleatorio;
		this.heuristicaAdicao[1]=HeuristicaAdicao.Raio;
		
		this.criterioAceitacao=TipoCriterioAceitacao.SA;
		this.tipoBL=TipoBL.First15;
	}
	
	public Config clone()
	{
		try {
			return (Config) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() 
	{
		return "Config "
		+"\n----------------------------Main----------------------------"
		+"\nsizeJanelaGA: "+sizeJanelaGA
		+"\nperturbacao: "+Arrays.toString(perturbacao)
		+"\nnumIterUpdate: "+numIterUpdate
		+"\ndistM: "+distM
		+"\ndistM2: "+distM2
		+"\neta: " + deci.format(eta)
		+"\netaMin: "+deci.format(etaMin)
		+"\netaMax: "+deci.format(etaMax)
		+"\ncriterioAceitacao: "+criterioAceitacao
		+"\ntipoBL: "+tipoBL
		+"\ntipoEscolhaSolPR: "+tipoEscolhaSolPR
		+"\ntipoPathReLinking: "+tipoPathReLinking
		+"\nfluxoIdeal: " + deci.format(fluxoIdeal)
		+"\nnumChilds: "+deci.format(numChilds)
		+"\nsizeElite: " + sizeElite
		+"\nprob: " + deci.format(prob)
		+"\ndistElite: " + distElite
		+"\niteracaoSemMelhora: " + iteracaoSemMelhora
		+"\ndistLearning: " + deci.format(distLearning)
		+"\nnumPR: " + numPR
		+"\nomegaFixo: "+omegaFixo
		+"\nomegaMin: "+omegaMin
		+"\nomegaMax: "+omegaMax
		+"\ntipoAjusteOmega: "+tipoAjusteOmega
		+"\nperiodicidade: "+periodicidade
		+"\nrelative: "+relative
		+"\nlimiteKnn: "+limiteKnn
		+"\nheuristicaAdicao: "+Arrays.toString(heuristicaAdicao)
		;  
	}
	
	//---------gets and sets-----------

	public int getSizeJanelaGA() {
		return sizeJanelaGA;
	}

	public int getLimiteKnn() {
		return limiteKnn;
	}

	public void setLimiteKnn(int limiteKnn) {
		this.limiteKnn = limiteKnn;
	}

	public double getRelative() {
		return relative;
	}

	public void setRelative(double relative) {
		this.relative = relative;
	}

	public TipoAjusteOmega getTipoAjusteOmega() {
		return tipoAjusteOmega;
	}

	public void setTipoAjusteOmega(TipoAjusteOmega tipoAjusteOmega) {
		this.tipoAjusteOmega = tipoAjusteOmega;
	}

	public void setSizeJanelaGA(int sizeJanelaGA) {
		this.sizeJanelaGA = sizeJanelaGA;
	}

	public TipoPerturbacao[] getPerturbacao() {
		return perturbacao;
	}

	public void setPerturbacao(TipoPerturbacao[] perturbacao) {
		this.perturbacao = perturbacao;
	}

	public int getDistM() {
		return distM;
	}

	public void setDistM(int distM) {
		this.distM = distM;
	}

	public int getNumIterUpdate() {
		return numIterUpdate;
	}

	public void setNumIterUpdate(int numIterUpdate) {
		this.numIterUpdate = numIterUpdate;
	}

	public double getEta() {
		return eta;
	}

	public void setEta(double eta) {
		this.eta = eta;
	}

	public double getEtaMin() {
		return etaMin;
	}

	public void setEtaMin(double etaMin) {
		this.etaMin = etaMin;
	}

	public double getEtaMax() {
		return etaMax;
	}

	public void setEtaMax(double etaMax) {
		this.etaMax = etaMax;
	}

	public TipoCriterioAceitacao getCriterioAceitacao() {
		return criterioAceitacao;
	}

	public void setCriterioAceitacao(TipoCriterioAceitacao criterioAceitacao) {
		this.criterioAceitacao = criterioAceitacao;
	}

	public TipoBL getTipoBL() {
		return tipoBL;
	}

	public void setTipoBL(TipoBL tipoBL) {
		this.tipoBL = tipoBL;
	}

	public double getFluxoIdeal() {
		return fluxoIdeal;
	}

	public void setFluxoIdeal(double fluxoIdeal) {
		this.fluxoIdeal = fluxoIdeal;
	}

	public int getNumChilds() {
		return numChilds;
	}

	public void setNumChilds(int numChilds) {
		this.numChilds = numChilds;
	}

	public int getSizeElite() {
		return sizeElite;
	}

	public void setSizeElite(int sizeElite) {
		this.sizeElite = sizeElite;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

	public int getDistElite() {
		return distElite;
	}

	public void setDistElite(int distElite) {
		this.distElite = distElite;
	}

	public int getDistM2() {
		return distM2;
	}

	public void setDistM2(int distM2) {
		this.distM2 = distM2;
	}

	public double getDistLearning() {
		return distLearning;
	}

	public void setDistLearning(double distLearning) {
		this.distLearning = distLearning;
	}

	public int getIteracaoSemMelhora() {
		return iteracaoSemMelhora;
	}

	public void setIteracaoSemMelhora(int iteracaoSemMelhora) {
		this.iteracaoSemMelhora = iteracaoSemMelhora;
	}

	public TipoPathReLinking getTipoPathReLinking() {
		return tipoPathReLinking;
	}

	public void setTipoPathReLinking(TipoPathReLinking tipoPathReLinking) {
		this.tipoPathReLinking = tipoPathReLinking;
	}

	public TipoEscolhaSolPR getTipoEscolhaSolPR() {
		return tipoEscolhaSolPR;
	}

	public void setTipoEscolhaSolPR(TipoEscolhaSolPR tipoEscolhaSolPR) {
		this.tipoEscolhaSolPR = tipoEscolhaSolPR;
	}

	public int getNumPR() {
		return numPR;
	}

	public void setNumPR(int numPR) {
		this.numPR = numPR;
	}

	public int getOmegaFixo() {
		return omegaFixo;
	}

	public void setOmegaFixo(int omegaFixo) {
		this.omegaFixo = omegaFixo;
	}

	public int getOmegaMin() {
		return omegaMin;
	}

	public void setOmegaMin(int omegaMin) {
		this.omegaMin = omegaMin;
	}

	public int getOmegaMax() {
		return omegaMax;
	}

	public void setOmegaMax(int omegaMax) {
		this.omegaMax = omegaMax;
	}

	public int getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(int periodicidade) {
		this.periodicidade = periodicidade;
	}

	public HeuristicaAdicao[] getHeuristicaAdicao() {
		return heuristicaAdicao;
	}

	public void setHeuristicaAdicao(HeuristicaAdicao[] heuristicaAdicao) {
		this.heuristicaAdicao = heuristicaAdicao;
	}

}
