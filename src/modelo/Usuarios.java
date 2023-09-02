package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class Usuarios {

	private String nombre;
	private String contrasena;

	public Usuarios(String nombre, String contrasena) {
		this.nombre = nombre;
		this.contrasena = contrasena;
	}

	public static boolean validarUsuario(String nombre, String contrasena) {
		Connection con = new ConnectionFactory().recuperaConexion();
		Connection connec = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connec = con;
			statement = connec.prepareStatement("SELECT * FROM usuarios WHERE nombre=? AND contrasena=?");
			statement.setString(1, nombre);
			statement.setString(2, contrasena);
			resultSet = statement.executeQuery();
			System.out.println("coneccion exitosa con la tabla de usuarios");
			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("fallo en conexion con tabla de usuarios");
			return false;
		} finally {
			try {
				if(resultSet !=null)
					resultSet.close();
				if(statement !=null)
					statement.close();
				if(connec !=null)
					connec.close();
				System.out.println("todas las conexiones de la tabla de usuario se han cerrado");
			}catch(SQLException e2) {
				e2.printStackTrace();
				System.out.println("no se han podido cerrar las conexiones puede que ya se hayan cerrado o no se logro conectar");
			}
		}
	}

	//getters and setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
