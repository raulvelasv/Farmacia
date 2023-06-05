package entornos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient extends Person{

	public Patient() {}
	public Patient(String name, String mail) {
	super(name, mail);
	}
	
	public void load(String id) {
		String query = "SELECT * FROM patient;";
		BBDD bd = new BBDD();
		bd.conectar();
		ResultSet rs = bd.loadSelect(query);
		try {
			if (rs.next()) {
				this.setName(rs.getString("name"));
				this.setMail(rs.getString("mail"));
			}
		} catch (SQLException e) {
			System.out.println("Error a Patients.load: " + e.getMessage());
		}
	}
	
}
