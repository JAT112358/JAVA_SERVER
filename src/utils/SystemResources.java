package utils;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

/**
 * @author Jordan Aranda Tejada
 */
public class SystemResources {

	private static Sigar			sigar;
	private static OperatingSystem	sys;
	private static CpuInfo[]		infoCPU;
	private static CpuPerc[]		cpus;
	private static Mem				memory;
	private static Swap				swap;

	private static void init()
	{
		sigar = new Sigar();
		infoCPU = null;
		cpus = null;
		memory = null;
		swap = null;
		sys = OperatingSystem.getInstance();
		try
		{
			infoCPU = sigar.getCpuInfoList();
			cpus = sigar.getCpuPercList();
			memory = sigar.getMem();
			swap = sigar.getSwap();
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
	 * @return System RAM
	 */
	public static double getSystemRAMMemory()
	{
		if (memory == null)
		{
			init();
		}
		return memory.getRam();
	}

	/**
	 * @return System RAM in bytes
	 */
	public static long getSystemTotalMemory()
	{
		if (memory == null)
		{
			init();
		}
		return toBytes(memory.getRam());
	}

	/**
	 * @return System used memory in bytes
	 */
	public static long getSystemUsedMemory()
	{
		if (memory == null)
		{
			init();
		}
		return toBytes(memory.getActualUsed());
	}

	/**
	 * @return System free memory in bytes
	 */
	public static long getSystemFreeMemory()
	{
		if (memory == null)
		{
			init();
		}
		return toBytes(memory.getActualFree());
	}

	/**
	 * @return System total swap memory in bytes
	 */
	public static long getSystemTotalSwapMemory()
	{
		if (swap == null)
		{
			init();
		}
		return toBytes(swap.getTotal());
	}

	/**
	 * @return System used swap memory in bytes
	 */
	public static long getSystemUsedSwapMemory()
	{
		if (swap == null)
		{
			init();
		}
		return toBytes(swap.getUsed());
	}

	/**
	 * @return System free swap memory in bytes
	 */
	public static long getSystemFreeSwapMemory()
	{
		if (swap == null)
		{
			init();
		}
		return toBytes(swap.getFree());
	}

	private static Long toBytes(long value)
	{
		return new Long(value / 1024);
	}

	/**
	 * @return The uptime in array (0-days, 1-hours, 2-minutes, 3-seconds)
	 */
	public static int[] getUptime()
	{
		int[] time = new int[4];
		double uptime = 0;
		try
		{
			uptime = sigar.getUptime().getUptime();
		}
		catch (SigarException e)
		{
			e.printStackTrace();
		}

		int days = (int) uptime / (60 * 60 * 24);
		int seconds, minutes, hours;
		minutes = (int) uptime / 60;
		hours = minutes / 60;
		hours %= 24;
		minutes %= 60;
		seconds = (int) (uptime % 60);

		time[0] = days;
		time[1] = hours;
		time[2] = minutes;
		time[3] = seconds;

		return time;
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

		System.out.println("Tiempo encendido: "
		+ SystemResources.getUptime()[0] + " dias, "
		+ SystemResources.getUptime()[1] + " horas, "
		+ SystemResources.getUptime()[2] + " minutos,"
		+ SystemResources.getUptime()[3] + " segundos");
	}
}
