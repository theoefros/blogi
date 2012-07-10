package theoefros.blogi.teated;

/**
 * Klass teate jaoks. Teatel on tekst, ning samuti saab määrata teksti värvi
 * väljatrüki jaoks (näiteks HTML color jaoks).
 * @author Theo Efros
 *
 */
public class Teade {
	


	private String tekst;
	private String tekstiVarv = "black";
	
	public Teade(String tekst){
		this.tekst = tekst;
	}
	
	public Teade(String tekst, String tekstiVarv){
		this.tekst = tekst;
		this.tekstiVarv = tekstiVarv;
	}
	
	public String getTekst() {
		return tekst;
	}

	public void setTekst(String tekst) {
		this.tekst = tekst;
	}

	public String getTekstiVarv() {
		return tekstiVarv;
	}

	public void setTekstiVarv(String tekstiVarv) {
		this.tekstiVarv = tekstiVarv;
	}
}
