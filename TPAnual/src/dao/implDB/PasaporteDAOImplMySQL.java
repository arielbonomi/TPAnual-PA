package dao.implDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import dao.Interfaces.PasaporteDAO;
import dao.negocio.Pasaporte;
import dao.negocio.Provincia;
import dao.util.ConexionMySQL;

public class PasaporteDAOImplMySQL implements PasaporteDAO{
	
	ConexionMySQL sql = new ConexionMySQL();
	
	final String add = "INSERT INTO prog_avanzada.pasaporte (numero_pasaporte,autoridad_emision,fecha_emision,fecha_vencimiento) VALUES(?,?,?,?)";
	final String delete = "DELETE FROM prog_avanzada.pasaporte WHERE id_ventas = ?";
	final String update = "UPDATE prog_avanzada.pasaporte set id_ventas = ? , fecha_hora_venta = ?, forma_pago = ? WHERE id_ventas = ? ";
	final String ListAll = "SELECT * FROM prog_avanzada.pasaporte";
    final String get = "SELECT * FROM prog_avanzada.pasaporte WHERE id_ventas = ?";
    
	@Override
	public void addPasaporte(Pasaporte pasaporte) {
		
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {		
			conexion = sql.getConnection();
			ps = conexion.prepareStatement(add);
			ps.setString(1, pasaporte.getNumero());
			ps.setString(2, pasaporte.getAutoridadEmision());
			ps.setString(3, pasaporte.getFechaEmision());
			ps.setString(4, pasaporte.getFechaVencimiento());
			ps.executeUpdate();	
					} 
			catch (SQLException e) { e.printStackTrace();}
		finally {	
			try {ps.close();conexion.close();}
			catch(Exception e) {e.printStackTrace();}
		}
		
	}
	@Override
	public void deletePasaporte(String numero_pasaporte) {
		
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {
		conexion = sql.getConnection();
		ps = conexion.prepareCall(delete);
		ps.setString(1, numero_pasaporte);
		ps.executeUpdate();	
		conexion.close();
	} 
		catch (SQLException e) {e.printStackTrace();}
	}
	
	@Override
	public void updatePasaporte(Pasaporte pasaporte) {
		
		Connection conexion = null;
		PreparedStatement ps = null;
		try {
			conexion = sql.getConnection();
			ps = conexion.prepareCall(update);
			ps.setString(1, pasaporte.getNumero());
			ps.setString(2, pasaporte.getAutoridadEmision());
			ps.setString(3, pasaporte.getFechaEmision());
			ps.setString(4, pasaporte.getFechaVencimiento());
			
	     	ps.executeUpdate();
			conexion.close();
			} catch (SQLException e) {e.printStackTrace();}	
		}
		
	@Override
	public List<Pasaporte> ListAllPasaporte() {
		
		Connection conexion = null;
		 PreparedStatement ps = null;
		 List<Pasaporte> lista= new ArrayList<>();
		 try {
		 conexion = sql.getConnection();
	     ps = conexion.prepareStatement(ListAll);
		 ResultSet rs = ps.executeQuery();    
		 while(rs.next()) {
			 
		String numero = (rs.getString("nombre_pasaporte"));
		String autoridadEmision = (rs.getString("autoridad_emision"));
		String fecha_emision = (rs.getString("fecha_emision"));
		String fecha_vencimiento = (rs.getString("fecha_vencimiento"));
		Pasaporte pasaporte = new Pasaporte(numero,autoridadEmision,fecha_emision,fecha_vencimiento,null);
		lista.add(pasaporte);
	     }
			conexion.close();
					
	} catch (SQLException e) {e.printStackTrace();}
			return lista;	
	}
	
	@Override
	public Pasaporte getPasaporte(String numero_pasaporte) {
		
		Connection conexion = null;
	    PreparedStatement ps = null;	    
		try {	 
		conexion = sql.getConnection();
	    ps = conexion.prepareStatement(get);
	    ps.setString(1, numero_pasaporte);
	    ResultSet rs = ps.executeQuery();
		while(rs.next()) {
	
	    String numero = (rs.getString("nombre_pasaporte"));
	    String autoridadEmision = (rs.getString("autoridad_emision"));
	    String fecha_emision = (rs.getString("fecha_emision"));
	    String fecha_vencimiento = (rs.getString("fecha_vencimiento"));

		Pasaporte pasaporte = new Pasaporte(numero,autoridadEmision,fecha_emision,fecha_vencimiento,null);
		return pasaporte;	
	}
	conexion.close();
	} catch (SQLException e) {e.printStackTrace();}
	return null;
	}

	

}
