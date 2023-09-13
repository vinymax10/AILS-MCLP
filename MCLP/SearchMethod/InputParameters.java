package SearchMethod;

import java.io.File;

public class InputParameters
{

	private String file = "";
	private double limit = Double.MAX_VALUE;
	private double best = Double.MAX_VALUE;
	private Config config = new Config();
	private int p=50;
	private int r=400;

	public void readingInput(String[] args)
	{
		try
		{
			for(int i = 0; i < args.length - 1; i += 2)
			{
				switch(args[i]) {
				case "-file":
					file = getEndereco(args[i + 1]);
					break;
				case "-limit":
					limit = getLimit(args[i + 1]);
					break;
				case "-best":
					best = getBest(args[i + 1]);
					break;
				case "-dBeta":
					config.setDBeta(getDBeta(args[i + 1]));
					break;
				case "-gamma":
					config.setGamma(getGamma(args[i + 1]));
					break;
				case "-sigma":
					config.setSigma(getSigma(args[i + 1]));
					break;
				case "-etaMin":
					config.setEtaMin(getEtaMin(args[i + 1]));
					break;
				case "-p":
					p = getP(args[i + 1]);
					break;
				case "-r":
					r = getR(args[i + 1]);
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("File: " + file);
		System.out.println("limit: " + limit);
		System.out.println("Best: " + best);
		System.out.println("LimitTime: " + limit);
		System.out.println(config);
	}

	public String getEndereco(String texto)
	{
		try
		{
			File file = new File(texto);
			if(file.exists() && !file.isDirectory())
				return texto;
			else
				System.err.println("The -file parameter must contain the address of a valid file.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public double getLimit(String texto)
	{
		try
		{
			limit = Double.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -limit parameter must contain a valid real value.");
		}
		return limit;
	}

	public double getBest(String texto)
	{
		
		try
		{
			best = Double.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -best parameter must contain a valid real value.");
		}
		return best;
	}

	public double getEtaMin(String texto)
	{
		double etaMin = 0.1;
		try
		{
			etaMin = Double.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -etaMin parameter must contain a valid real value.");
		}
		return etaMin;
	}

	public int getP(String texto)
	{
		int p = 50;
		try
		{
			p = Integer.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -p parameter must contain a valid integer value.");
		}
		return p;
	}

	public int getR(String texto)
	{
		int r = 400;
		try
		{
			r = Integer.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -r parameter must contain a valid integer value.");
		}
		return r;
	}

	public int getGamma(String texto)
	{
		int gamma = 30;
		try
		{
			gamma = Integer.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -gamma parameter must contain a valid integer value.");
		}
		return gamma;
	}

	public int getSigma(String texto)
	{
		int sigma = 40;
		try
		{
			sigma = Integer.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -sigma parameter must contain a valid integer value.");
		}
		return sigma;
	}

	public int getDBeta(String texto)
	{
		int dBeta = 10;
		try
		{
			dBeta = Integer.valueOf(texto);
		}
		catch(java.lang.NumberFormatException e)
		{
			System.err.println("The -dBeta parameter must contain a valid integer value.");
		}
		return dBeta;
	}

	public String getFile()
	{
		return file;
	}

	public double getTimeLimit()
	{
		return limit;
	}

	public double getBest()
	{
		return best;
	}

	public Config getConfig()
	{
		return config;
	}

	public int getP()
	{
		return p;
	}

	public void setP(int p)
	{
		this.p = p;
	}

	public int getR()
	{
		return r;
	}

	public void setR(int r)
	{
		this.r = r;
	}

}
