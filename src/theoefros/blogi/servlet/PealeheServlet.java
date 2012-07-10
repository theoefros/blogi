package theoefros.blogi.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import theoefros.blogi.db.PostitusDAO;
import theoefros.blogi.log.MyLogger;
import theoefros.blogi.model.Postitus;
import theoefros.blogi.teated.Teade;
import theoefros.blogi.teated.Veateade;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Servlet, mis tegeleb pealehe pöördumistega
 * 
 * @author Theo Efros
 * 
 */
public class PealeheServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// Pealehel kuvatakse 5 viimast postitust.
		// Uus POstitusDAO objekt postituste saamiseks andmebaadsist
		PostitusDAO abPostitus = new PostitusDAO();

		// Postituste nimekiri
		List<Postitus> postitused = new ArrayList<Postitus>();

		// Küsitakse andmebaasist postitusi
		try {
			postitused = abPostitus.getPostitused(5);
		} catch (SQLException e) {
			// Vigade puhul logi ja teade kasutajale
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

		// Kui ei leitud ühtegi, siis vastav teade
		if (postitused.isEmpty()) {
			Teade t = new Teade("Ei leitud ühtegi postitust.");
			req.setAttribute("teade", t);
			getServletConfig().getServletContext()
					.getRequestDispatcher("/pealeht.jsp").forward(req, res);
		} else {
			// Kui leiti, siis suunatakse edasi, kaasa antakse postituste
			// nimekiri
			req.setAttribute("postitused", postitused);
			getServletConfig().getServletContext()
					.getRequestDispatcher("/pealeht.jsp").forward(req, res);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
}