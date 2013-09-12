package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import utils.Lang;
import components.IButton;
import components.IConsole;
import components.IPanel;
import components.Window;

/**
 * @author Jordan Aranda Tejada
 */

public class ConsolePanel extends IPanel {

	private static final long 	serialVersionUID = 1L;
	
	private IConsole 			textPane_console;
    private JPanel 				input_console_panel;
	private JTextField			textField_input_console;
    private IButton 			btn_enter_comand;
    
	public ConsolePanel() 
	{
		setLayout(new BorderLayout(0, 0));

		textPane_console = new IConsole();
		textPane_console.setEditable(false);
		textPane_console.setFont(new Font("Calibri", Font.PLAIN, 16));
		add(new JScrollPane(textPane_console));
		
		input_console_panel = new JPanel();
		input_console_panel.setForeground(Color.BLACK);
		input_console_panel.setFont(new Font("Calibri", Font.PLAIN, 16));
		add(input_console_panel, BorderLayout.SOUTH);
		GridBagLayout gbl_input_console_panel = new GridBagLayout();
		gbl_input_console_panel.columnWidths = new int[]{0, 0, 0};
		gbl_input_console_panel.rowHeights = new int[]{0, 0};
		gbl_input_console_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_input_console_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		input_console_panel.setLayout(gbl_input_console_panel);
		
		textField_input_console = new JTextField();
		textField_input_console.addKeyListener(new KeyAdapter() 
		{
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyChar() == KeyEvent.VK_ENTER)
				{
					processComand(textField_input_console.getText().trim());
					textField_input_console.setText("");
				}
			}

			public void keyReleased(KeyEvent e) 
			{
				if(e.getKeyChar() == '<')
				{
					textField_input_console.setText(textField_input_console.getText()+">");
				}
			}
		});
		textField_input_console.setForeground(Color.BLACK);
		textField_input_console.setFont(new Font("Calibri", Font.PLAIN, 16));
		GridBagConstraints gbc_textField_input_console = new GridBagConstraints();
		gbc_textField_input_console.insets = new Insets(10, 0, 0, 5);
		gbc_textField_input_console.fill = GridBagConstraints.BOTH;
		gbc_textField_input_console.gridx = 0;
		gbc_textField_input_console.gridy = 0;
		input_console_panel.add(textField_input_console, gbc_textField_input_console);
		textField_input_console.setColumns(10);
		
		btn_enter_comand = new IButton();
		Lang.setLine(btn_enter_comand, "input_command_button");
		btn_enter_comand.setForeground(Color.BLACK);
		btn_enter_comand.setFont(new Font("Calibri", Font.PLAIN, 16));
		GridBagConstraints gbc_btn_enter_comand = new GridBagConstraints();
		gbc_btn_enter_comand.fill = GridBagConstraints.VERTICAL;
		gbc_btn_enter_comand.insets = new Insets(10, 0, 0, 0);
		gbc_btn_enter_comand.gridx = 1;
		gbc_btn_enter_comand.gridy = 0;
		input_console_panel.add(btn_enter_comand, gbc_btn_enter_comand);
	}
	
	public JTextField getTextField_input_console() 
	{
		return textField_input_console;
	}
	
	private void processComand(String comand)
	{
		if(comand.equals("/help"))
		{
			textPane_console.addString(Lang.getLine("text_console_enabled_commands")+"\n" +
								   		"/help\t\t\t"+Lang.getLine("help_command_information")+"\n" +
								   		"/connect\t\t\t"+Lang.getLine("connect_comand_information")+"\n" +
								   		"/disconnect\t\t"+Lang.getLine("disconnect_comand_information")+"\n" +
								   		"/database\t\t\t"+Lang.getLine("database_comand_information")+"\n" +
								   		"/settings\t\t\t"+Lang.getLine("settings_comand_information")+"\n" +
								   		"/users\t\t\t"+Lang.getLine("users_comand_information")+"\n" +
								   		"/block <user>\t\t"+Lang.getLine("block_comand_information")+"\n" +
								   		"/unblock <user>\t\t"+Lang.getLine("unblock_comand_information")+"\n" +
								   		"/delete <user>\t\t"+Lang.getLine("delete_comand_information")+"\n" +
								   		"/message <user> TEXT\t"+Lang.getLine("message_comand_information")+"\n" +
								   		"/message-all TEXT\t\t"+Lang.getLine("message_all_comand_information")+"\n" +
								   		"/close\t\t\t"+Lang.getLine("close_comand_information")+"\n");
		} else if(comand.equals("/connect")) {

		} else if(comand.equals("/disconnect")) {

		} else if(comand.equals("/database")) {
			// Abrir panel base de datos
		} else if(comand.equals("/settings")) {

		} else if(comand.equals("/users")) {
			// Abrir panel usuarios
		} else if(comand.equals("/close")) {
			Window.getInstance().dispose();
		}
		else {
			textPane_console.addString("El comando: "+comand+" no existe.\n" +
								   	   "Introduce el comando /help para ver los comandos disponibles.\n");
		}
	}

	public IConsole getTextPaneConsole() 
	{
		return textPane_console;
	}
}
