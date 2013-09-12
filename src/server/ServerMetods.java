package server;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * @author Jordan Aranda Tejada
 */
public class ServerMetods 
{

	public static String getFirstWord (String command)
	{
		String first_word = "";
		char character = 0;
		int i = 0;
		while(character!=' ' && i<command.length())
		{
			character = command.charAt(i);
			if(character!=' ')
			{
				first_word += character;
				i++;
			}
		}
		return first_word.toUpperCase().trim();
	}
	
	public static boolean isUpdateComand (String command)
	{
		boolean isUpdateComand = false;
		String first_word = getFirstWord(command);
		if(first_word.equals("INSERT") || first_word.equals("UPDATE") || first_word.equals("DELETE") || first_word.equals("CREATE") || first_word.equals("DROP"))
		{
			isUpdateComand = true;
		}
		return isUpdateComand;
	}
	
	public static boolean isConsultComand (String command)
	{
		boolean isConsultComand = false;
		String first_word = getFirstWord(command);
		if(first_word.equals("SELECT"))
		{
			isConsultComand = true;
		}
		return isConsultComand;
	}

	public static String [][] resultSetToArray(ResultSet rs, int rows) throws SQLException
    {
		String [][] array = null;
        if(rows!=0)
        {
			ResultSetMetaData rsmd = rs.getMetaData();
	       
	        int numCols = rsmd.getColumnCount();
	        array = new String [rows+1][numCols];
	               
	        int j = 1;
	        while (rs.next()) 
	        {
	        	for(int i=1; i<=numCols; i++)
		        {
	        		array[0][i-1] = rsmd.getColumnName(i).toString();
		        }
	        	for(int i = 1; i<=numCols; i++)
	        	{
	        		array[j][i-1] = rs.getObject(i).toString();
	        	}
	        	j++;
	        }      
        }
        return array;
    }

	public static String getDateAndTime()
	{
		Calendar calendar = Calendar.getInstance();
		int hour = 			calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = 		calendar.get(Calendar.MINUTE);
		int seconds = 		calendar.get(Calendar.SECOND);
		int day = 			calendar.get(Calendar.DAY_OF_MONTH);
		int month = 		calendar.get(Calendar.MONTH)+1;
		int year = 			calendar.get(Calendar.YEAR);
		
		String day2 = 		dF(day);
		String month2 = 	dF(month);
		String year2 = 		dF(year);
		
		String hour2 =		dF(hour);
		String minutes2 = 	dF(minutes);
		String seconds2 = 	dF(seconds);
		
		return "("+day2+"/"+month2+"/"+year2+" - "+hour2+":"+minutes2+":"+seconds2+")";
	}
	
	private static String dF(int num)
	{
		if(num<10)
		{
			return "0"+num;
		}else{
			return ""+num;
		}
	}
}