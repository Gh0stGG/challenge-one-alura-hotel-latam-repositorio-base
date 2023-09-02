package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import modelo.Huespedes;

public class HuespedesDao {

	private Connection con;

	public HuespedesDao(Connection con) {
		
		this.con = con;
	}
	//guardar huespedes
	public void guardar(Huespedes huespedes) {
		
		try {
			String sql = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad"
					+ ", telefono, id_reserva) VALUES (?,?,?,?,?,?)"; 
			
			try (PreparedStatement pStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
				pStatement.setString(1, huespedes.getNombre());
				pStatement.setString(2, huespedes.getApellido());
				pStatement.setObject(3, huespedes.getFechaNacimiento());
				pStatement.setString(4, huespedes.getNacionalidad());
				pStatement.setString(5, huespedes.getTelefono());
				pStatement.setInt(6, huespedes.getIdReserva());
				pStatement.execute();
				System.out.println("Statement ejecutado correctamente ingresando datos de huesped");
				try(ResultSet resultSet = pStatement.getGeneratedKeys()) {
					while(resultSet.next()) {
						huespedes.setId(resultSet.getInt(1));
						System.out.println("Se ha seteado correctamente el id del huesped");
					}
				}				
			}
		}catch(SQLException e){
			System.out.println("Error al ejecutar el statement para ingresar los datos del huesped");
			throw new RuntimeException("fallo en: " + e.getMessage(),e);
		}
	}
	
	//listar huespedes
	public List<Huespedes> mostrar(){
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes";
			
			try (PreparedStatement pStatement = con.prepareStatement(sql)){
				pStatement.execute();		
				System.out.println("solicitud de lista de huespedes exitosa");
				transformarResultado(huespedes, pStatement);
			}
			return huespedes;
		}catch (SQLException e) {
			System.out.println("Error al solicitar la lista de huespedes");
			throw new RuntimeException();
		}
	}
	//buscar en la lista por id
	public List<Huespedes> buscarApellido(String apellido){ //ex-id
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, id_reserva FROM huespedes WHERE apellido=?"; // ex where id 
			
			try (PreparedStatement pStatement = con.prepareStatement(sql)){
				pStatement.setString(1, apellido); //ex-id
				pStatement.execute();		
				System.out.println("solicitud de lista de huespedes exitosa");
				transformarResultado(huespedes, pStatement);
			}
			return huespedes;
		}catch (SQLException e) {
			System.out.println("Error al solicitar la lista de huespedes");
			throw new RuntimeException( e.getMessage(), e);
		}
	}
	
	//se actualizan los vinculos de ambas tablas
	public void ActualizarH(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva, Integer id) {
		try (PreparedStatement pStatement = con.prepareStatement("UPDATE huespedes SET nombre=?, apellido=?, fecha_nacimiento=?, nacionalidad=?,"
				+ " telefono=?, id_reserva=? WHERE id=?")){
			
			pStatement.setString(1, nombre);
			pStatement.setString(2, apellido);
			pStatement.setObject(3, fechaNacimiento);
			pStatement.setString(4, nacionalidad);
			pStatement.setString(5, telefono);
			pStatement.setInt(6, idReserva);
			pStatement.setInt(7, id);
			pStatement.execute();
			System.out.println("exito al actualizar la lista de huespedes");
			
		}catch(SQLException e) {
			System.out.println("error al actualizar los huespedes");
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public void Eliminar(Integer id) {
		try(PreparedStatement pStatement = con.prepareStatement("DELETE FROM huespedes WHERE id=?")){
			pStatement.setInt(1, id);
			pStatement.execute();
			System.out.println("exito al eliminar el huesped");
			
		}catch(SQLException e) {
			System.out.println("error al eliminar los huespedes");
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	
	
	 
	//metodo para transformar la informacion de la tabla de datos de huespedes
	private void transformarResultado(List<Huespedes> huespedes, PreparedStatement pStatement) throws SQLException {
		try(ResultSet resultSet = pStatement.getResultSet()) {
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				String apellido = resultSet.getString("apellido");
				LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate().plusDays(1);
				String nacionalidad = resultSet.getString("nacionalidad");
				String telefono = resultSet.getString("telefono");
				int idReserva = resultSet.getInt("id_reserva");
				
				Huespedes huesped = new Huespedes(id, nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva);
				huespedes.add(huesped);
				System.out.println("datos transformados exitosamente desde la base de datos");
			}
		}
	}
	
}
