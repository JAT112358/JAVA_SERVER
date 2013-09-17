package utils;

import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author Jordan Aranda Tejada
 */

public class LogFile {

	/**
	 * @param line The new line to write in the log file
	 */
	public static void write(String line)
	{
		if (Properties.isLogFileEnable())
		{
			FileWriter file = null;
			PrintWriter pw = null;
			try
			{
				file = new FileWriter("data/log.txt", true);
				pw = new PrintWriter(file);
				pw.append(line);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (file != null)
					{
						file.close();
					}
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		}
	}
}