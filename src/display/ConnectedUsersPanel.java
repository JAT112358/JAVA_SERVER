package display;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import utils.Lang;
import utils.Properties;
import components.IPanel;
import entities.User;

/**
 * @author Jordan Aranda Tejada
 */

public class ConnectedUsersPanel extends IPanel
{
	private static final long serialVersionUID = -1966551995047609277L;

	private JList <String> 	users_list;
	private boolean 		running;
	
	public ConnectedUsersPanel() 
	{
		setLayout(new BorderLayout(0, 10));
		users_list = new JList<String>();
		users_list.setFocusable(false);
		users_list.setForeground(Color.BLACK);
		users_list.setFont(new Font("Calibri", Font.ITALIC, 18));
		users_list.setOpaque(false);
		users_list.updateUI();
		add(users_list, BorderLayout.CENTER);
		
		this.running = false;
	}
	
	private void cleanList()
	{
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		users_list.setModel(dlm);
	}
	
	

	public void run() 
	{
		while(true)
		{
			if(running)
			{
				System.out.println("Entra");
				try {Thread.sleep(Properties.getUpdateTime()*1000);} catch (InterruptedException e) {e.printStackTrace();}
				users_list.setListData(User.getConnectedUsersNamesVector());
				setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Lang.getLine("panel_users_title")+" ["+User.getConnectedUsersNamesVector().size()+"]", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, new Font("Calibri", Font.ITALIC, 14), null));
			} else {
				cleanList();
			}
		}
	}
}