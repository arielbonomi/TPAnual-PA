package dao.implDB;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.Interfaces.ClienteDAO;
import dao.negocio.Cliente;
import dao.negocio.Vuelo;
import dao.util.ConexionMySQL;

public class ClienteDAOImplMySQL implements ClienteDAO {

	
	ConexionMySQL sql = new ConexionMySQL();
	final String add = "INSERT INTO prog_avanzada.cliente (nombre, apellido, dni, fecha_de_nacimiento, cuit_cuil, email) VALUES(?,?,?,?,?,?)";
	final String delete = "DELETE FROM prog_avanzada.cliente WHERE dni = ?";
	final String update = "UPDATE prog_avanzada.cliente set nombre = ?, apellido = ?, dni = ? , fecha_hora_nacimiento = ?, cuit_cuil = ?, email = ? WHERE dni = ? ";
	final String ListAll = "SELECT * FROM prog_avanzada.cliente";
	final String get = "SELECT * FROM prog_avanzada.cliente WHERE dni = ?";

	
	@Override
	public void altaCliente(Cliente cliente) {
		
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {		
			conexion = sql.getConnection();
			ps = conexion.prepareStatement(add);
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getApellido());
			ps.setString(3, cliente.getDni());
			ps.setString(4, cliente.getFecha_nacimiento());
			ps.setString(5, cliente.getCuit_cuil());
			ps.setString(6, cliente.getEmail());

			ps.executeUpdate();	
					} 
			catch (SQLException e) { e.printStackTrace();}
			finally {	
			try {ps.close();conexion.close();}
			catch(Exception e) {e.printStackTrace();}
		}
		
	}

	@Override
	public void bajaCliente(String dni) {
	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {
		conexion = sql.getConnection();
		ps = conexion.prepareCall(delete);
		ps.setString(1, dni);
		ps.executeUpdate();	
		conexion.close();
	} 
		catch (SQLException e) {e.printStackTrace();}
	}
	

	@Override
	public void modificarCliente(Cliente cliente) {
	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {
			conexion = sql.getConnection();
			ps = conexion.prepareCall(update);
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getApellido());
			ps.setString(3, cliente.getDni());
			ps.setString(4, cliente.getFecha_nacimiento());
			ps.setString(5, cliente.getCuit_cuil());
			ps.setString(6, cliente.getEmail());

	     	ps.executeUpdate();
			conexion.close();
			} catch (SQLException e) {e.printStackTrace();}	
		}
	
	@Override
	public Cliente getCliente(String dni) {
		
		Connection conexion = null;
	    PreparedStatement ps = null;	    
		try {	 
		conexion = sql.getConnection();
	    ps = conexion.prepareStatement(get);
	    ps.setString(1, dni);
	    ResultSet rs = ps.executeQuery();
			    
		while(rs.next()) {
			 
		String nombre = (rs.getString(("nombre")));
	    String apellido = (rs.getString(("apellido")));
		String fecha_nacimiento = (rs.getString("fecha_de_nacimiento"));
		String cuit_cuil = rs.getString("cuit_cuil");
		String email = rs.getString("email");
		
		Cliente cliente = new Cliente(nombre,apellido,dni,fecha_nacimiento,cuit_cuil,email,null,null,null,null);
		return cliente;
		
	}
	conexion.close();
	} catch (SQLException e) {e.printStackTrace();}
	return null;}
	

	@Override
	public List<Cliente> ListAllCliente() {
		Connection conexion = null;
		 PreparedStatement ps = null;
		 List<Cliente> lista= new ArrayList<>();
		 try {
		 conexion = sql.getConnection();
	     ps = conexion.prepareStatement(ListAll);
		 ResultSet rs = ps.executeQuery();    
		 while(rs.next()) {
			 
		String nombre = (rs.getString(("nombre")));
		String apellido = (rs.getString(("apellido")));
		String dni = (rs.getString("dni"));
		String fecha_nacimiento = (rs.getString("fecha_de_nacimiento"));
		String cuit_cuil = rs.getString("cuit_cuil");
		String email = rs.getString("email");
				
		Cliente cliente = new Cliente(nombre,apellido,dni,fecha_nacimiento,cuit_cuil,email,null,null,null,null);
		lista.add(cliente);
	     }
			conexion.close();
					
	} catch (SQLException e) {e.printStackTrace();}
			return lista;	
	}
		

}