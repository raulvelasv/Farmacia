package entornos;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServeXips
 */
@WebServlet("/ServeXips")
public class ServeXips extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ServeXips() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws ServletException, IOException {
		System.out.println("conexion de xip");
		String mail = request.getParameter("mail");
		String session = request.getParameter("session");
		Doctor doctor= new Doctor();
		doctor.load(mail);
		doctor.loadReleaseList();
		String tableHtml = doctor.getTable();
        // Envía la respuesta al cliente
        response.setContentType("text/html");
        response.getWriter().write(tableHtml);
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
