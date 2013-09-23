package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Vector;

import utils.Properties;

/**
 * @author Jordan Aranda Tejada
 */
public class Server implements Runnable {

	private boolean			running;
	private ServerSocket	serverSocket;
	private Vector<Thread>	connectionsThreads;

	/**
	 * Creates the server
	 */
	public Server(final int PORT, final int backlog, final InetAddress IP)
	{
		try
		{
			serverSocket = new ServerSocket(PORT, backlog, IP);
			connectionsThreads = new Vector<Thread>();
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
		Thread serverThread = new Thread(
		new ServerThread(serverSocket.accept()));
		connectionsThreads.add(serverThread);
		serverThread.start();
		// System.out.println("New connection received from: "+connection.getInetAddress().toString()+" ("+connection.getInetAddress().getHostName()+")");
	}

	private void closeConnection()
	{
		try
		{
			serverSocket.close();
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
		Server server = null;
		try
		{
			server = new Server(Properties.getServerPORT(), 3000,
			InetAddress.getByName(Properties.getServerIP()));
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		Thread server_thread = new Thread(server);
		server_thread.run();
	}
}