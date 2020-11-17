package dao.implDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import dao.Interfaces.DireccionDAO;
import dao.negocio.Direccion;
import dao.negocio.Vuelo;
import dao.util.ConexionMySQL;

public class DireccionDaoImplMysql implements DireccionDAO{
	
	ConexionMySQL sql = new ConexionMySQL();
	final String add = "INSERT INTO prog_avanzada.direccion (altura, calle, ciudad, codigo_postal) VALUES(?,?,?,?)";
	final String delete = "DELETE FROM prog_avanzada.direccion WHERE id_direccion = ?";
	final String update = "UPDATE prog_avanzada.direccion set altura = ?, calle = ?, ciudad = ? , codigo_postal = ? WHERE id_direccion = ? ";
	final String ListAll = "SELECT * FROM prog_avanzada.direccion";
	final String get = "SELECT * FROM prog_avanzada.direccion WHERE id_direccion = ?";
	final static String OBTENERULTIMO = "SELECT * FROM prog_avanzada.direccion ORDER BY id_direccion DESC LIMIT 1";
	

	@Override
	public void altaDireccion(Direccion direccion) {
	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {		
			conexion = sql.getConnection();
			ps = conexion.prepareStatement(add);
			ps.setString(1, direccion.getAltura());
			ps.setString(2, direccion.getCalle());
			ps.setString(3, direccion.getCiudad());
			ps.setString(4, direccion.getCodigoPostal());
			ps.executeUpdate();	
		} 
			catch (SQLException e) { e.printStackTrace();}
			finally {	
			try {ps.close();conexion.close();}
			catch(Exception e) {e.printStackTrace();}
		}
	}

	@Override
	public void bajaDireccion(String id_direccion) {
		
		Connection conexion = null;
		PreparedStatement ps = null;
		try {
		conexion = sql.getConnection();
		ps = conexion.prepareCall(delete);
		ps.setString(1, id_direccion);
		ps.executeUpdate();	
		conexion.close();
	} 
		catch (SQLException e) {e.printStackTrace();}
	}
		
	@Override
	public void modificacionDireccion(Direccion direccion) {
	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {
			conexion = sql.getConnection();
			ps = conexion.prepareCall(update);
			ps.setString(1, direccion.getAltura());
			ps.setString(2, direccion.getCalle());
			ps.setString(3, direccion.getCiudad());
			ps.setString(4, direccion.getCodigoPostal());
	     	ps.executeUpdate();
			conexion.close();
			} catch (SQLException e) {e.printStackTrace();}	
		}
	
	@Override
	public List<Direccion> ListAllDireccion() {
		Connection conexion = null;
		 PreparedStatement ps = null;
		 List<Direccion> lista= new ArrayList<>();
		 try {
		 conexion = sql.getConnection();
	     ps = conexion.prepareStatement(ListAll);
		 ResultSet rs = ps.executeQuery();    
		 while(rs.next()) {
		  String altura = (rs.getString(("altura")));
		  String calle = (rs.getString(("calle")));
		  String ciudad = (rs.getString(("ciudad")));
		  String codigo_postal = (rs.getString(("codigo_postal")));
		  Direccion direccion = new Direccion(altura, calle, ciudad, codigo_postal,null,null);
		  lista.add(direccion);
	     }
		  conexion.close();
					
	} catch (SQLException e) {e.printStackTrace();}
			return lista;	
	}
	
	@Override
	public Direccion getDireccion(String id_direccion) {
		
		Connection conexion = null;
	    PreparedStatement ps = null;	    
		try {	 
		conexion = sql.getConnection();
	    ps = conexion.prepareStatement(get);
	    ps.setString(1, id_direccion);
	    ResultSet rs = ps.executeQuery();
			    
		while(rs.next()) {
		
		String altura = (rs.getString(("altura")));
		String calle = (rs.getString(("calle")));
		String ciudad = (rs.getString(("ciudad")));
		String codigo_postal = (rs.getString(("codigo_postal")));
		Direccion direccion = new Direccion(altura, calle, ciudad, codigo_postal,null,null);
		return direccion;
	}
	conexion.close();
	} catch (SQLException e) {e.printStackTrace();}
	return null;}

	@Override
	public Direccion obtenerUltimo() {
		Connection conexion = null;
	    PreparedStatement ps = null;	    
		try {	 
		conexion = sql.getConnection();
	    ps = conexion.prepareStatement(OBTENERULTIMO);
	    ResultSet rs = ps.executeQuery();
			    
		while(rs.next()) {
		String id = rs.getString("id_direccion");
		String altura = (rs.getString(("altura")));
		String calle = (rs.getString(("calle")));
		String ciudad = (rs.getString(("ciudad")));
		String codigo_postal = (rs.getString(("codigo_postal")));
		Direccion direccion = new Direccion(Integer.parseInt(id),altura, calle, ciudad, codigo_postal,null,null);
		return direccion;
	}
	conexion.close();
	} catch (SQLException e) {e.printStackTrace();}
	return null;
	}
}
