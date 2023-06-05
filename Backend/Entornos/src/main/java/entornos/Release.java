package entornos;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Release
 */
@WebServlet("/Release")
public class Release extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Release() {
        // TODO Auto-generated constructor stub
    }

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		
		String idXip = request.getParameter("xip_id");
		String idMed = request.getParameter("id_medicine");
		String mailP = request.getParameter("patient_mail");
		String date = request.getParameter("end_date");
		String query = "INSERT INTO xip (xip_id, doctor_mail, id_medicine, patient_mail, end_date) VALUES "
				+ "('"+idXip+"', '"+ mail +"', '"+ idMed +"', '"+ mailP +"', '"+ date +"')";
		Doctor d = new Doctor();
		d.load(mail);
		boolean isLogged = d.isLogged(mail, Integer.parseInt(session));
		if(isLogged) {
			try {
				BBDD bd = new BBDD();
				bd.conectar();
				bd.updateDoctor(query);
				
				System.out.println("Insert hecho");
				
				response.getWriter().append("Alta confirmada");
				
			} catch(Exception e) {
				System.out.println("Error en Release.doPost" + e.getMessage());
			}
		} else {
			System.out.println("El doctor no está logueado, inicia sesión");
		}
	}

}
