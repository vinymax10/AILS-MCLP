package Data;

public class Instancias 
{
	public DadosInstancia instancias[];
	
	public Instancias()
	{
		this.instancias=new DadosInstancia[25];
		this.instancias[0]=new DadosInstancia("NewYork",1153640,true,true,50,400);
		this.instancias[1]=new DadosInstancia("NewYork",1288174,true,true,60,400);
		this.instancias[2]=new DadosInstancia("NewYork",1396333,true,true,70,400);
		this.instancias[3]=new DadosInstancia("NewYork",1481434,true,true,80,400);
		this.instancias[4]=new DadosInstancia("NewYork",1539992,true,true,90,400);
		this.instancias[5]=new DadosInstancia("NewYork",1574256,false,true,100,400);
		this.instancias[6]=new DadosInstancia("Bronx",1205051,true,true,50,600);
		this.instancias[7]=new DadosInstancia("Bronx",1290635,true,true,60,600);
		this.instancias[8]=new DadosInstancia("Bronx",1348232,false,true,70,600);
		this.instancias[9]=new DadosInstancia("Bronx",1376061,false,true,80,600);
		this.instancias[10]=new DadosInstancia("Bronx",1384376,false,true,90,600);
		this.instancias[11]=new DadosInstancia("Bronx",1385099,false,true,100,600);
		this.instancias[12]=new DadosInstancia("SanFrancisco",617592,true,true,50,600);
		this.instancias[13]=new DadosInstancia("SanFrancisco",683139,false,true,60,600);
		this.instancias[14]=new DadosInstancia("SanFrancisco",733772,false,true,70,600);
		this.instancias[15]=new DadosInstancia("SanFrancisco",769781,false,true,80,600);
		this.instancias[16]=new DadosInstancia("SanFrancisco",791562,false,true,90,600);
		this.instancias[17]=new DadosInstancia("SanFrancisco",802307,false,true,100,600);
		this.instancias[18]=new DadosInstancia("Kings",2022077,false,true,50,800);
		this.instancias[19]=new DadosInstancia("Kings",2227025,false,true,60,800);
		this.instancias[20]=new DadosInstancia("Kings",2380660,false,true,70,800);
		this.instancias[21]=new DadosInstancia("Kings",2467701,false,true,80,800);
		this.instancias[22]=new DadosInstancia("Kings",2502669,false,true,90,800);
		this.instancias[23]=new DadosInstancia("Kings",2504700,true,true,100,800);
		this.instancias[24]=new DadosInstancia("SJC32",0,true,false,10,120);
	}
	
	public String toString()
	{
		String str="";
		for(DadosInstancia dadosInstancia : instancias)
		{
			str+=dadosInstancia+"\n";
		}
		return str;
	}
	
	public static void main(String[] args) 
	{
		Instancias instancias=new Instancias();
		
		System.out.println(instancias);
		
	}
}
