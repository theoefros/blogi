package theoefros.blogi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import theoefros.blogi.log.MyLogger;

/**
 * Klass andmebaasiga ühenduse saamiseks.
 * @author Theo Efros
 *
 */
public class Andmebaasiyhendus {

	private String pwd = "";
	private String usr = "";
	private String url = "";
	private Connection abYhendus;

	/**
	 * Meetod andmebaasiühenduse saamiseks. Loeb failist DBConnection vajalikud parameetrid 
	 * ning proovib saada ühendust andmebaasiga.
	 * @return andmebaasiühenuse (Connection), kui õnnestub saada ühendus.
	 * @throws ClassNotFoundException kui ei õnnestu luua andmebaasidraiveri klassi
	 * @throws SQLException kui ei õnnestu saada ühendust andmebaasiga.
	 */
	public Connection getAbYhendus() throws ClassNotFoundException, SQLException {
		ResourceBundle bundle = ResourceBundle.getBundle("DBConnection");
		
		Class.forName(bundle.getString("Driver"));
		this.url = bundle.getString("url");
		this.usr = bundle.getString("usr");
		this.pwd = bundle.getString("pwd");
		this.abYhendus = DriverManager.getConnection(this.url, this.usr, this.pwd);

		return this.abYhendus;
	}

	/**
	 * Meetod andmebaasiühenduse sulgemiseks
	 */
	public void sulgeAbYhendus() {
		try {
			this.abYhendus.close();
		} catch (SQLException ex) {
			// Vea puhul logime vea
			MyLogger.Log("sulgeYhendus", ex.getMessage());
		}
	}

}
