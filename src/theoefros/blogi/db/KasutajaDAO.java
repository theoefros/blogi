package theoefros.blogi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import theoefros.blogi.model.Kasutaja;

/**
 * Klass kasutaja andmeta küsimises andmebaasist
 * @author Theo Efros
 *
 */
public class KasutajaDAO {

	/**
	 * Meetod ,mis kontrollib, kas kasutaja on andmebaasis või mitte.
	 * @param k - kasutaja, keda kontrollida.
	 * @return Kasutaja objekt, millel on onLubatud väli seatud vastavalt kontrollile - true, kui
	 * kasutaja on andmebaasis olemas, false, kui mitte.
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Kasutaja kontrolliKasutajat(Kasutaja k) throws SQLException, ClassNotFoundException{
		
		// Ühenduse loomine andmebaasiga
		Andmebaasiyhendus abYhendus = new Andmebaasiyhendus();
		Connection yhendus = abYhendus.getAbYhendus();
		
		// SQL lause ettevalmistamine
		String sqlLause = "SELECT kasutajanimi, parool FROM kasutaja WHERE kasutajanimi = ? AND parool = ?;";		
		
		PreparedStatement prepStmt = yhendus.prepareStatement(sqlLause);
		prepStmt.setString(1, k.getKasutajanimi());
		prepStmt.setString(2, k.getParool());
		
		// SQL lause käivitamine
		ResultSet tulemus = prepStmt.executeQuery();
		
		// Kui andmebaasist saadi andmeid, siis järelikult on selline kasutaja andmebaasis olemas
		if(tulemus.next()){
			k.setOnLubatud(true);
		}else{
			k.setOnLubatud(false);
		}
		
		// Ühenduse sulgemine
		abYhendus.sulgeAbYhendus();
		
		return k;
	}
}
