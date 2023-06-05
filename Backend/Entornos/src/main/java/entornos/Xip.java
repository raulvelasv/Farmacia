package entornos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Xip {
	private int id;
	private Medicine medicine;
	private Patient patient;
	private LocalDate date;
	public Xip() {
		
	}
	public Xip(int id, Medicine medicine, Patient patient, LocalDate date) {
		this.id = id;
		this.medicine = medicine;
		this.patient = patient;
		this.date = date;
	}
	
	public void load(int id) {
	    String query = "SELECT * FROM xip WHERE id = " + id;
	    BBDD bd = new BBDD();
	    bd.conectar();
	    ResultSet rs = bd.loadSelect(query);

	    try {
	        if (rs.next()) {
	            int medicineId = rs.getInt("id_medicine");
	            String patientMail = rs.getString("patient_mail");
	            LocalDate date = rs.getDate("date").toLocalDate();
	            // Crear objeto Medicine y Patient si es necesario
	            Medicine medicine = new Medicine();  // Necesitas implementar la clase Medicine
	            Patient patient = new Patient();  // Necesitas implementar la clase Patient
	            
	            // Asignar los valores obtenidos a los atributos correspondientes
	            this.setId(id);
	            this.setMedicine(medicine);
	            this.setPatient(patient);
	            this.setDate(date);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en Xip.load: " + e.getMessage());
	    }
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Medicine getMedicine() {
		return medicine;
	}
	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	

}
