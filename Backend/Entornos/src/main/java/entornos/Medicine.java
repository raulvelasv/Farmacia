package entornos;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Medicine {
	private int id;
	private String name;
	private Float tMax;
	private Float tMin;
	public Medicine(int id, String name, Float tmax, Float tmin) {
		this.id = id;
		this.name = name;
		this.tMax = tMax;
		this.tMin = tMin;
		
	}
	public Medicine() {}
	public void load(String id) {
		String query = "SELECT * FROM medicine where med_id ='"+id+"';";
		BBDD bd = new BBDD();
		bd.conectar();
		ResultSet rs = bd.loadSelect(query);
		
		
		try {
			if (rs.next()) {
				this.setName(rs.getString("name"));
				this.settMax(rs.getFloat("tmax"));
				this.settMin(rs.getFloat("tmin"));
			}
		} catch (SQLException e) {
			System.out.println("Error a Doctor.load: " + e.getMessage());
		

		}
		
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float gettMax() {
		return tMax;
	}
	public void settMax(Float tMax) {
		this.tMax = tMax;
	}
	public Float gettMin() {
		return tMin;
	}
	public void settMin(Float tMin) {
		this.tMin = tMin;
	}
	

}
