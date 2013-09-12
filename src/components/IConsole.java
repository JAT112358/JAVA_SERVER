package components;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import utils.LogFile;

public class IConsole extends JTextPane 
{
	private static final long serialVersionUID = 901134918983952294L;
	
	public IConsole()
	{
		super(new ConsoleContent(new StyleContext()));
	}
	
	public void addString(String text)
	{
		((ConsoleContent) getStyledDocument()).addString(text);
		LogFile.write(text);
	}
	
	public void addServerMessage(String text)
	{
		((ConsoleContent) getStyledDocument()).addServerMessage(text);
		LogFile.write(text);
	}
	
	public void addUserMessage(String text)
	{
		((ConsoleContent) getStyledDocument()).addUserMessage(text);
		LogFile.write(text);
	}
}

class ConsoleContent extends DefaultStyledDocument
{
	private static final long serialVersionUID = -3738863506027979831L;
	private  StyleContext	sC;
	
	public ConsoleContent(final StyleContext sC)
	{
		super(sC);
		this.sC = sC;
	}

	/**
	 * Add text to the document
	 * 
	 * @param text The message for document.
	 */
	public void addString(final String text)
	{
		final Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.BLACK);
		try
		{
			super.insertString(getLength(), text + "\n", style);
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add red text to the document
	 * 
	 * @param text The message for document.
	 */
	public void addServerMessage(final String text)
	{
		final Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.BLUE);
		try
		{
			super.insertString(getLength(), text + "\n", style);
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add orange text to the document
	 * 
	 * @param text The message for document.
	 */
	public void addUserMessage(final String text)
	{
		final Style style = sC.addStyle(null, null);
		StyleConstants.setForeground(style, Color.MAGENTA);
		try
		{
			super.insertString(getLength(), text + "\n", style);
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Clearn the document text
	 * 
	 * @param string The message for document.
	 */
	public void cleanContent()
	{
		try
		{
			super.remove(0, getLength());
		}
		catch (final BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}
