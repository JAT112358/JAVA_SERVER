package display;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.JTable;
import components.IButton;
import components.IPanel;
import components.ITabbedPane;
import database.DataBase;
import server.ServerMetods;
import utils.Lang;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Jordan Aranda Tejada
 */

public class DatabasePanel extends JPanel 
{
	private static final long 	serialVersionUID = -5645206302391372806L;
	private JList<String> 		list_tables;
	private JTable 				tableView;
	private DefaultTableModel 	dtm;
	private String 				selectedTable;
	private IPanel 				tableViewPanel;
	private JTextArea			textArea_input_console;
	private JTextArea 			textArea_console;

	public DatabasePanel() 
	{
		dtm = new DefaultTableModel();
		
		setBounds(100, 100, 845, 579);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{210, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gbl_contentPane);
		
		JPanel left_panel = new JPanel();
		left_panel.setBorder(new TitledBorder(null, "Tablas", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
		GridBagConstraints gbc_left_panel = new GridBagConstraints();
		gbc_left_panel.insets = new Insets(5, 10, 5, 10);
		gbc_left_panel.fill = GridBagConstraints.BOTH;
		gbc_left_panel.gridx = 0;
		gbc_left_panel.gridy = 0;
		add(left_panel, gbc_left_panel);
		left_panel.setLayout(new BorderLayout(0, 0));
		
		final String [] tableNames = new String[DataBase.getInstance().count("sqlite_master", "type='table'")];
		loadTablesList(tableNames);
		selectedTable = "USERS";
		list_tables = new JList<String>();
		list_tables.setOpaque(false);
		list_tables.setListData(tableNames);
		list_tables.setFont(new Font("Calibri", Font.PLAIN, 15));
		list_tables.addMouseListener(new java.awt.event.MouseAdapter() 
		{
			public void mouseClicked(java.awt.event.MouseEvent e) 
			{
				if( e.getButton() == java.awt.event.MouseEvent.BUTTON1 )
				{
					selectedTable = tableNames[list_tables.locationToIndex(e.getPoint())];
					setTable(selectedTable);
				}
			}
		});
		list_tables.setSelectedValue("USERS", true);
		left_panel.add(new JScrollPane(list_tables), BorderLayout.CENTER);
		
		JPanel right_panel = new JPanel();
		GridBagConstraints gbc_right_panel = new GridBagConstraints();
		gbc_right_panel.insets = new Insets(10, 0, 10, 10);
		gbc_right_panel.fill = GridBagConstraints.BOTH;
		gbc_right_panel.gridx = 1;
		gbc_right_panel.gridy = 0;
		add(right_panel, gbc_right_panel);
		right_panel.setLayout(new BorderLayout(0, 0));
		
			tableViewPanel = new IPanel();
		
			IPanel consoleViewPanel = new IPanel();
		
			ITabbedPane tabbedPane = new ITabbedPane(1);
			tabbedPane.addTab(Lang.getLine("table_view_tab"), tableViewPanel);
			tableViewPanel.setLayout(new BorderLayout(0, 0));
			
			tableView = new JTable();
			tableViewPanel.add(new JScrollPane(tableView), BorderLayout.CENTER);

			tabbedPane.addTab(Lang.getLine("console_view_tab"), consoleViewPanel);
			Lang.setLine(tabbedPane, "");
			GridBagLayout gbl_consoleViewPanel = new GridBagLayout();
			gbl_consoleViewPanel.columnWidths = new int[]{0, 0};
			gbl_consoleViewPanel.rowHeights = new int[]{0, 60, 0};
			gbl_consoleViewPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_consoleViewPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			consoleViewPanel.setLayout(gbl_consoleViewPanel);
			
			JScrollPane scrollPane = new JScrollPane();
			GridBagConstraints gbc_scrollPane = new GridBagConstraints();
			gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
			gbc_scrollPane.fill = GridBagConstraints.BOTH;
			gbc_scrollPane.gridx = 0;
			gbc_scrollPane.gridy = 0;
			consoleViewPanel.add(scrollPane, gbc_scrollPane);
			
			textArea_console = new JTextArea();
			textArea_console.setEditable(false);
			textArea_console.setBackground(Color.BLACK);
			textArea_console.setForeground(Color.GREEN);
			textArea_console.setFont(new Font("Calibri", Font.PLAIN, 16));
			scrollPane.setViewportView(textArea_console);
			
			JPanel panel_input_console = new JPanel();
			GridBagConstraints gbc_panel_input_console = new GridBagConstraints();
			gbc_panel_input_console.fill = GridBagConstraints.BOTH;
			gbc_panel_input_console.gridx = 0;
			gbc_panel_input_console.gridy = 1;
			consoleViewPanel.add(panel_input_console, gbc_panel_input_console);
			GridBagLayout gbl_panel_input_console = new GridBagLayout();
			gbl_panel_input_console.columnWidths = new int[]{0, 0, 0};
			gbl_panel_input_console.rowHeights = new int[]{0, 0};
			gbl_panel_input_console.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
			gbl_panel_input_console.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			panel_input_console.setLayout(gbl_panel_input_console);
			
			JScrollPane scrollPane_1 = new JScrollPane();
			GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
			gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
			gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
			gbc_scrollPane_1.gridx = 0;
			gbc_scrollPane_1.gridy = 0;
			panel_input_console.add(scrollPane_1, gbc_scrollPane_1);
			
			textArea_input_console = new JTextArea();
			textArea_input_console.addKeyListener(new KeyAdapter() 
			{
				public void keyPressed(KeyEvent e) 
				{
					if(e.getKeyChar() == KeyEvent.VK_ENTER)
					{
						processCommand(textArea_input_console.getText().trim());
						textArea_input_console.setText("");
						setTable(selectedTable);
						e.consume();
					}
				}
			});
			textArea_input_console.setFont(new Font("Calibri", Font.PLAIN, 16));
			textArea_input_console.setForeground(Color.GREEN);
			textArea_input_console.setBackground(Color.BLACK);
			scrollPane_1.setViewportView(textArea_input_console);
			
			IButton btn_enter_comand = new IButton();
			btn_enter_comand.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					processCommand(textArea_input_console.getText().trim());
					textArea_input_console.setText("");
					setTable(selectedTable);
				}
			});
			Lang.setLine(btn_enter_comand, "input_command_button");
			btn_enter_comand.setForeground(Color.BLACK);
			btn_enter_comand.setFont(new Font("Calibri", Font.PLAIN, 18));
			GridBagConstraints gbc_btnEnviar = new GridBagConstraints();
			gbc_btnEnviar.fill = GridBagConstraints.VERTICAL;
			gbc_btnEnviar.gridx = 1;
			gbc_btnEnviar.gridy = 0;
			panel_input_console.add(btn_enter_comand, gbc_btnEnviar);
			right_panel.add(tabbedPane, BorderLayout.CENTER);
		
		setTable(selectedTable);
				
		setVisible(true);
	}
	
	private void loadTablesList(String[] tableNames)
	{
		int i = 0;
		ResultSet rs = DataBase.getInstance().consult("SELECT * FROM sqlite_master WHERE type='table'");
		try {
			while(rs.next())
			{
				tableNames[i] = rs.getString("NAME");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setTable(String table)
	{
		ResultSet rs = DataBase.getInstance().consult("SELECT * FROM "+table);
		ResultSetMetaData rsmd;
		String [] header = null;
		String [][] content = null;
		try {
			rsmd = rs.getMetaData();
			header = new String [rsmd.getColumnCount()];
			content = new String [DataBase.getInstance().count(table, null)][rsmd.getColumnCount()];
		    for(int i=1; i<=rsmd.getColumnCount(); i++)
			{
		    	header[i-1] = rsmd.getColumnName(i).toString();
			} 
		    int j=0;
		    while(rs.next())
			{
				for(int i=1; i<=rsmd.getColumnCount(); i++)
				{
					content[j][i-1] = rs.getObject(i).toString();
				}
				j++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dtm.setDataVector(content, header);
		tableView.setModel(dtm);
	}

	private void processCommand(String command)
	{
		textArea_console.append("SQL> "+command+ "\n");
		if(ServerMetods.isUpdateComand(command))
		{
			int updates = DataBase.getInstance().update(command);
			textArea_console.append(updates + " actualizaciones con exito.\n\n");
		} else if(ServerMetods.isConsultComand(command)) 
		{
			ResultSet rs = DataBase.getInstance().consult(command);
			if(rs == null)
			{
				textArea_console.append("Error en el comando "+command+"\n\n");
			} else {
				int rows = 0;
				try {
					while(rs.next())
					{
						rows++;
					}
					rs = DataBase.getInstance().consult(command);
					String [][] resultSet = ServerMetods.resultSetToArray(rs, rows);
					for(int i=1; i<=resultSet[i].length; i++)
					{
						textArea_console.append(resultSet[0][i]);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} else {
			
		}
	}
}