package CriterioAceitacao;

import java.lang.reflect.InvocationTargetException;

import ConstrutorInicial.ConstrutorSolucao;
import Dados.Instancia;
import MetaHeuristicas.Config;
import MetaHeuristicas.Media;
import MetaHeuristicas.Solucao;

public class CALimiarRestart extends CA
{
	int newMelhorF=0;
	int interadorMelhorF;
	int iteracaoSemMelhora;
	ConstrutorSolucao construtorSolucao;
	
	public CALimiarRestart(Instancia instancia, Config config, Double max)
	{
		super(config);
		try {
			this.construtorSolucao=(ConstrutorSolucao) Class.forName("ConstrutorInicial."+config.getTipoConst()).
					getConstructor(Instancia.class,Config.class).
					newInstance(instancia,config);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		iteracaoSemMelhora=config.getIteracaoSemMelhora();
	}
	
	public boolean aceitaSolucao(Solucao solucao, double distanciaBLEdge, boolean fase2)
	{
		if(fase2)
			return false;
		else
		{
			if((iterador-interadorMelhorF)>iteracaoSemMelhora)
			{
				newMelhorF=0;
				interadorMelhorF=iterador;
				construtorSolucao.construir(solucao);
				return true;
			}
			
			if(newMelhorF<solucao.f)
			{
				newMelhorF=solucao.f;
				interadorMelhorF=iterador;
			}
			
			update(solucao.f);
			
			limiarF=(int)(mFBL.getMediaDinam()+((1-eta)*(teto-mFBL.getMediaDinam())));
			if(solucao.f>limiarF)
			{
				qtnPassouReal++;
				mDistLearnig.setValor(distanciaBLEdge);
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean aceitaSolucao(int f)
	{
		update(f);
		
		limiarF=(int)(mFBL.getMediaDinam()+((1-eta)*(teto-mFBL.getMediaDinam())));
		if(f>limiarF)
		{
			qtnPassouReal++;
			return true;
		}
		else
			return false;
	}
	
	@Override
	public double getEta() {
		return eta;
	}

	@Override
	public Media getmFluxo() {
		return mFluxo;
	}

	@Override
	public Media getmEta() {
		return mEta;
	}

	@Override
	public double getLimiarF() {
		return limiarF;
	}

	@Override
	public double getFluxoAtual() {
		return fluxoAtual;
	}
	
	public Media getmDistLearnig() {
		return mDistLearnig;
	}
	
	public void setEta(double eta) {
		this.eta = eta;
	}
	
	public void setFluxoIdeal(double fluxoIdeal) {}
}
