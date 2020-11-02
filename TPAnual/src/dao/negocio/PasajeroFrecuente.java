package dao.negocio;

public class PasajeroFrecuente {
	
	private int id_pasajeroFrecuente;
	private String categoria;
	private String numero;
	private Alianza alianza;
	private Aerolinea aerolinea;
	
	public PasajeroFrecuente(String categoria, String numero, Alianza alianza, Aerolinea aerolinea) {
		
		this.categoria = categoria;
		this.numero = numero;
		this.alianza = alianza;
		this.aerolinea = aerolinea;
	}

	public int getId_pasajeroFrecuente() {
		return id_pasajeroFrecuente;
	}
	public void setId_pasajeroFrecuente(int id_pasajeroFrecuente) {
		this.id_pasajeroFrecuente = id_pasajeroFrecuente;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Aerolinea getAerolinea() {
		return aerolinea;
	}
	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}

	public Alianza getAlianza() {
		return alianza;
	}

	public void setAlianza(Alianza alianza) {
		this.alianza = alianza;
	}

	@Override
	public String toString() {
		return "PasajeroFrecuente [id_pasajeroFrecuente=" + id_pasajeroFrecuente + ", categoria=" + categoria
				+ ", numero=" + numero + ", alianza=" + alianza + ", aerolinea=" + aerolinea + "]";
	}
	
	
	
	
	
	
}
