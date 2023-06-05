package entornos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Doctor extends Person {
	
	private String pass;
	private LocalDate lastLog;
	private int session;
	private ArrayList<Xip> releaseList;
	
//	Constructors
	
	public Doctor() {
	    releaseList = new ArrayList<>();

	}
	
	public Doctor(String name, String mail, String pass, LocalDate lastLog, int session) {
		super(name, mail);
		this.setPass(pass);
		this.setLastLog(lastLog);
		this.setSession(session);
	}
	
//	Métodos
	public void load(String id) {
		String query = "SELECT * FROM doctor where mail ='"+id+"';";
		BBDD bd = new BBDD();
		bd.conectar();
		ResultSet rs = bd.loadSelect(query);
		
		
		try {
			if (rs.next()) {
				this.setName(rs.getString("name"));
				this.setMail(rs.getString("mail"));
				this.setPass(rs.getString("pass"));
				this.setLastLog(rs.getDate("last_log").toLocalDate());
	            this.setSession(rs.getInt("session"));
			}
		} catch (SQLException e) {
			System.out.println("Error a Doctor.load: " + e.getMessage());
		

		}
		
		
	}
	
	public void login (String mail, String pass) {
		String query = "SELECT * FROM doctor where mail ='"+mail+"' AND pass='"+pass+"';";
		BBDD bd = new BBDD();
		bd.conectar();
		ResultSet rs = bd.loadSelect(query);
		try {
			if (rs.next()) {
				this.setLastLog(LocalDate.now());
				Random random = new Random();
				String code = "";
				for (int i= 0; i<9;i++) {
					code+=random.nextInt(10);
				}
				int session = Integer.parseInt(code);
				
				this.setSession(session);
				
				query = "UPDATE doctor SET last_log= '"+this.getLastLog()+"',session= '"+this.getSession()+"' WHERE mail='"+mail+"';";
				bd.updateDoctor(query);
				
				this.load(mail);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isLogged(String mail, int session) {
		// Verificar si el correo electrónico y la sesión coinciden en la base de datos
        // Cargar los datos utilizando el método login() si son válidos

        // Ejemplo de verificación de sesión de ejemplo
        if (this.getMail().equals(mail) && this.getSession()==session) {
            return true;
        }else{
            return false;
        }
    }
	public void loadReleaseList() {
	    releaseList.clear(); // Limpiamos la lista antes de cargar los nuevos xips
	    // Consulta SQL para obtener los xips vinculados al doctor
	    String query = "SELECT * FROM xip WHERE doctor_mail = '" + getMail() + "';";
	    
	    // Conectamos a la base de datos
	    BBDD bbdd = new BBDD();
	    bbdd.conectar();
	    
	    // Ejecutamos la consulta y obtenemos el resultado
	    ResultSet rs = bbdd.loadSelect(query);
	    
	    // Procesamos el resultado y agregamos los xips a la lista
	    try {
	        while (rs.next()) {
	            int xipId = rs.getInt("xip_id");
	            int medicineId = rs.getInt("id_medicine");
	            String patientMail = rs.getString("patient_mail");
	            LocalDate endDate = rs.getDate("end_date").toLocalDate();
	            // Crear objeto Medicine y Patient si es necesario
                Medicine medicine= new Medicine();  // Necesitas implementar la clase Medicine
                Patient patient = new Patient(); 
                medicine.setId(medicineId);
                patient.setMail(patientMail);
                
	            // Aquí puedes crear una instancia de la clase Xip y agregarla a la lista releaseList
	            // Por ejemplo:
                Xip xip = new Xip(xipId, medicine, patient, endDate);
	            
                releaseList.add(xip);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error en loadReleaseList: " + e.getMessage());
	    }
	}
	public String getTable() {
		String table = "<table>";
	    
	    // Crear la fila de encabezado de la tabla
	    table += "<tr>" +
	            "<th>Xip ID</th>" +
	    		"<th>Doctor Mail</th>" +
	            "<th>Medicine ID</th>" +
	            "<th>Patient Mail</th>" +
	            "<th>End Date</th>" +
	            "</tr>";
	    
	    // Obtener la lista de xips de alta y vigentes del doctor
	    loadReleaseList();
	    // Recorrer la lista de xips y agregar cada uno como una fila en la tabla
	    for (Xip xip : releaseList) {
	        table += "<tr>" +
	                "<td>" + xip.getId() + "</td>" +
	        		"<td>" + getMail() + "</td>" +
	                "<td>" + xip.getMedicine().getId() + "</td>" +
	                "<td>" + xip.getPatient().getMail() + "</td>" +
	                "<td>" + xip.getDate() + "</td>" +
	                
	                "</tr>";
	    }
	    
	    table += "</table>";
	    return table;
	}
//	Getters y setters
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public LocalDate getLastLog() {
		return lastLog;
	}

	public void setLastLog(LocalDate lastLog) {
		this.lastLog = lastLog;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}
	
}