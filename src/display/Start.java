package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import server.Server;
import utils.Lang;

import components.ILabel;
import components.Window;

/**
 * @author Jordan Aranda Tejada
 */

public class Start extends JPanel {

	private static final long	serialVersionUID	= - 6855686055738953340L;

	private Server				server;

	private ControlsPanel		controls_panel;
	private InformationPanel	system_panel;
	private ConsolePanel		console_panel;
	private ConnectedUsersPanel	users_panel;

	private boolean				databaseWindowOpened;
	private boolean				usersWindowOpened;

	public Start()
	{
		// this.server = new Server();
		// this.server.start();
		this.databaseWindowOpened = false;
		this.usersWindowOpened = false;

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {450, 0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0};
		gridBagLayout.columnWeights = new double[] {0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[] {1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JPanel panel_izquierdo = new JPanel();
		panel_izquierdo.setOpaque(false);
		GridBagConstraints gbc_panel_izquierdo = new GridBagConstraints();
		gbc_panel_izquierdo.insets = new Insets(0, 0, 10, 5);
		gbc_panel_izquierdo.fill = GridBagConstraints.BOTH;
		gbc_panel_izquierdo.gridx = 0;
		gbc_panel_izquierdo.gridy = 0;
		add(panel_izquierdo, gbc_panel_izquierdo);
		GridBagLayout gbl_panel_izquierdo = new GridBagLayout();
		gbl_panel_izquierdo.columnWidths = new int[] {0, 0};
		gbl_panel_izquierdo.rowHeights = new int[] {120, 160, 0, 0};
		gbl_panel_izquierdo.columnWeights = new double[] {1.0, Double.MIN_VALUE};
		gbl_panel_izquierdo.rowWeights = new double[] {0.0, 0.0, 1.0,
		Double.MIN_VALUE};
		panel_izquierdo.setLayout(gbl_panel_izquierdo);

		// PANEL DE CONTROLES
		controls_panel = new ControlsPanel();
		Lang.setLine(controls_panel, "panel_controls_title");
		GridBagConstraints gbc_panel_controles = new GridBagConstraints();
		gbc_panel_controles.insets = new Insets(10, 15, 5, 15);
		gbc_panel_controles.fill = GridBagConstraints.BOTH;
		gbc_panel_controles.gridx = 0;
		gbc_panel_controles.gridy = 0;
		panel_izquierdo.add(controls_panel, gbc_panel_controles);

		// PANEL INFORMACIÓN DEL SISTEMA
		system_panel = new InformationPanel();
		Lang.setLine(system_panel, "panel_system_title");
		GridBagConstraints gbc_panel_sistema = new GridBagConstraints();
		gbc_panel_sistema.insets = new Insets(0, 15, 5, 15);
		gbc_panel_sistema.fill = GridBagConstraints.BOTH;
		gbc_panel_sistema.gridx = 0;
		gbc_panel_sistema.gridy = 1;
		panel_izquierdo.add(system_panel, gbc_panel_sistema);
		system_panel.setLayout(new BorderLayout(0, 0));

		// PANEL DE USUARIOS
		users_panel = new ConnectedUsersPanel();
		Lang.setLine(users_panel, "panel_users_title");
		GridBagConstraints gbc_panel_usuarios = new GridBagConstraints();
		gbc_panel_usuarios.insets = new Insets(0, 15, 0, 15);
		gbc_panel_usuarios.fill = GridBagConstraints.BOTH;
		gbc_panel_usuarios.gridx = 0;
		gbc_panel_usuarios.gridy = 2;
		panel_izquierdo.add(users_panel, gbc_panel_usuarios);

		JPanel panel_derecho = new JPanel();
		GridBagConstraints gbc_panel_derecho = new GridBagConstraints();
		gbc_panel_derecho.insets = new Insets(0, 0, 10, 15);
		gbc_panel_derecho.fill = GridBagConstraints.BOTH;
		gbc_panel_derecho.gridx = 1;
		gbc_panel_derecho.gridy = 0;
		add(panel_derecho, gbc_panel_derecho);
		GridBagLayout gbl_panel_derecho = new GridBagLayout();
		gbl_panel_derecho.columnWidths = new int[] {60, 0};
		gbl_panel_derecho.rowHeights = new int[] {0, 30, 0};
		gbl_panel_derecho.columnWeights = new double[] {1.0, Double.MIN_VALUE};
		gbl_panel_derecho.rowWeights = new double[] {1.0, 0.0, Double.MIN_VALUE};
		panel_derecho.setLayout(gbl_panel_derecho);

		// PANEL DE LA CONSOLA
		console_panel = new ConsolePanel();
		Lang.setLine(console_panel, "panel_console_title");
		GridBagConstraints gbc_panel_consola = new GridBagConstraints();
		gbc_panel_consola.insets = new Insets(10, 0, 5, 0);
		gbc_panel_consola.fill = GridBagConstraints.BOTH;
		gbc_panel_consola.gridx = 0;
		gbc_panel_consola.gridy = 0;
		panel_derecho.add(console_panel, gbc_panel_consola);

		ILabel lblDesignedBy = new ILabel();
		lblDesignedBy.setForeground(Color.BLACK);
		lblDesignedBy.setFont(new Font("Calibri", Font.ITALIC, 16));
		Lang.setLine(lblDesignedBy, "designed_by_label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel_derecho.add(lblDesignedBy, gbc_lblNewLabel);
	}

	public ConnectedUsersPanel getUsers_panel()
	{
		return users_panel;
	}

	public ConsolePanel getConsolePanel()
	{
		return console_panel;
	}

	public Server getServer()
	{
		return server;
	}

	public boolean isDatabaseWindowOpened()
	{
		return databaseWindowOpened;
	}

	public void setDatabaseWindowOpened(boolean databaseWindowOpened)
	{
		this.databaseWindowOpened = databaseWindowOpened;
	}

	public boolean isUsersWindowOpened()
	{
		return usersWindowOpened;
	}

	public void setUsersWindowOpened(boolean usersWindowOpened)
	{
		this.usersWindowOpened = usersWindowOpened;
	}

	public InformationPanel getSystem_panel()
	{
		return system_panel;
	}

	public void setSystem_panel(InformationPanel system_panel)
	{
		this.system_panel = system_panel;
	}

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(utils.Properties.getLookAndFeel());
		}
		catch (ClassNotFoundException | InstantiationException
		| IllegalAccessException | UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		}

		Start st = new Start();
		Window.getInstance().setContentPane(st);
		Window.getInstance().setVisible(true);
	}
}