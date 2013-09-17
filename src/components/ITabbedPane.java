package components;

import java.awt.Component;

import javax.swing.JTabbedPane;

import utils.Lang;

/**
 * @author Jordan Aranda Tejada
 */
public class ITabbedPane extends JTabbedPane implements Internationalizable {

	private static final long	serialVersionUID	= - 3263230912894274789L;

	/**
	 * Creates a TabbedPane
	 */
	public ITabbedPane()
	{
		super();
	}

	/**
	 * Creates a TabbedPane
	 * 
	 * @param tabPlacement
	 */
	public ITabbedPane(int tabPlacement)
	{
		super(tabPlacement);
	}

	public ITabbedPane(int tabPlacement, int tabLayoutPolicy)
	{
		super(tabPlacement, tabLayoutPolicy);
	}

	@Override
	public void addTab(String title, Component component)
	{
		super.addTab(title, component);
	}

	@Override
	public String getTitleAt(int index)
	{
		return super.getTitleAt(index);
	}

	@Override
	public void setTitleAt(int index, String title)
	{
		super.setTitleAt(index, title);
	}

	@Override
	public void changeLanguage(String newText)
	{
		setTitleAt(0, Lang.getLine("table_view_tab"));
		setTitleAt(1, Lang.getLine("console_view_tab"));
	}
}