package theoefros.blogi.model;

/**
 * Kasutaja  klass
 * @author Theo Efros
 *
 */
public class Kasutaja {

	private int kasutajaId;
	private String kasutajanimi;
	private String parool;
	
	/**
	 * Näitab, kas kasutajal on lubatud süsteemi sisse logida või mitte.
	 */
	private boolean onLubatud = false;
	
	public boolean onLubatud() {
		return onLubatud;
	}
	public void setOnLubatud(boolean onLubatud) {
		this.onLubatud = onLubatud;
	}
	public int getKasutajaId() {
		return kasutajaId;
	}
	public void setKasutajaId(int kasutajaId) {
		this.kasutajaId = kasutajaId;
	}
	public String getKasutajanimi() {
		return kasutajanimi;
	}
	public void setKasutajanimi(String kasutajanimi) {
		this.kasutajanimi = kasutajanimi;
	}
	public String getParool() {
		return parool;
	}
	public void setParool(String parool) {
		this.parool = parool;
	}
	
}
