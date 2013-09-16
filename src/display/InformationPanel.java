package display;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JList;

import org.hyperic.sigar.CpuPerc;

import utils.SystemResources;

import components.IPanel;

/**
 * @author Jordan Aranda Tejada
 */
public class InformationPanel extends IPanel implements Runnable {

	private static final long	serialVersionUID	= - 8580797369160111257L;
	private JList<String>		listSystemResources;

	/**
	 * Panel with system resources information
	 */
	public InformationPanel()
	{
		setLayout(new BorderLayout(0, 0));

		listSystemResources = new JList<String>();
		listSystemResources.setRequestFocusEnabled(false);
		listSystemResources.setFocusable(false);
		listSystemResources
		.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
		listSystemResources.setOpaque(false);
		add(listSystemResources, BorderLayout.CENTER);
	}

	@Override
	public void run()
	{
		while (true)
		{
			String[] consumeCPU = new String[SystemResources
			.getSystemCPUCores() + 1];
			double[] consumeCPUvalues = SystemResources.getSystemCPUConsume();
			for (int i = 0; i < SystemResources.getSystemCPUCores(); i++)
			{
				consumeCPU[i] = "Consumo CPU " + (i + 1) + "                "
				+ CpuPerc.format(consumeCPUvalues[i]);
			}
			consumeCPU[SystemResources.getSystemCPUCores()] = "Consumo total de CPU     "
			+ CpuPerc.format(SystemResources.getSystemCPUTotalConsume());
			listSystemResources.setListData(consumeCPU);
		}
	}
}