package database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utilities.FileUtils;
import utils.Properties;

/**
 * @author Jordan Aranda Tejada
 */
public class DataBase {

	private static DataBase	instance;
	private Connection		connection;

	private DataBase()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:"
			+ Properties.getDataBasePath());
			this.update("PRAGMA encoding = \"UTF-8\";");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		if (numberOfTables() == 0)
		{
			create_tables();
		}
	}

	public void disconnect()
	{
		try
		{
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void create_tables()
	{
		try
		{
			this.update(FileUtils.toString("data/createDB.sql"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		(new File("createDB.sql")).delete();
	}

	private Statement create_statement()
	{
		Statement statement = null;
		try
		{
			statement = this.connection.createStatement();
		}
		catch (SQLException e)
		{
			System.out.println("Error creating statement.");
		}
		return statement;
	}

	public ResultSet consult(String consult)
	{
		Statement statement = this.create_statement();
		try
		{
			return statement.executeQuery(consult);
		}
		catch (SQLException e)
		{
			System.out.println("Error consulting database.");
			return null;
		}
	}

	public int update(String consult)
	{
		Statement statement = this.create_statement();
		try
		{
			return statement.executeUpdate(consult);
		}
		catch (SQLException e)
		{
			System.out.println("Error updating database.\n " + e.toString());
			return 0;
		}
	}

	public int count(String table, String condition)
	{
		int number = 0;
		String where = condition == null ? "" : " WHERE " + condition;
		String consult = "SELECT COUNT(*) as number FROM " + table + where
		+ ";";
		ResultSet result = consult(consult);
		try
		{
			while (result.next())
			{
				number = result.getInt("number");
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error counting tables.");
		}
		return number;
	}

	private int numberOfTables()
	{
		return count("sqlite_master", "type='table'");
	}

	public boolean exist(String table, String condition)
	{
		boolean enc = false;
		String where = condition == null ? "" : " WHERE " + condition;
		String consult = "SELECT * FROM " + table + where + ";";
		ResultSet result = consult(consult);
		try
		{
			while (result.next())
			{
				enc = true;
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error checking table.");
		}
		return enc;
	}

	public int getFirstIdAvailable(String table, String columnName,
	String condition)
	{
		int id = 1;
		boolean enc = false;
		String where = condition == null ? "" : " WHERE " + condition;
		ResultSet rs = DataBase.getInstance().consult(
		"SELECT " + columnName + " FROM " + table + where + ";");
		try
		{
			while (rs.next() && ! enc)
			{
				if (id != rs.getInt(columnName))
				{
					enc = true;
				}
				else
				{
					id++;
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * @return instance of database
	 */
	public static DataBase getInstance()
	{
		if (instance == null)
		{
			instance = new DataBase();
		}
		return instance;
	}

	/**
	 * @param args Arguments
	 */
	public static void main(String[] args)
	{
		DataBase.getInstance().update("DELETE FROM USERS WHERE ID=2");
	}
}