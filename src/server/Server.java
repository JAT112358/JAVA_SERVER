package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import utils.Properties;
import database.DataBase;
import entities.Request;

/**
 * @author Jordan Aranda Tejada
 */
public class Server implements Runnable {

	private boolean				running;
	private ServerSocket		serverSocket;
	private Socket				connection;
	private ObjectOutputStream	output;
	private ObjectInputStream	input;
	private long				connections;

	/**
	 * Creates the server
	 */
	public Server()
	{
		this.connections = 0;
		try
		{
			serverSocket = new ServerSocket(Properties.getServerPORT(), 3000,
			InetAddress.getByName(Properties.getServerIP()));
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("No se ha establecido la conexión");
			e.printStackTrace();
		}
	}

	private void waitConnections() throws IOException
	{
		connection = serverSocket.accept();
		// System.out.println("New connection received from: "+connection.getInetAddress().toString()+" ("+connection.getInetAddress().getHostName()+")");
		connections++;
	}

	// Obtenemos los flujos de la conexión
	private void getStreams() throws IOException
	{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}

	private void processConnection()
	{
		Request request = (Request) obtainInputObject();
		String command = (String) obtainInputObject();
		if (ServerMetods.isUpdateComand(command))
		{
			sendData(DataBase.getInstance().update(command));
		}
		else if (ServerMetods.isConsultComand(command))
		{
			String[][] array = null;
			// try
			// {
			// array =
			// ServerMetods.resultSetToArray(DataBase.getInstance().consult(command),
			// DataBase.getInstance().countConsult(command));
			// sendData(array);
			// }
			// catch (SQLException e)
			// {
			// e.printStackTrace();
			// }
		}
	}

	private Object obtainInputObject()
	{
		Object object = null;
		try
		{
			object = input.readObject();
		}
		catch (ClassNotFoundException excepcionClaseNoEncontrada)
		{
			JOptionPane
			.showMessageDialog(null, "Unknown type object receibed.", "Error",
			JOptionPane.ERROR_MESSAGE);
		}
		catch (IOException e)
		{
			closeConnection();
			JOptionPane.showMessageDialog(null,
			"The connection with the server failed.", "Connection failed.",
			JOptionPane.ERROR_MESSAGE);
		}
		return object;
	}

	public void sendData(Object object)
	{
		try
		{
			output.writeObject(object);
			output.flush();
		}
		catch (IOException excepcionES)
		{
			JOptionPane.showMessageDialog(null, "Error sending object.",
			"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void closeConnection()
	{
		try
		{
			output.close();
			input.close();
			connection.close();
		}
		catch (IOException excepcionES)
		{
			excepcionES.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while (running)
		{

			try
			{
				waitConnections();
				getStreams();
				processConnection();
			}
			catch (EOFException excepcionEOF)
			{
				JOptionPane.showMessageDialog(null,
				"The server finished the connection.", "Connection finished",
				JOptionPane.INFORMATION_MESSAGE);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				closeConnection();
			}

		}
	}

	/**
	 * @param args Arguments
	 */
	public static void main(String ... args)
	{
		Server server = new Server();
		Thread server_thread = new Thread(server);
		server_thread.run();
	}
}