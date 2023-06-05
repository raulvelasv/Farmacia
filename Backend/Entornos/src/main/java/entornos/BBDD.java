package entornos;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BBDD {
	private Connection con;
	private Statement st;

	public void conectar() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Error a BBDD.connectar.Class"+ e.getMessage());
		}
		con=null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3305/farmacia","root","1234");
		} catch (SQLException e) {
			System.out.println("Error a BBDD.connectar.Connection"+ e.getMessage());
		}
		st=null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			System.out.println("Error a BBDD.connectar.Statement"+ e.getMessage());
		}
	}
	
	public ResultSet loadSelect(String query) {
		ResultSet rs;
		
        rs = null;
		try {
			rs = st.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("Error en BBDDFarmacia.loadSelect.executeQuery " + e.getMessage());
		}
		return rs;
	}
	
	public void updateDoctor (String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Error a update"+ e.getMessage());
		}
	}
}