package theoefros.blogi.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import theoefros.blogi.db.KasutajaDAO;
import theoefros.blogi.log.MyLogger;
import theoefros.blogi.model.Kasutaja;
import theoefros.blogi.teated.HeaTeade;
import theoefros.blogi.teated.Teade;
import theoefros.blogi.teated.Veateade;

/**
 * Servlet, mis tegeleb sisse- ja väljalogimistega.
 * 
 * @author Theo Efros
 * 
 */
public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {

		// Selle parameetri järgi otsustatakse, mida teha
		String valik = req.getParameter("mode");

		if (valik.equals("sisselogimiseVorm")) {

			// Suunatakse sisselogimise vormile
			getServletConfig().getServletContext()
					.getRequestDispatcher("/login.jsp").forward(req, res);

		} else if (valik.equals("logiSisse")) {

			// Kui sisselogimise vorm täideti, siis kontrollitakse, kas kasutaja
			// on andmebaasis
			// Uus kasutaja objekt
			Kasutaja kasutaja = new Kasutaja();
			kasutaja.setKasutajanimi(req.getParameter("kasutajanimi"));
			kasutaja.setParool(req.getParameter("parool"));

			KasutajaDAO kasutajaDao = new KasutajaDAO();

			try {
				// Kontrollitakse kas kasutaja on andmebaasis
				kasutaja = kasutajaDao.kontrolliKasutajat(kasutaja);
			} catch (SQLException e) {
				// Püüdes kinni vea, logitakse veateade ning
				// moodustame teate objekti, ning edastame selle kasutajale
				MyLogger.LogMessage(e.getMessage());

				Teade t = new Veateade("Viga andmebaasiga!");
				req.setAttribute("teade", t);
				// Suunatakse pealehele
				getServletConfig().getServletContext()
						.getRequestDispatcher("/pealeht.jsp").forward(req, res);
				return;
			} catch (ClassNotFoundException e) {

				MyLogger.LogMessage(e.getMessage());

				Teade t = new Veateade("Rakenduses ilmnes viga!");
				req.setAttribute("teade", t);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/pealeht.jsp").forward(req, res);
				return;
			}

			// Kui on andmebaasis olemas, siis salvestatakse kasutaja on´bjekt
			// sessioonimuutujasse
			if (kasutaja.onLubatud()) {
				HttpSession sessioon = req.getSession(true);
				sessioon.setAttribute("sessiooniKasutaja", kasutaja);
				// Loome teate eduka sisselogimise kohta
				Teade t = new HeaTeade("Sisselogimine õnnestus");
				req.setAttribute("teade", t);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/pealeht").forward(req, res);
			} else {
				// Kui kasutajat pole andmebaasis, siis teade ning tagasi
				// sisselogimise vormile
				Teade t = new Veateade("Sisselogimine ebaõnnestus");
				req.setAttribute("teade", t);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/login.jsp").forward(req, res);
			}
		} else if (valik.equals("logiValja")) {
			// Väljalogimisel küsitakse sessioon
			HttpSession sessioon = req.getSession(false);
			if (sessioon != null) {
				// Ning tühistatakse see
				sessioon.invalidate();
			}
			res.sendRedirect((req.getContextPath() + "/pealeht"));
		} else {
			getServletConfig().getServletContext()
					.getRequestDispatcher("/pealeht").forward(req, res);
		}

	}
}
