package entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import utils.Properties;
import utils.StringUtils;
import utils.Utilities;
import database.DataBase;

/**
 * @author Jordan Aranda Tejada
 */

public class User 
{
	private static User			user;
	
	private int 				id;
	private String 				name;
	private String 				email;
	private String 				password;
	private String				securityQuestion;
	private String				securityAnswer;
	private boolean				sendEmailUpdates;
	private Date				firstDate;
	private Date 				lastDate;
	private boolean 			connected;
	private boolean 			blocked;
	
	private User(final int id, final String name, final String email, final String password, 
			final String securityQuestion, final String securityAnswer, final boolean sendEmailUpdates, 
			final Date firstDate, final Date lastDate, final boolean connected, final boolean blocked)
	{
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
		this.sendEmailUpdates = sendEmailUpdates;
		this.firstDate = firstDate;
		this.lastDate = lastDate;
		this.connected = connected;
		this.blocked = blocked;
	}
	

	public static User createUser(final String name, final String email, final String password, 
			final String securityQuestion, final String securityAnswer, final boolean sendEmailUpdates)
	{
		int idUser = DataBase.getInstance().getFirstIdAvailable(Properties.getTableUsersName(), "ID", null);		
		if(sendEmailUpdates)
		{
			DataBase.getInstance().update("INSERT INTO "+Properties.getTableUsersName()+" VALUES ("+idUser+", '"+name+"', '"+email+"', " +
					"'"+StringUtils.sha1(password)+"', '"+securityQuestion+"', '"+securityAnswer+"', 1, " +
					""+Utilities.getCurrentDate().getTime()+", "+Utilities.getCurrentDate().getTime()+", 0, 0);");
		} else {
			DataBase.getInstance().update("INSERT INTO "+Properties.getTableUsersName()+" VALUES ("+idUser+", '"+name+"', '"+email+"', " +
					"'"+StringUtils.sha1(password)+"', '"+securityQuestion+"', '"+securityAnswer+"', 0, " +
					""+Utilities.getCurrentDate().getTime()+", "+Utilities.getCurrentDate().getTime()+", 0, 0);");			
		}
		return user;
	}

	
	public static void loadUser(final String name, final String password)
	{
		ResultSet rs = DataBase.getInstance().consult("SELECT * FROM "+Properties.getTableUsersName()+" WHERE NAME='"+name+"' " +
				"AND PASSWORD='"+StringUtils.sha1(password)+"'");
		try {
			while(rs.next())
			{
				int id = rs.getInt("ID");
				String email = rs.getString("EMAIL");
				String securityQuestion = rs.getString("SECURITY_QUESTION");
				String securityAnswer = rs.getString("SECURITY_ANSWER");
				Date firstDate = new Date(rs.getInt("FIRST_DATE"));
				boolean sendEmailUpdates = false;
				if(rs.getInt("SEND_EMAIL_UPDATES") == 1)
				{
					sendEmailUpdates = true;
				}
				boolean blocked = false;
				if(rs.getInt("BLOCKED") == 1)
				{
					blocked = true;
				}
				DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET LAST_DATE = "+Utilities.getCurrentDate().getTime()+", CONNECTED=1 WHERE ID = "+id);
				user = new User(id, name, email, StringUtils.sha1(password), securityQuestion, securityAnswer, sendEmailUpdates,
						firstDate, Utilities.getCurrentDate(), true, blocked);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void save()
	{
		if(sendEmailUpdates)
		{
			DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET NAME= '"+name+"', EMAIL='"+email+"', " +
					"PASSWORD='"+StringUtils.sha1(password)+"', SEND_EMAILS_UPDATES=1, " +
					"LAST_DATE="+lastDate.getTime()+" WHERE ID = "+id);
		} else {
			DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET NAME= '"+name+"', EMAIL='"+email+"', " +
					"PASSWORD='"+StringUtils.sha1(password)+"', SEND_EMAILS_UPDATES=0, " +
					"LAST_DATE="+lastDate.getTime()+" WHERE ID = "+id);
		}
	}

	
	public void disconnect()
	{
		connected = false;
		DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET CONNECTED=0 WHERE ID = "+id);
	}
	
	
	public void block()
	{
		DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET BLOCKED=1 WHERE ID = "+id);
		DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET CONNECTED=0 WHERE ID = "+id);
	}
	
	
	public void unlock()
	{
		DataBase.getInstance().update("UPDATE "+Properties.getTableUsersName()+" SET BLOCKED=0 WHERE ID = "+id);
	}
	
	
	public void delete()
	{
		DataBase.getInstance().update("DELETE FROM "+Properties.getTableUsersName()+" WHERE ID="+id);
	}
	
	
	public static boolean exist (final String name)
	{
		return DataBase.getInstance().exist(Properties.getTableUsersName(), "NAME='"+name+"'");
	}
	
	
	public static Vector<User> getUsersVector()
	{
		Vector<User> users = new Vector <User> ();
		ResultSet rs = DataBase.getInstance().consult("SELECT * FROM "+Properties.getTableUsersName());
		try {
			while(rs.next())
			{
				User u = new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), 
						rs.getString("SECURITY_QUESTION"), rs.getString("SECURITY_ANSWER"), false, 
						new Date(rs.getLong("FIRST_DATE")), new Date(rs.getLong("LAST_DATE")), false, false);
				
				if(rs.getInt("SEND_EMAILS_UPDATES")==1)
				{
					u.setSendEmailUpdates(true);
				} 
				if(rs.getInt("CONNECTED") == 1)
				{
					u.setConnected(true);
				} 
				if(rs.getInt("BLOCKED") == 1)
				{
					u.setBlocked(true);
				} 
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public static Vector<String> getConnectedUsersNamesVector()
	{
		Vector<String> users = new Vector <String> ();
		ResultSet rs = DataBase.getInstance().consult("SELECT * FROM "+Properties.getTableUsersName()+" WHERE CONNECTED=1");
		try {
			while(rs.next())
			{
				User u = new User(rs.getInt("ID"), rs.getString("NAME"), rs.getString("EMAIL"), rs.getString("PASSWORD"), 
						rs.getString("SECURITY_QUESTION"), rs.getString("SECURITY_ANSWER"), false, 
						new Date(rs.getLong("FIRST_DATE")), new Date(rs.getLong("LAST_DATE")), false, false);
				
				users.add(u.getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	public static int getConnected()
	{
		return DataBase.getInstance().count("USERS", "CONNECTED=1");
	}
	

	public static User getUser() {
		return user;
	}


	public static void setUser(User user) {
		User.user = user;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getSecurityQuestion() {
		return securityQuestion;
	}


	public void setSecurityQuestion(String securityQuestion) {
		this.securityQuestion = securityQuestion;
	}


	public String getSecurityAnswer() {
		return securityAnswer;
	}


	public void setSecurityAnswer(String securityAnswer) {
		this.securityAnswer = securityAnswer;
	}


	public boolean isSendEmailUpdates() {
		return sendEmailUpdates;
	}


	public void setSendEmailUpdates(boolean sendEmailUpdates) {
		this.sendEmailUpdates = sendEmailUpdates;
	}


	public Date getFirstDate() {
		return firstDate;
	}


	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}


	public Date getLastDate() {
		return lastDate;
	}


	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}


	public boolean isBlocked() {
		return blocked;
	}


	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isConnected() {
		return connected;
	}
	
	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public static void main (String [] args)
	{
		System.out.println("INICIO");
		createUser("Jordan", "jordan.aranda@me.com", "1234", "Hola?", "Adios!", true);
		createUser("Paula", "paula.pereda9@gmail.com", "1234", "Hola?", "Adios!", false);
		System.out.println("FIN");
	}
}