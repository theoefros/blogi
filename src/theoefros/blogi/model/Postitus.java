package theoefros.blogi.model;

import java.sql.Date;

/**
 * Postituse klass
 * @author Theo Efros
 *
 */
public class Postitus {

	private int postituseNumber;
	private String pealkiri;
	private String sisu;
	private Date kuupaev;
	
	public int getPostituseNumber() {
		return postituseNumber;
	}

	public void setPostituseNumber(int postituseNumber) {
		this.postituseNumber = postituseNumber;
	}
	public String getPealkiri() {
		return pealkiri;
	}
	
	public void setPealkiri(String pealkiri) {
		this.pealkiri = pealkiri;
	}
	
	public String getSisu() {
		return sisu;
	}
	
	public void setSisu(String sisu) {
		this.sisu = sisu;
	}
	
	public Date getKuupaev() {
		return kuupaev;
	}
	
	public void setKuupaev(Date kuupaev) {
		this.kuupaev = kuupaev;
	}
	
}
