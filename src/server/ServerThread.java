package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author Jordan Aranda Tejada
 */
public class ServerThread implements Runnable {

	private Socket				socket;
	private ObjectOutputStream	output;
	private ObjectInputStream	input;

	/**
	 * @param socket The socket
	 */
	public ServerThread(Socket socket)
	{
		this.socket = socket;
		InicializaStreams();
	}

	private void InicializaStreams()
	{
		try
		{
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private Object readObject()
	{
		Object object = null;
		try
		{
			object = input.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return object;
	}

	private void writeObject(Object object)
	{
		try
		{
			output.writeObject(object);
			output.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void closeConnection()
	{
		try
		{
			output.close();
			input.close();
			socket.close();
		}
		catch (IOException excepcionES)
		{
			excepcionES.printStackTrace();
		}
	}

	@Override
	public void run()
	{

	}
}
