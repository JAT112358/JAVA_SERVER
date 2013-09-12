package entities;

import java.io.Serializable;

/**
 * @author Jordan Aranda Tejada
 */

public class Request implements Serializable
{
	private static final long 	serialVersionUID = -3957233478861491515L;
	private String 				usuario;
	private String 				peticion;
	private String 				informacion;
	
	public Request()
	{
		
	}

	public Request(String usuario, String peticion, String informacion) 
	{
		this.usuario = usuario;
		this.peticion = peticion;
		this.informacion = informacion;
	}

	public String getUsuario() 
	{
		return usuario;
	}

	public void setUsuario(String usuario) 
	{
		this.usuario = usuario;
	}

	public String getPeticion() 
	{
		return peticion;
	}

	public void setPeticion(String peticion) 
	{
		this.peticion = peticion;
	}

	public String getInformacion() 
	{
		return informacion;
	}

	public void setInformacion(String informacion) 
	{
		this.informacion = informacion;
	}
}