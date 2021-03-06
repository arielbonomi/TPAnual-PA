package dao.implDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Factory.Factory;
import dao.Interfaces.*;
import dao.negocio.*;
import dao.util.ConexionMySQL;

public class VentasDAOImplMySQL implements VentasDAO{
	
//Conexión a mysql    
	ConexionMySQL sql = new ConexionMySQL();    
	
//Statements    
    final String add = "INSERT INTO prog_avanzada.ventas (fecha_hora_venta, forma_pago, id_cliente, id_vuelo, id_aerolinea) VALUES(?,?,?,?,?)";
	final String delete = "DELETE FROM prog_avanzada.ventas WHERE id_ventas = ?";
	final String update = "UPDATE prog_avanzada.ventas set fecha_hora_venta = ?, forma_pago = ?, id_cliente=?, id_vuelo=?, id_aerolinea=? WHERE id_ventas = ? ";
	final String ListALL = "SELECT * FROM prog_avanzada.ventas";
	final String get = "SELECT * FROM prog_avanzada.ventas WHERE id_ventas = ?";
	final String OBTENERIDS = "SELECT id_ventas FROM prog_avanzada.ventas";
	final String OBTENERULTIMA = "SELECT * FROM ventas ORDER BY id_ventas DESC LIMIT 1";
	  
	@Override
	public boolean altaVenta(Venta venta) {
		
	//Realizo la conexión	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {		
			conexion = sql.getConnection();
			ps = conexion.prepareStatement(add);
			
		//Seteo los parámetros	
			ps.setString(1, venta.getFecha());
			ps.setString(2, venta.getFormaDePago());
			ps.setInt(3, venta.getCliente().getId_cliente());
			ps.setInt(4, venta.getVuelo().getId_Vuelo());
			ps.setInt(5, venta.getAerolinea().getId_aeroLinea());
			ps.executeUpdate();	
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace(); 
			return false;
		}
		
	//Cierro la conexión	
		finally {	
			try {ps.close();conexion.close();}
			catch(Exception e) {e.printStackTrace();}
		}
		
	}
	@Override
	public boolean bajaVenta(String id_venta) {
		
	//Realizo la conexión	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {
			conexion = sql.getConnection();
			ps = conexion.prepareCall(delete);
			
		//Seteo los parámetros	
			ps.setString(1, id_venta);
			ps.executeUpdate();	
			
		//Cierro la conexión	
			conexion.close();
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace(); 
			return false;
		}
	}
	
	@Override
	public boolean modificarVenta(Venta venta) {
	
	//Realizo la conexión	
		Connection conexion = null;
		PreparedStatement ps = null;
		
		try {
			conexion = sql.getConnection();
			ps = conexion.prepareCall(update);
			
		//Seteo los parámetros	
			ps.setString(1, venta.getFecha());
			ps.setString(2, venta.getFormaDePago());
			ps.setInt(3, venta.getCliente().getId_cliente());
			ps.setInt(4, venta.getVuelo().getId_Vuelo());
			ps.setInt(5, venta.getAerolinea().getId_aeroLinea());
			ps.setInt(6, venta.getId_Venta());
	     	ps.executeUpdate();
	     	
	     //Cierro la conexión	
			conexion.close();
			
			return true;
		}catch (SQLException e) {
			e.printStackTrace(); 
			return false;
		}
	}
	
	@Override
	public List<Venta> ListAllVenta() {
		
	//Realizo la conexión	
		Connection conexion = null;
		PreparedStatement ps = null;
		List<Venta> lista= new ArrayList<>();
		try {
			conexion = sql.getConnection();
		    ps = conexion.prepareStatement(ListALL);
			ResultSet rs = ps.executeQuery();    
			while(rs.next()) {
				 
			//Obtengo los datos y genero el objeto venta
				String fecha_hora_venta = (rs.getString(("fecha_hora_venta")));
				String forma_pago = (rs.getString("forma_pago"));
				
				Venta venta = new Venta(fecha_hora_venta,forma_pago,null,null,null);
				lista.add(venta);
			 
			 }
			 conexion.close();
						
		}catch (SQLException e) {e.printStackTrace();}
		return lista;	
	}
		
	@Override
	public Venta getVentas(String id_venta) {
		
	//Realizo la conexión	
		Connection conexion = null;
	    PreparedStatement ps = null;	    
		try {	 
			conexion = sql.getConnection();
		    ps = conexion.prepareStatement(get);
		    
		//Seteo el parámetro    
		    ps.setString(1, id_venta);
		    ResultSet rs = ps.executeQuery();
				    
			while(rs.next()) {
				 
			//Obtengo los datos y creo el objeto venta  
				String fecha_hora_venta = rs.getString("fecha_hora_venta");
				String forma_pago = rs.getString("forma_pago");
				Integer id_cliente = rs.getInt("id_cliente");
				Integer id_aerolinea = rs.getInt("id_aerolinea");
				Integer id_vuelo = rs.getInt("id_vuelo");
				Cliente cliente = new Cliente(id_cliente, null, null, null, null, null, null, null, null, null, null);
				Aerolinea aerolinea = new Aerolinea(id_aerolinea, null, null);
				Vuelo vuelo = new Vuelo(id_vuelo, null, null, null, null, null, null, null, null);
			  
				Venta venta = new Venta(fecha_hora_venta, forma_pago, cliente, vuelo, aerolinea);
				return venta;
			}
			
	//Cierro la conexión	
		conexion.close();
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	
	
	@Override
	public List<Integer> obtenerIds() {
		
	//Realizo la conexión	
		Connection conexion = null;
		PreparedStatement ps = null;
		List<Integer> lista= new ArrayList<>();
		try {
			conexion = sql.getConnection();
		    ps = conexion.prepareStatement(ListALL);
			ResultSet rs = ps.executeQuery();    
			while(rs.next()) {
	
			//Obtengo el id y lo agrego a la lista	 
				Integer id_venta = rs.getInt("id_ventas");
				
				lista.add(id_venta);
			 
		}
	//Cierro la conexión		
		conexion.close();
						
		}catch (SQLException e) {e.printStackTrace();}
		return lista;	
	}
	
	@Override
	public Venta obtenerUltima() {
		
	//Realizo la conexión	
		Connection conexion = null;
	    PreparedStatement ps = null;	    
		try {	 
			conexion = sql.getConnection();
		    ps = conexion.prepareStatement(OBTENERULTIMA);
		    
		    ResultSet rs = ps.executeQuery();
				    
			while(rs.next()) {
				
			//Obtiene los datos y crea el objeto venta	
				Integer id_venta = rs.getInt("id_ventas");
				String fecha_hora_venta = rs.getString("fecha_hora_venta");
				String forma_pago = rs.getString("forma_pago");
				Integer id_cliente = rs.getInt("id_cliente");
				Integer id_aerolinea = rs.getInt("id_aerolinea");
				Integer id_vuelo = rs.getInt("id_vuelo");
				Cliente cliente = new Cliente(id_cliente, null, null, null, null, null, null, null, null, null, null);
				Aerolinea aerolinea = new Aerolinea(id_aerolinea, null, null);
				Vuelo vuelo = new Vuelo(id_vuelo, null, null, null, null, null, null, null, null);
			  
				Venta venta = new Venta(id_venta, cliente, vuelo, aerolinea, fecha_hora_venta, forma_pago);
				return venta;
			}
			
		//Cierro la conexión	
			conexion.close();
		} catch (SQLException e) {e.printStackTrace();}
		return null;
	}
	}

	