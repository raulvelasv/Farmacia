package entornos;

import java.io.IOException;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import java.util.*;

/**
 * Servlet implementation class ServePatients
 */
@WebServlet("/ServePatients")
public class ServePatients extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServePatients() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("conexion tabla pacientes");
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		Doctor d =new Doctor();
		d.load(mail);
		if(d.isLogged(mail, Integer.parseInt(session))){
			String query = "SELECT * FROM patient";
			ArrayList<String> listaPatients = new ArrayList<>();
			String jsonString = null;
			JSONArray ja = new JSONArray();
			BBDD bd = new BBDD();
			bd.conectar();
			ResultSet rs = bd.loadSelect(query);
			try {
				while(rs.next()) {
					String patientMail = rs.getString("mail");
					listaPatients.add(patientMail);
				}
				
			    for (String patient : listaPatients){ 
		            ja.put(patient);
		        }
				
				jsonString = ja.toString();
	
			} catch (Exception e) {
				System.out.println("Error en ServePatients.doGet " + e.getMessage());
			}
			
			
			response.getWriter().append(jsonString);
		}
	}
}
