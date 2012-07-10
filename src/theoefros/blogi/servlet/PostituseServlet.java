package theoefros.blogi.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import theoefros.blogi.db.PostitusDAO;
import theoefros.blogi.log.MyLogger;
import theoefros.blogi.model.Postitus;
import theoefros.blogi.teated.Teade;
import theoefros.blogi.teated.Veateade;

/**
 * Servlet, mis tegeleb postitustega seotud pöördumistega.
 * 
 * @author Theo
 * 
 */
public class PostituseServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		suuna(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		doGet(req, res);
	}

	/**
	 * Meetod, mis vastavalt request objektist saadud parameetrile "mode" suunab
	 * tegevust
	 * 
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	public void suuna(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// Tegakse kindlaks parameetri "mode" väärtus
		String valik = req.getParameter("mode");

		// Tegevused
		if (valik.equals("vaata")) {

			// Üksiku postituse vaatamine

			// Leitakse postituse number, mida tahetakse vaadata
			int postituseNumber = Integer.parseInt(req
					.getParameter("postituseNumber"));

			Postitus postitus = null;

			// Postituse küsimine andmebaasist
			try {
				postitus = leiaPostitus(postituseNumber);
			} catch (SQLException e) {
				// Vigade puhul logitakse veateade ning suunatakse tagasi
				// pealehele, koos teatega
				MyLogger.LogMessage(e.getMessage());

				Teade t = new Veateade("Viga andmebaasiga!");
				req.setAttribute("teade", t);
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
			// Kui saadi postitus ning see pole NULL, siis kuvatakse postitus
			if (postitus != null) {
				req.setAttribute("postitus", postitus);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/postitus.jsp")
						.forward(req, res);
			} else {
				// Kui aga andmebaasist ei leitud postitust, siis vastav teade
				Teade t = new Teade("Postitust ei leitud");
				req.setAttribute("teade", t);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/postitus.jsp")
						.forward(req, res);
			}

		} else {

			// Kui pole tegemist üksiku postituse vaatamisega, siis
			// kontrollitakse,
			// kas sessiooniga on seotud kasutaja ehk kas keegi on sisse
			// loginud.
			if (kontrolliKasutajat(req)) {

				// Kui on sisse logitud, siis suuntakse edasi
				if (valik.equals("muutmisvormile")) {

					// Muutmisvormi jaoks otsitakse andmebaasist postituse
					// numbri järgi postitus,
					// mida tahetakse muuta

					int postituseNumber = Integer.parseInt(req
							.getParameter("postituseNumber"));

					Postitus postitus = null;

					// Postituse küsimine andmebaasist
					try {
						postitus = leiaPostitus(postituseNumber);
					} catch (SQLException e) {
						// Vigade puhul logi ja teade kasutajale
						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Viga andmebaasiga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;

					} catch (ClassNotFoundException e) {
						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Rakenduses ilmnes viga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;

					}

					// Postitus olemas, suunatakse muutmisvormile
					req.setAttribute("postitus", postitus);
					getServletConfig().getServletContext()
							.getRequestDispatcher("/muudaPostitust.jsp")
							.forward(req, res);

				} else if (valik.equals("salvestaMuudatused")) {

					int postituseNumber = Integer.parseInt(req
							.getParameter("postituseNumber"));
					// Muudetud andmetast moodustatakse postituse objekt
					Postitus postitus = new Postitus();
					postitus.setPostituseNumber(postituseNumber);
					postitus.setPealkiri(req.getParameter("pealkiri"));
					postitus.setSisu(req.getParameter("sisu"));

					// Postituse uuendamine andmebaasis
					try {
						muudaPostitust(postitus);
					} catch (SQLException e) {
						// Vigade puhule logi ja teade kasutajale
						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Viga andmebaasiga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;
					} catch (ClassNotFoundException e) {

						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Rakenduses ilmnes viga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;

					}

					// Seejärel suunatakse muudetud postituse lehele
					res.sendRedirect(req.getContextPath()
							+ "/postitus?mode=vaata&postituseNumber="
							+ postituseNumber);

				} else if (valik.equals("kustuta")) {

					// Kustutamise jaoks tehekse kindlaks postituse number
					int postituseNumber = Integer.parseInt(req
							.getParameter("postituseNumber"));

					// Ning proovitakse seda andmebaasist kustutada
					try {
						kustutaPostitus(postituseNumber);
					} catch (SQLException e) {
						// Vigade puhul logi ja teade kasutajale
						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Viga andmebaasiga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;

					} catch (ClassNotFoundException e) {

						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Rakenduses ilmnes viga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;
					}
					// Suunatakse tagasi pealehele
					getServletConfig().getServletContext()
							.getRequestDispatcher("/pealeht").forward(req, res);

				} else if ((valik.equals("lisamiseVormile"))) {
					// Suunatakse kasutaja uue postituse lisamise vormile
					getServletConfig().getServletContext()
							.getRequestDispatcher("/lisaPostitus.jsp")
							.forward(req, res);
				} else if (valik.equals("lisaUus")) {

					// Postituse lisamiseks andmebaasi moodustatakse uus
					// postituse objekt
					// Postituse numbri ja lisamise kuupäeva lisab
					// andmebaasisüteem automaatselt
					Postitus postitus = new Postitus();
					postitus.setPealkiri(req.getParameter("pealkiri"));
					postitus.setSisu(req.getParameter("sisu"));

					// Proovitakse lisada andmebaasi uut postitust
					try {
						lisaPostitus(postitus);
					} catch (SQLException e) {

						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Viga andmebaasiga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;

					} catch (ClassNotFoundException e) {

						MyLogger.LogMessage(e.getMessage());

						Teade t = new Veateade("Rakenduses ilmnes viga!");
						req.setAttribute("teade", t);
						getServletConfig().getServletContext()
								.getRequestDispatcher("/pealeht.jsp")
								.forward(req, res);
						return;

					}
					// Õnnestumise puhul suunatakse kasutaja pealehele, kus on
					// uus postitus ka näha
					res.sendRedirect(req.getContextPath() + "/pealeht");

				} else {
					// Kui "mode" on miski muu, siis suunatakse pealehele
					getServletConfig().getServletContext()
							.getRequestDispatcher("/pealeht").forward(req, res);
				}
			} else {

				// Kui ei olnud sisselogitud kasutajat, siis suunatakse
				// sisselogimise vormile
				// koos teatega
				Teade t = new Veateade("Palun logige sisse");
				req.setAttribute("teade", t);
				getServletConfig().getServletContext()
						.getRequestDispatcher("/login.jsp").forward(req, res);
			}
		}
	}

	// Abimeetodid postituse andmete küsimiseks, muutmiseks, lisamiseks,
	// kustutamiseks
	// Kasutavad PostitusDAO klassi, et suhelda andmebaasiga

	/**
	 * Küsib andmebaasist vastava numbriga postitust.
	 * 
	 * @param postituseNumber
	 *            - postituse numbri järgi leitakse postitus
	 * @return postitus, mis andmebaasist saadi
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Postitus leiaPostitus(int postituseNumber) throws SQLException,
			ClassNotFoundException {
		Postitus postitus = new Postitus();
		PostitusDAO postitusDAO = new PostitusDAO();
		postitus = postitusDAO.getPostitus(postituseNumber);
		return postitus;
	}

	/**
	 * Meetod postituse uuendamiseks.
	 * 
	 * @param postitus
	 *            - postitus, mis sisaldab muudetava postituse numbrit ning
	 *            muudetud infot.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void muudaPostitust(Postitus postitus)
			throws ClassNotFoundException, SQLException {

		PostitusDAO postitusDAO = new PostitusDAO();
		postitusDAO.muudaPostitust(postitus);

	}

	/**
	 * Postituse kustutamine
	 * 
	 * @param postituseNumber
	 *            - kustutatava postituse number
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void kustutaPostitus(int postituseNumber) throws SQLException,
			ClassNotFoundException {
		PostitusDAO postituseDAO = new PostitusDAO();
		postituseDAO.kustutaPostitusAndmebaasist(postituseNumber);

	}

	/**
	 * Uue postituse lisamine andmebaasi
	 * 
	 * @param postitus
	 *            - uus postitus
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void lisaPostitus(Postitus postitus) throws SQLException,
			ClassNotFoundException {
		PostitusDAO postitusDAO = new PostitusDAO();
		postitusDAO.lisaPostitusAndmebaasi(postitus);

	}

	/**
	 * Meetod, mis kontrollib, kas kasutaja on sisse loginud.
	 * 
	 * @param req
	 * @return true, kui kasutaja on sisselogitud
	 */
	public boolean kontrolliKasutajat(HttpServletRequest req) {

		// Küsime sessiooni
		HttpSession sessioon = req.getSession(false);

		if (sessioon != null) {
			// Kui atribuut sessioonikasutaja on olemas, siis on kasutaja sisse
			// loginud
			if (sessioon.getAttribute("sessiooniKasutaja") != null) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
