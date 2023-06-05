package entornos;

import java.io.IOException;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class ServeMedicines
 */
@WebServlet("/ServeMedicines")
public class ServeMedicines extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServeMedicines() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("conexion tabla medicinas");
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		Doctor d =new Doctor();
		d.load(mail);
		if(d.isLogged(mail, Integer.parseInt(session))){
			String jsonString = null;
			JSONArray ja = new JSONArray();
			
			BBDD bd = new BBDD();
			bd.conectar();
			
			try {
				String query = "SELECT * FROM medicine";
				ResultSet rs = bd.loadSelect(query);
				Class.forName("org.json.JSONObject");
				while(rs.next()) {
					int medId = rs.getInt("med_Id");
					String name = rs.getString("name");
					float tmax = rs.getFloat("tmax");
					float tmin = rs.getFloat("tmin");
					Medicine medicine = new Medicine(medId,name,tmax,tmin);
					JSONObject jsonlista = new JSONObject(medicine);
					ja.put(jsonlista);
				}
				jsonString = ja.toString();
			} catch (Exception e) {
				System.out.println("Error en ServePatients.doGet " + e.getMessage());
			}
			
			
			response.getWriter().append(jsonString);
		} else {
			System.out.println("El doctor no está logueado");
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
