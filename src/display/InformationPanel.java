package display;

import java.awt.BorderLayout;

import javax.swing.JTextArea;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import utils.Properties;

import components.IPanel;

/**
 * @author Jordan Aranda Tejada
 */
public class InformationPanel extends IPanel {

	private static final long	serialVersionUID	= - 8580797369160111257L;
	private boolean				running;
	private JTextArea			textArea;
	private Sigar				sigar;

	public InformationPanel()
	{
		this.running = true;
		setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea();
		add(textArea, BorderLayout.CENTER);

		setVisible(true);
	}

	public void run()
	{
		while (true)
		{
			if (running)
			{
				System.out.println("Entra");
				try
				{
					Thread.sleep(Properties.getUpdateTime() * 1000);
					sigar = new Sigar();
					CpuInfo[] infos = null;
					CpuPerc[] cpus = null;
					try
					{
						infos = sigar.getCpuInfoList();
						cpus = sigar.getCpuPercList();
					}
					catch (SigarException e)
					{
						e.printStackTrace();
					}
					CpuInfo info = infos[0];
					textArea.setText("Total CPUs\t\t" + info.getTotalCores());
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				setVisible(false);
			}
		}
	}
}