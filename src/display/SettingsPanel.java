package display;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import components.IButton;
import components.ICheckBox;
import components.ILabel;
import utils.FileChooser;
import utils.Lang;
import utils.Properties;

/**
 * @author Jordan Aranda Tejada
 */

public class SettingsPanel extends JPanel 
{
	private static final long serialVersionUID = 3726753153976194226L;
	private JTextField 				textField_server_ip;
	private JTextField 				textField_server_port;
	private JTextField 				textField_max_users_connected;
	private JTextField 				textField_max_total_users;
	private JTextField 				textField_update_time;
	private JTextField 				textField_table_users_name;
	private JComboBox<String>		comboBox_language;
	private JComboBox<String> 		comboBox_appearance;
	private ICheckBox 				checkBoxLogFileEnable;
	private JLabel 					lblDatabasePath;
	private HashMap<String, String>	lookNFeelHashMap;
	private String					currentLookAndFeel;

	public SettingsPanel() 
	{
		setBorder(null);
		setLayout(new BorderLayout(0, 0));
				
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setBorder(null);
		add(scrollPane, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{3.0, 0.0, 1.0, 0.0, 3.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		ILabel lblTitle = new ILabel();
		Lang.setLine(lblTitle, "tool_tip_text_settings");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setFont(new Font("Calibri", Font.BOLD, 40));
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.gridwidth = 5;
		gbc_lblTitle.insets = new Insets(10, 0, 30, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		panel.add(lblTitle, gbc_lblTitle);
		
		ILabel lblServerIP = new ILabel();
		Lang.setLine(lblServerIP, "panel_settings_lbl_server_ip");
		lblServerIP.setForeground(Color.BLACK);
		lblServerIP.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblServerIP = new GridBagConstraints();
		gbc_lblServerIP.anchor = GridBagConstraints.WEST;
		gbc_lblServerIP.insets = new Insets(0, 0, 10, 10);
		gbc_lblServerIP.gridx = 1;
		gbc_lblServerIP.gridy = 1;
		panel.add(lblServerIP, gbc_lblServerIP);
		
		textField_server_ip = new JTextField(Properties.getServerIP());
		textField_server_ip.setForeground(Color.BLACK);
		textField_server_ip.setFont(new Font("Calibri", Font.PLAIN, 18));
		textField_server_ip.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent arg0) 
			{
				char car = arg0.getKeyChar();
				if(textField_server_ip.getText().length()>=15) 
				{ 
					arg0.consume();
				}
				if((car<'0' || car>'9') && car!='.') arg0.consume();
			}
		});
		GridBagConstraints gbc_textField_server_ip = new GridBagConstraints();
		gbc_textField_server_ip.gridwidth = 2;
		gbc_textField_server_ip.insets = new Insets(0, 0, 10, 5);
		gbc_textField_server_ip.fill = GridBagConstraints.BOTH;
		gbc_textField_server_ip.gridx = 2;
		gbc_textField_server_ip.gridy = 1;
		panel.add(textField_server_ip, gbc_textField_server_ip);
		textField_server_ip.setColumns(10);
		
		ILabel lblServerPORT = new ILabel();
		Lang.setLine(lblServerPORT, "panel_settings_lbl_server_port");
		lblServerPORT.setForeground(Color.BLACK);
		lblServerPORT.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblServerPORT = new GridBagConstraints();
		gbc_lblServerPORT.anchor = GridBagConstraints.WEST;
		gbc_lblServerPORT.insets = new Insets(0, 0, 10, 10);
		gbc_lblServerPORT.gridx = 1;
		gbc_lblServerPORT.gridy = 2;
		panel.add(lblServerPORT, gbc_lblServerPORT);
		
		textField_server_port = new JTextField(Properties.getServerPORT()+"");
		textField_server_port.setForeground(Color.BLACK);
		textField_server_port.setFont(new Font("Calibri", Font.PLAIN, 18));
		textField_server_port.setColumns(6);
		textField_server_port.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent arg0) 
			{
				char car = arg0.getKeyChar();
				if(textField_server_port.getText().length()>=5) 
				{ 
					arg0.consume();
				}
				if(car<'0' || car>'9') arg0.consume();
			}
		});
		GridBagConstraints gbc_textField_server_port = new GridBagConstraints();
		gbc_textField_server_port.gridwidth = 2;
		gbc_textField_server_port.insets = new Insets(0, 0, 10, 5);
		gbc_textField_server_port.fill = GridBagConstraints.BOTH;
		gbc_textField_server_port.gridx = 2;
		gbc_textField_server_port.gridy = 2;
		panel.add(textField_server_port, gbc_textField_server_port);
		
		ILabel lblMaxUsersConnected = new ILabel();
		Lang.setLine(lblMaxUsersConnected, "panel_settings_lbl_max_users_connected");
		lblMaxUsersConnected.setForeground(Color.BLACK);
		lblMaxUsersConnected.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblMaxUsersConnected = new GridBagConstraints();
		gbc_lblMaxUsersConnected.anchor = GridBagConstraints.WEST;
		gbc_lblMaxUsersConnected.gridwidth = 2;
		gbc_lblMaxUsersConnected.insets = new Insets(0, 0, 10, 10);
		gbc_lblMaxUsersConnected.gridx = 1;
		gbc_lblMaxUsersConnected.gridy = 4;
		panel.add(lblMaxUsersConnected, gbc_lblMaxUsersConnected);
		
		textField_max_users_connected = new JTextField(Properties.getMaxUsersConnected()+"");
		textField_max_users_connected.setForeground(Color.BLACK);
		textField_max_users_connected.setFont(new Font("Calibri", Font.PLAIN, 18));
		textField_max_users_connected.setColumns(3);
		textField_max_users_connected.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent arg0) 
			{
				char car = arg0.getKeyChar();
				if(car<'0' || car>'9') arg0.consume();
			}
		});
		GridBagConstraints gbc_textField_max_users_connected = new GridBagConstraints();
		gbc_textField_max_users_connected.insets = new Insets(0, 0, 10, 5);
		gbc_textField_max_users_connected.fill = GridBagConstraints.BOTH;
		gbc_textField_max_users_connected.gridx = 3;
		gbc_textField_max_users_connected.gridy = 4;
		panel.add(textField_max_users_connected, gbc_textField_max_users_connected);
		
		ILabel lblMaxTotalUsers = new ILabel();
		Lang.setLine(lblMaxTotalUsers, "panel_settings_lbl_total_users");
		lblMaxTotalUsers.setForeground(Color.BLACK);
		lblMaxTotalUsers.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblMaxTotalUsers = new GridBagConstraints();
		gbc_lblMaxTotalUsers.anchor = GridBagConstraints.WEST;
		gbc_lblMaxTotalUsers.gridwidth = 2;
		gbc_lblMaxTotalUsers.insets = new Insets(0, 0, 10, 10);
		gbc_lblMaxTotalUsers.gridx = 1;
		gbc_lblMaxTotalUsers.gridy = 5;
		panel.add(lblMaxTotalUsers, gbc_lblMaxTotalUsers);
		
		textField_max_total_users = new JTextField(Properties.getTotalUsers()+"");
		textField_max_total_users.setForeground(Color.BLACK);
		textField_max_total_users.setFont(new Font("Calibri", Font.PLAIN, 18));
		textField_max_total_users.setColumns(3);
		textField_max_total_users.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent arg0) 
			{
				char car = arg0.getKeyChar();
				if(car<'0' || car>'9') arg0.consume();
			}
		});
		GridBagConstraints gbc_textField_max_total_users = new GridBagConstraints();
		gbc_textField_max_total_users.insets = new Insets(0, 0, 10, 5);
		gbc_textField_max_total_users.fill = GridBagConstraints.BOTH;
		gbc_textField_max_total_users.gridx = 3;
		gbc_textField_max_total_users.gridy = 5;
		panel.add(textField_max_total_users, gbc_textField_max_total_users);
		
		ILabel lblUpdateTime = new ILabel();
		Lang.setLine(lblUpdateTime, "panel_settings_lbl_update_time");
		lblUpdateTime.setForeground(Color.BLACK);
		lblUpdateTime.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblUpdateTime = new GridBagConstraints();
		gbc_lblUpdateTime.anchor = GridBagConstraints.WEST;
		gbc_lblUpdateTime.gridwidth = 2;
		gbc_lblUpdateTime.insets = new Insets(0, 0, 10, 10);
		gbc_lblUpdateTime.gridx = 1;
		gbc_lblUpdateTime.gridy = 6;
		panel.add(lblUpdateTime, gbc_lblUpdateTime);
		
		textField_update_time = new JTextField(Properties.getUpdateTime()+"");
		textField_update_time.setForeground(Color.BLACK);
		textField_update_time.setFont(new Font("Calibri", Font.PLAIN, 18));
		textField_update_time.setColumns(3);
		textField_update_time.addKeyListener(new KeyAdapter() 
		{
			public void keyTyped(KeyEvent arg0) 
			{
				char car = arg0.getKeyChar();
				if(car<'0' || car>'9') arg0.consume();
			}
		});
		GridBagConstraints gbc_textField_update_time = new GridBagConstraints();
		gbc_textField_update_time.insets = new Insets(0, 0, 10, 5);
		gbc_textField_update_time.fill = GridBagConstraints.BOTH;
		gbc_textField_update_time.gridx = 3;
		gbc_textField_update_time.gridy = 6;
		panel.add(textField_update_time, gbc_textField_update_time);
		
		ILabel lblLanguage = new ILabel();
		Lang.setLine(lblLanguage, "panel_settings_lbl_language");
		lblLanguage.setForeground(Color.BLACK);
		lblLanguage.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.WEST;
		gbc_lblLanguage.insets = new Insets(0, 0, 10, 10);
		gbc_lblLanguage.gridx = 1;
		gbc_lblLanguage.gridy = 8;
		panel.add(lblLanguage, gbc_lblLanguage);
		
		comboBox_language = new JComboBox<String>(Lang.getCombableLocales());
		comboBox_language.setForeground(Color.BLACK);
		comboBox_language.setFont(new Font("Calibri", Font.PLAIN, 16));
		comboBox_language.setSelectedIndex(Lang.getCurrentLocaleKey());
		GridBagConstraints gbc_comboBox_language = new GridBagConstraints();
		gbc_comboBox_language.gridwidth = 2;
		gbc_comboBox_language.insets = new Insets(0, 0, 10, 5);
		gbc_comboBox_language.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_language.gridx = 2;
		gbc_comboBox_language.gridy = 8;
		panel.add(comboBox_language, gbc_comboBox_language);
		
		ILabel lblAppearance = new ILabel();
		Lang.setLine(lblAppearance, "panel_settings_lbl_appearance");
		lblAppearance.setForeground(Color.BLACK);
		lblAppearance.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblAppearance = new GridBagConstraints();
		gbc_lblAppearance.anchor = GridBagConstraints.WEST;
		gbc_lblAppearance.insets = new Insets(0, 0, 10, 10);
		gbc_lblAppearance.gridx = 1;
		gbc_lblAppearance.gridy = 9;
		panel.add(lblAppearance, gbc_lblAppearance);
		
		comboBox_appearance = new JComboBox<String>(getAvailableLF());
		comboBox_appearance.setSelectedItem(currentLookAndFeel);
		comboBox_appearance.setForeground(Color.BLACK);
		comboBox_appearance.setFont(new Font("Calibri", Font.PLAIN, 16));
		GridBagConstraints gbc_comboBox_appearance = new GridBagConstraints();
		gbc_comboBox_appearance.gridwidth = 2;
		gbc_comboBox_appearance.insets = new Insets(0, 0, 10, 5);
		gbc_comboBox_appearance.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_appearance.gridx = 2;
		gbc_comboBox_appearance.gridy = 9;
		panel.add(comboBox_appearance, gbc_comboBox_appearance);
		
		ILabel lblSelectDatabase = new ILabel();
		Lang.setLine(lblSelectDatabase, "panel_settings_lbl_database");
		lblSelectDatabase.setForeground(Color.BLACK);
		lblSelectDatabase.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblSelectDatabase = new GridBagConstraints();
		gbc_lblSelectDatabase.anchor = GridBagConstraints.WEST;
		gbc_lblSelectDatabase.gridwidth = 2;
		gbc_lblSelectDatabase.insets = new Insets(0, 0, 10, 10);
		gbc_lblSelectDatabase.gridx = 1;
		gbc_lblSelectDatabase.gridy = 11;
		panel.add(lblSelectDatabase, gbc_lblSelectDatabase);
		
		lblDatabasePath = new JLabel(Properties.getDataBasePath());
		lblDatabasePath.setFont(new Font("Calibri", Font.PLAIN, 15));
		GridBagConstraints gbc_lblDatabasePath = new GridBagConstraints();
		gbc_lblDatabasePath.anchor = GridBagConstraints.WEST;
		gbc_lblDatabasePath.gridwidth = 3;
		gbc_lblDatabasePath.insets = new Insets(0, 0, 10, 5);
		gbc_lblDatabasePath.gridx = 1;
		gbc_lblDatabasePath.gridy = 12;
		panel.add(lblDatabasePath, gbc_lblDatabasePath);
		
		IButton btnExamine = new IButton("Examinar");
		Lang.setLine(btnExamine, "panel_settings_btn_examine");
		btnExamine.setForeground(Color.BLACK);
		btnExamine.setFont(new Font("Calibri", Font.PLAIN, 16));
		btnExamine.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				final File file = FileChooser.openFile("Database file", "sqlite3");
				if (file != null)
				{
					lblDatabasePath.setText(file.getAbsolutePath()+".sqlite3");
				}
			}
		});
		GridBagConstraints gbc_btnExamine = new GridBagConstraints();
		gbc_btnExamine.insets = new Insets(0, 0, 10, 5);
		gbc_btnExamine.gridx = 3;
		gbc_btnExamine.gridy = 11;
		panel.add(btnExamine, gbc_btnExamine);
		
		ILabel lblTableUsersName = new ILabel();
		Lang.setLine(lblTableUsersName, "panel_settings_lbl_name_table_users");
		lblTableUsersName.setForeground(Color.BLACK);
		lblTableUsersName.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_lblTableUsersName = new GridBagConstraints();
		gbc_lblTableUsersName.anchor = GridBagConstraints.WEST;
		gbc_lblTableUsersName.gridwidth = 2;
		gbc_lblTableUsersName.insets = new Insets(0, 0, 10, 10);
		gbc_lblTableUsersName.gridx = 1;
		gbc_lblTableUsersName.gridy = 13;
		panel.add(lblTableUsersName, gbc_lblTableUsersName);
		
		textField_table_users_name = new JTextField(Properties.getTableUsersName());
		textField_table_users_name.setForeground(Color.BLACK);
		textField_table_users_name.setFont(new Font("Calibri", Font.PLAIN, 18));
		textField_table_users_name.setColumns(3);
		GridBagConstraints gbc_textField_table_users_name = new GridBagConstraints();
		gbc_textField_table_users_name.insets = new Insets(0, 0, 10, 5);
		gbc_textField_table_users_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_table_users_name.gridx = 3;
		gbc_textField_table_users_name.gridy = 13;
		panel.add(textField_table_users_name, gbc_textField_table_users_name);
		
		JSeparator separator_3 = new JSeparator();
		GridBagConstraints gbc_separator_3 = new GridBagConstraints();
		gbc_separator_3.gridwidth = 5;
		gbc_separator_3.insets = new Insets(0, 0, 10, 0);
		gbc_separator_3.gridx = 0;
		gbc_separator_3.gridy = 14;
		panel.add(separator_3, gbc_separator_3);
		
		checkBoxLogFileEnable = new ICheckBox();
		Lang.setLine(checkBoxLogFileEnable, "panel_settings_checkbox_logfile");
		checkBoxLogFileEnable.setForeground(Color.BLACK);
		checkBoxLogFileEnable.setFont(new Font("Calibri", Font.PLAIN, 18));
		GridBagConstraints gbc_checkBoxLogFileEnable = new GridBagConstraints();
		gbc_checkBoxLogFileEnable.anchor = GridBagConstraints.WEST;
		gbc_checkBoxLogFileEnable.gridwidth = 3;
		gbc_checkBoxLogFileEnable.insets = new Insets(0, 0, 10, 5);
		gbc_checkBoxLogFileEnable.gridx = 1;
		gbc_checkBoxLogFileEnable.gridy = 15;
		panel.add(checkBoxLogFileEnable, gbc_checkBoxLogFileEnable);
		
		if(Properties.isLogFileEnable())
		{
			checkBoxLogFileEnable.setSelected(true);
		}
	}
	
	private Vector<String> getAvailableLF()
	{
		final LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();
		
		lookNFeelHashMap = new HashMap<>(lfs.length);
		final Vector<String> v = new Vector<>(lfs.length);

		for (final LookAndFeelInfo lf2: lfs)
		{
			lookNFeelHashMap.put(lf2.getName(), lf2.getClassName());
			v.add(lf2.getName());
			if (utils.Properties.getLookAndFeel().equals(lf2.getClassName()))
			{
				currentLookAndFeel = lf2.getName();
			}
		}
		return v;
	}

	public JComboBox<String> getComboBox_language() {
		return comboBox_language;
	}

	public String getLookAndFeel()
	{
		return lookNFeelHashMap.get(comboBox_appearance.getSelectedItem());
	}
}
