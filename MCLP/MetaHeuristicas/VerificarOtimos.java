package MetaHeuristicas;

import Dados.Instancia;
import Dados.Instancias;

public class VerificarOtimos 
{
	public static void main(String[] args)
	{
		Instancias instancias=new Instancias();
		
		for (int i = 10; i < instancias.instancias.length; i++) 
		{
			Config config =new Config();
			if(instancias.instancias[i].isJaTemosSolucaoSalva())
			{
				Instancia instancia=new Instancia(
				"Instancias//"+instancias.instancias[i].nome+".txt",config,
				instancias.instancias[i].getNumFacilidades(),instancias.instancias[i].getRaio());
				
				Solucao solucao=new Solucao(instancia,config);
				int fInformado=solucao.carregaSolution1("OtimosFinais//"+instancias.instancias[i].nome+"_"
						+instancias.instancias[i].getNumFacilidades()+"_"+instancias.instancias[i].getRaio()+".txt");
//				System.out.println(solucao);
				System.out.println("valida solucao "
				+instancias.instancias[i].nome+" "
				+instancias.instancias[i].getNumFacilidades()+" "
				+instancias.instancias[i].getRaio()+": "+solucao.validarSolucao());
				if(solucao.f!=fInformado)
					System.out.println("Erro fInformado: "+fInformado+" solucao.f: "+solucao.f);
			}
		}
	}
}
