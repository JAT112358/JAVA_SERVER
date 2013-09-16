package utils;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 * @author Jordan Aranda Tejada
 */
public class SystemResources {

	private static Sigar			sigar;
	private static OperatingSystem	sys;
	private static CpuInfo[]		infoCPU;
	private static CpuPerc[]		cpus;
	private static Mem				memory;

	private static void init()
	{
		sigar = new Sigar();
		infoCPU = null;
		cpus = null;
		memory = null;
		sys = OperatingSystem.getInstance();
		try
		{
			infoCPU = sigar.getCpuInfoList();
			cpus = sigar.getCpuPercList();
			memory = sigar.getMem();
		}
		catch (SigarException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @return Operative System Description
	 */
	public static String getOperativeSystemDescription()
	{
		if (sys == null)
		{
			init();
		}
		return sys.getDescription();
	}

	/**
	 * @return Operative System Name
	 */
	public static String getOperativeSystemName()
	{
		if (sys == null)
		{
			init();
		}
		return sys.getName();
	}

	/**
	 * @return Operative System Architecture
	 */
	public static String getOperativeSystemArchitecture()
	{
		if (sys == null)
		{
			init();
		}
		return sys.getArch();
	}

	/**
	 * @return Operative System Version
	 */
	public static String getOperativeSystemVersion()
	{
		if (sys == null)
		{
			init();
		}
		return sys.getVersion();
	}

	/**
	 * @return Operative System Vendor
	 */
	public static String getOperativeSystemVendor()
	{
		if (sys == null)
		{
			init();
		}
		return sys.getVendor();
	}

	/**
	 * @return System CPU Vendor
	 */
	public static String getSystemCPUVendor()
	{
		if (infoCPU == null)
		{
			init();
		}
		return infoCPU[0].getVendor();
	}

	/**
	 * @return System CPU Model
	 */
	public static String getSystemCPUModel()
	{
		if (infoCPU == null)
		{
			init();
		}
		return infoCPU[0].getModel();
	}

	/**
	 * @return System CPU MHz
	 */
	public static int getSystemCPU_MHz()
	{
		if (infoCPU == null)
		{
			init();
		}
		return infoCPU[0].getMhz();
	}

	/**
	 * @return System CPU Cores
	 */
	public static int getSystemCPUCores()
	{
		if (infoCPU == null)
		{
			init();
		}
		return infoCPU[0].getTotalCores();
	}

	/**
	 * @return System CPUs consume
	 */
	public static double[] getSystemCPUConsume()
	{
		double[] cpu_consume = new double[getSystemCPUCores()];
		init();
		for (int i = 0; i < getSystemCPUCores(); i++)
		{
			cpu_consume[i] = cpus[i].getUser();
		}
		return cpu_consume;
	}

	/**
	 * @return System CPU Total Consume
	 */
	public static double getSystemCPUTotalConsume()
	{
		double consume = 0;
		init();
		try
		{
			consume = sigar.getCpuPerc().getUser();
		}
		catch (SigarException e)
		{
			e.printStackTrace();
		}
		return consume;
	}

	/**
	 * @param args Arguments
	 */
	public static void main(String ... args)
	{
		System.out.println("INFORMACION SO");
		System.out.println(SystemResources.getOperativeSystemDescription());
		System.out.println(SystemResources.getOperativeSystemName());
		System.out.println(SystemResources.getOperativeSystemArchitecture());
		System.out.println(SystemResources.getOperativeSystemVersion());
		System.out.println(SystemResources.getOperativeSystemVendor());
		System.out.println("\nINFORMACIÓN CPU");
		System.out.println(SystemResources.getSystemCPUVendor());
		System.out.println(SystemResources.getSystemCPUModel());
		System.out.println(SystemResources.getSystemCPU_MHz());
		System.out.println(SystemResources.getSystemCPUCores());
		System.out.println("Consumo CPUs");
		double[] cpu_consume = SystemResources.getSystemCPUConsume();
		for (int i = 0; i < SystemResources.getSystemCPUCores(); i++)
		{
			System.out.println("CPU " + i + " \t " + cpu_consume[i]);
		}
		System.out.println("Consumo total CPU: "
		+ SystemResources.getSystemCPUTotalConsume());
	}
}
