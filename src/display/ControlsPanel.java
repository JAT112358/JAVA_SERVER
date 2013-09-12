package display;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import utils.Lang;
import utils.Properties;
import components.IButton;
import components.IPanel;
import components.Window;

/**
 * @author Jordan Aranda Tejada
 */

public class ControlsPanel extends IPanel implements ActionListener
{	
	private static final long serialVersionUID = -2610951090020986172L;
	
	private IButton 		btnActive;
	private IButton			btnDatabase;
	private IButton			btnSettings;
	private IButton			btnUsers;
	
	public ControlsPanel() 
	{
		GridBagLayout gbl_panel_controles = new GridBagLayout();
		gbl_panel_controles.columnWidths = new int[]{70, 70, 70, 70, 0};
		gbl_panel_controles.rowHeights = new int[]{0, 0};
		gbl_panel_controles.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel_controles.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gbl_panel_controles);
		
		btnActive = new IButton(new ImageIcon("img/power-off.png"));
		Lang.setLine(btnActive, "tool_tip_text_enable_disable_server");
		btnActive.addActionListener(this); 
		btnActive.setContentAreaFilled(false);
		btnActive.setOpaque(false);
		btnActive.setBorder(null);
		btnActive.setBorderPainted(false);
		GridBagConstraints gbc_btnActivar = new GridBagConstraints();
		gbc_btnActivar.insets = new Insets(0, 0, 0, 5);
		gbc_btnActivar.fill = GridBagConstraints.BOTH;
		gbc_btnActivar.gridx = 0;
		gbc_btnActivar.gridy = 0;
		add(btnActive, gbc_btnActivar);
		
		btnDatabase = new IButton(new ImageIcon("img/database.png"));
		btnDatabase.addActionListener(this); 
		Lang.setLine(btnDatabase, "tool_tip_text_database");
		btnDatabase.setContentAreaFilled(false);
		btnDatabase.setOpaque(false);
		btnDatabase.setBorder(null);
		btnDatabase.setBorderPainted(false);
		GridBagConstraints gbc_btnDatabase = new GridBagConstraints();
		gbc_btnDatabase.insets = new Insets(0, 0, 0, 5);
		gbc_btnDatabase.fill = GridBagConstraints.BOTH;
		gbc_btnDatabase.gridx = 1;
		gbc_btnDatabase.gridy = 0;
		add(btnDatabase, gbc_btnDatabase);
		
		btnSettings = new IButton(new ImageIcon("img/settings-icon.png"));
		btnSettings.addActionListener(this); 
		Lang.setLine(btnSettings, "tool_tip_text_settings");
		btnSettings.setContentAreaFilled(false);
		btnSettings.setOpaque(false);
		btnSettings.setBorder(null);
		btnSettings.setBorderPainted(false);
		GridBagConstraints gbc_btnAjustes = new GridBagConstraints();
		gbc_btnAjustes.insets = new Insets(0, 0, 0, 5);
		gbc_btnAjustes.fill = GridBagConstraints.BOTH;
		gbc_btnAjustes.gridx = 2;
		gbc_btnAjustes.gridy = 0;
		add(btnSettings, gbc_btnAjustes);
		
		btnUsers = new IButton(new ImageIcon("img/users-icon.png"));
		Lang.setLine(btnUsers, "tool_tip_text_users");
		btnUsers.setToolTipText("Usuarios del servidor");
		btnUsers.setContentAreaFilled(false);
		btnUsers.setOpaque(false);
		btnUsers.setBorder(null);
		btnUsers.setBorderPainted(false);
		GridBagConstraints gbc_btnUsuarios = new GridBagConstraints();
		gbc_btnUsuarios.fill = GridBagConstraints.BOTH;
		gbc_btnUsuarios.gridx = 3;
		gbc_btnUsuarios.gridy = 0;
		add(btnUsers, gbc_btnUsuarios);
	}

	public void actionPerformed(ActionEvent e) 
	{
		final Start st = (Start) Window.getInstance().getContentPane();
		//Server server = st.getServer();
		if(e.getSource() == btnActive)
		{
//			if(server.isRunning())
//			{
//				btnActive.setIcon(new ImageIcon("img/power-off.png"));
//				server.setRunning(false);
//				st.getConsolePanel().getTextPaneConsole().addServerMessage("SEVIDOR DESCONECTADO");
//			} else {
//				btnActive.setIcon(new ImageIcon("img/power-on.png"));
//				server.setRunning(true);
//				st.getConsolePanel().getTextPaneConsole().addServerMessage("SEVIDOR CONECTADO");
//			}
		} else if(e.getSource() == btnDatabase)
		{
			if(!st.isDatabaseWindowOpened())
			{
				final JFrame databaseWindow = new JFrame(Lang.getLine("tool_tip_text_database"));
				databaseWindow.setContentPane(new DatabasePanel());
				databaseWindow.setIconImage((new ImageIcon("img/server-icon.png")).getImage());
				databaseWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				databaseWindow.setBounds(Window.getInstance().getX()+30, Window.getInstance().getY()+30, 820, 650);
				databaseWindow.setVisible(true);
				databaseWindow.addWindowListener(new WindowAdapter()
				{
					public void windowClosing(final WindowEvent winEvt)
					{
						st.setDatabaseWindowOpened(false);
						databaseWindow.dispose();
					}
				});
				st.setDatabaseWindowOpened(true);
			}
		} else if(e.getSource() == btnSettings)
		{
			final SettingsPanel p = new SettingsPanel();

			final String[] options = {Lang.getLine("accept_option"), Lang.getLine("cancel_option")};
			final JOptionPane pane = new JOptionPane(p, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null,
			options, options[1]);
			final JDialog dialog = pane.createDialog("Ajustes del servidor");
			dialog.setLocationRelativeTo(Window.getInstance());
			dialog.setResizable(true);
			dialog.setSize(new Dimension(400, 680));
			dialog.setMinimumSize(new Dimension(400, 500));
			dialog.setMaximumSize(new Dimension(400, 680));
			dialog.setVisible(true);

			if (pane.getValue() == options[0])
			{
				Properties.setLocale(Lang.getAvailableLocales().get(p.getComboBox_language().getSelectedIndex()));
				Lang.setLang(Lang.getAvailableLocales().get(p.getComboBox_language().getSelectedIndex()));

				Properties.setLookAndFeelClass(p.getLookAndFeel());

				try {
					UIManager.setLookAndFeel(p.getLookAndFeel());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
					e1.printStackTrace();
				}
				
				SwingUtilities.updateComponentTreeUI(Window.getInstance());
			}
			dialog.dispose();
		} else if(e.getSource() == btnUsers)
		{
			
		} 
	}
}