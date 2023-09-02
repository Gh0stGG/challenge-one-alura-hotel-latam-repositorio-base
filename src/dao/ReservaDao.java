package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Reserva;

public class ReservaDao {

	private Connection con;

	public ReservaDao(Connection con) {
		super();
		this.con = con;
	}

	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, valor, forma_de_pago) "
					+ "VALUES (?,?,?,?)";

			try (PreparedStatement pStatement = con.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
				pStatement.setObject(1, reserva.getDateE());
				pStatement.setObject(2, reserva.getDateS());
				pStatement.setString(3, reserva.getValor());
				pStatement.setString(4, reserva.getFormaPago());
				pStatement.executeUpdate();
				System.out.println("Insercion correcta en las reservas");

				try (ResultSet resultS = pStatement.getGeneratedKeys()) {
					while (resultS.next()) {
						reserva.setId(resultS.getInt(1));
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("fallo al ingresar la reserva");
			throw new RuntimeException();
		}
	}
	//mostrar lista de reservas
	public List<Reserva> mostrar(){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas";
			
			try (PreparedStatement pStatement = con.prepareStatement(sql)){
				pStatement.execute();		
				System.out.println("solicitud de lista de reservas exitosa");
				transformarResultado(reservas, pStatement);
			}
			return reservas;
		}catch (SQLException e) {
			System.out.println("Error al solicitar la lista de reservas");
			throw new RuntimeException();
		}
	}
	
	//buscar por id
	public List<Reserva> buscarId(String id){
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas WHERE id=?";
			
			try (PreparedStatement pStatement = con.prepareStatement(sql)){
				pStatement.setString(1, id);
				pStatement.execute();		
				System.out.println("solicitud de lista de reservas exitosa");
				transformarResultado(reservas, pStatement);
			}
			return reservas;
		}catch (SQLException e) {
			System.out.println("Error al solicitar la lista de reservas");
			throw new RuntimeException();
		}
	}
	
	public void Actualizar(LocalDate dataE, LocalDate dataS, String valor, String formaPago, Integer id) {
		
		try(PreparedStatement pStatement= con.prepareStatement("UPDATE reservas SET "
				+ "fecha_entrada=?, fecha_salida=?, valor=?, forma_de_pago=? WHERE id=?")){
			pStatement.setObject(1, java.sql.Date.valueOf(dataE));
			pStatement.setObject(2, java.sql.Date.valueOf(dataS));
			pStatement.setString(3, valor);
			pStatement.setString(4, formaPago);
			pStatement.setInt(5, id);
			pStatement.execute();
			System.out.println("actualizacion exitosa de los datos");
		}catch(SQLException e) {
			System.out.println("hubo un error al actualizar la informacion");
			throw new RuntimeException("error en la actualizacion de datos");
		}
		
	}
	
	public void Eliminar(Integer id) {
		try {
			Statement fKey = con.createStatement();
			fKey.execute("SET FOREIGN_KEY_CHECKS=0");
			PreparedStatement pStatement = con.prepareStatement("DELETE FROM reservas WHERE id =?");
			pStatement.setInt(1, id);
			pStatement.execute();
			fKey.execute("SET FOREIGN_KEY_CHECKS=1");
			System.out.println("borrado exitoso");
		}catch(SQLException e) {
			System.out.println("error al borrar");
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	private void transformarResultado(List<Reserva> reservas, PreparedStatement pStatement) throws SQLException {
		try(ResultSet resultSet = pStatement.getResultSet()) {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				LocalDate fechaE = resultSet.getDate("fecha_entrada").toLocalDate().plusDays(1);
				LocalDate fechaS = resultSet.getDate("fecha_salida").toLocalDate().plusDays(1);
				String valor = resultSet.getString("valor");
				String formaPago = resultSet.getString("forma_de_pago");
				
				Reserva resultado = new Reserva(id, fechaE, fechaS, valor, formaPago);
				reservas.add(resultado);
			}
		}
	}

}
