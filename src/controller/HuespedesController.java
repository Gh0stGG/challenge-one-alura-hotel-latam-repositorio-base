package controller;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import dao.HuespedesDao;
import factory.ConnectionFactory;
import modelo.Huespedes;

public class HuespedesController {

	private HuespedesDao huespedesDAO;
	
	public HuespedesController() {
		Connection con = new ConnectionFactory().recuperaConexion();
		this.huespedesDAO = new HuespedesDao(con);
	}
	
	public void guardar(Huespedes huespedes) {
		this.huespedesDAO.guardar(huespedes);
	}
	
	public List<Huespedes> mostrarHuesped(){
		return this.huespedesDAO.mostrar();
	}
	
	public List<Huespedes> buscarHuesped(String apellido){ //ex-id
		return this.huespedesDAO.buscarApellido(apellido); //ex-id
	}
	
	public void actualizarH(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva, Integer id) {
		this.huespedesDAO.ActualizarH(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id); 
	}
	
	public void Eliminar(Integer idReserva) {
		this.huespedesDAO.Eliminar(idReserva);
	}
}
