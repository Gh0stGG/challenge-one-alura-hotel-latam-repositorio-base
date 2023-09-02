package factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory{

	private DataSource datasource; //se define como variable un pool de conexiones
	
	//metodo que genera las conexiones
	public ConnectionFactory() {
		ComboPooledDataSource pooledDataSource = new ComboPooledDataSource();  //variable local tipo ComboPooledDataSource para instanciar la misma pool
		pooledDataSource.setJdbcUrl("your jdbc database url"); //url del servidor
		pooledDataSource.setUser("your user"); //username
		pooledDataSource.setPassword("your password"); //password
		pooledDataSource.setMaxPoolSize(10); //conexiones maximas
		
		this.datasource = pooledDataSource; //este datasource pertenece al variable private datasource
	}
	
	//metodo de conexion
	public Connection recuperaConexion() {
		try { //intenta conectar con la database
			System.out.println("Conexion exitosa a la base de datos");
			return this.datasource.getConnection(); //retorna la informacion de la base de datos y se prepara para realizar statements en sq
			
		} catch (SQLException e) { //atrapa cualquier fallo en la conexion
			System.out.println("ERROR EN CONEXION"); // imprime un aviso informando el fallo
			throw new RuntimeException(); //lanza la excepcion de la maquina virtual de java
		}
	}

}
