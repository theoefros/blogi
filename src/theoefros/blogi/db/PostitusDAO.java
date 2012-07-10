package theoefros.blogi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import theoefros.blogi.model.Postitus;

/**
 * Klass postitusega seotud andmebaasioperatsioonide teostamiseks (küsimine, lisamine,
 * uuendamine, kustutamine).
 * @author Theo Efros
 * 
 */
public class PostitusDAO {

	/**
	 * Tagastab soovitud arvu postitusi, järjestatuna kuupäeva järgi kahanevalt.
	 * 
	 * @param postitusteArv
	 *            - maksimaalne arv postitusi, mis tagastatakse.
	 * @return andmebaasist saadud postituste nimekiri.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public List<Postitus> getPostitused(int postitusteArv) throws SQLException, ClassNotFoundException {
		
		// Ühenduse loomine andmebaasiga
		Andmebaasiyhendus abYhendus = new Andmebaasiyhendus();
		Connection yhendus = abYhendus.getAbYhendus();
		
		// SQL lause ettevalmistamine
		Statement st = yhendus.createStatement();
		String sqlLause = "SELECT postituse_number, pealkiri, sisu, kuupaev FROM postitus ORDER BY kuupaev DESC LIMIT " + postitusteArv + ";";
		
		// SQL päring
		ResultSet tulemus = st.executeQuery(sqlLause);
		
		List<Postitus> postitused = new ArrayList<Postitus>();
		
		// Postituste lisamine postituste nimekirja.
		while (tulemus.next()) {
			Postitus postitus = new Postitus();
			postitus.setPostituseNumber(tulemus.getInt("postituse_number"));
			postitus.setPealkiri(tulemus.getString("pealkiri"));
			postitus.setSisu(tulemus.getString("sisu"));
			postitus.setKuupaev(tulemus.getDate("kuupaev"));
			postitused.add(postitus);
		}
		
		// Anmdebaadiühenduse sulgemine
		abYhendus.sulgeAbYhendus();
		
		return postitused;
	}
	
	/**
	 * Otsib ja tagastab postituse postituse numbri järgi.
	 * @param postituseNumber - otsitava postituse number.
	 * @return otsitud postitus
	 * @throws ClassNotFoundException kui andmebaasiühenduse looomisel ei ünnestunud luua draiverit
	 * @throws SQLException kui ei õnnestunud andmebaasiühenduse loomine; kui SQL lause oli vigane
	 */
	public Postitus getPostitus(int postituseNumber) throws SQLException, ClassNotFoundException{
		
		// Ühenduse loomine andmebaasiga
		Andmebaasiyhendus abYhendus = new Andmebaasiyhendus();
		Connection yhendus = abYhendus.getAbYhendus();
		
		// SQL lause ettevalmistus
		Statement st = yhendus.createStatement();
		String sqlLause = "SELECT postituse_number, pealkiri, sisu, kuupaev FROM postitus WHERE postituse_number = " + postituseNumber + ";";
		
		//SQL päring
		ResultSet tulemus = st.executeQuery(sqlLause);
		
		// ResultSet objektis kursori nihutamine, et saaksime andmed kätte
		tulemus.next();
		
		Postitus postitus = new Postitus();
		postitus.setPealkiri(tulemus.getString("pealkiri"));
		postitus.setSisu(tulemus.getString("sisu"));
		postitus.setPostituseNumber(tulemus.getInt("postituse_number"));
		postitus.setKuupaev(tulemus.getDate("kuupaev"));
		
		abYhendus.sulgeAbYhendus();
		
		return postitus;
	}
	
	/**
	 * Meetod uue postituse lisamiseks andmebaasi.
	 * @param postitus
	 * @throws SQLException kui ei õnnestunud andmebaasiga ühenduse loomine;
	 * @throws ClassNotFoundException
	 */
	public void lisaPostitusAndmebaasi(Postitus postitus) throws SQLException, ClassNotFoundException{
		
		// Ühendus andmebaasiga
		Andmebaasiyhendus abYhendus = new Andmebaasiyhendus();
		Connection yhendus = abYhendus.getAbYhendus();
		
		// SQL lause ettevalmistus
		String sqlLause = "INSERT INTO postitus (pealkiri, sisu) VALUES (?,?);";		
		
		PreparedStatement prepStmt = yhendus.prepareStatement(sqlLause);
		prepStmt.setString(1, postitus.getPealkiri());
		prepStmt.setString(2, postitus.getSisu());
		
		// SQL lause käivitamine
		prepStmt.executeUpdate();
		
		abYhendus.sulgeAbYhendus();
	}
	
	/**
	 * @param postituseNumber
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void kustutaPostitusAndmebaasist(int postituseNumber) throws SQLException, ClassNotFoundException{
		
		// Ühendus andmebaasiga
		Andmebaasiyhendus abYhendus = new Andmebaasiyhendus();
		Connection yhendus = abYhendus.getAbYhendus();
		
		// SQL lause
		Statement st = yhendus.createStatement();
		String sqlLause = "DELETE FROM postitus WHERE postituse_number = " + postituseNumber + ";";
		
		st.executeUpdate(sqlLause);
		
		abYhendus.sulgeAbYhendus();
	}
	
	/**
	 * @param postitus
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void muudaPostitust(Postitus postitus) throws ClassNotFoundException, SQLException{
		
		// Ühendus andmebaasiga
		Andmebaasiyhendus abYhendus = new Andmebaasiyhendus();
		Connection yhendus = abYhendus.getAbYhendus();

		// SQL lause
		String sqlLause = "UPDATE postitus SET pealkiri = ? , sisu = ? WHERE postituse_number = ? ";
		PreparedStatement prepStmt = yhendus.prepareStatement(sqlLause);
		prepStmt.setString(1, postitus.getPealkiri());
		prepStmt.setString(2, postitus.getSisu());
		prepStmt.setInt(3, postitus.getPostituseNumber());
				
		// SQL lause käivitamine
		prepStmt.executeUpdate();
	
		abYhendus.sulgeAbYhendus();
	}
}
