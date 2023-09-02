package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import factory.ConnectionFactory;

public class test1 {

	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		try (PreparedStatement stmt = con.prepareStatement("SELECT nombre FROM usuarios")) {
			  ResultSet rs = stmt.executeQuery();
			  System.out.println("test y comando sql ejecutados exitosamente");

			  while (rs.next())
			    System.out.println (rs.getString("nombre"));

			} catch (SQLException sqle) { 
			  System.out.println("Error en la ejecución:" 
			    + sqle.getErrorCode() + " " + sqle.getMessage());    
			}
	}

	}

