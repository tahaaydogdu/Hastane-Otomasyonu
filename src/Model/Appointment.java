package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Helper.DBConnection;

public class Appointment {
	private int id,doctor_id,hasta_id;
	private String doctorName,hastaName,appDate;
	
	DBConnection conn = new DBConnection();
    Statement st = null;
    ResultSet rs = null;
    PreparedStatement preparedStatement = null;

    public Appointment(int id, int doctor_id, int hasta_id, String doctorName, String hastaName, String appDate) {
		super();
		this.id = id;
		this.doctor_id = doctor_id;
		this.hasta_id = hasta_id;
		this.doctorName = doctorName;
		this.hastaName = hastaName;
		this.appDate = appDate;
	}
    
    public Appointment() {
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getHasta_id() {
		return hasta_id;
	}

	public void setHasta_id(int hasta_id) {
		this.hasta_id = hasta_id;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getHastaName() {
		return hastaName;
	}

	public void setHastaName(String hastaName) {
		this.hastaName = hastaName;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}
    
	public ArrayList<Appointment> getHastaList(int hasta_id) throws SQLException {
	    ArrayList<Appointment> list = new ArrayList<>();
	    Appointment obj;
	    Connection con = conn.connDb();
	    PreparedStatement preparedStatement = null;
	    ResultSet rs = null;

	    String query = "SELECT * FROM appointment WHERE hasta_id = ?";
	    try {
	        preparedStatement = con.prepareStatement(query);
	        preparedStatement.setInt(1, hasta_id); 
	        rs = preparedStatement.executeQuery();

	        while (rs.next()) {
	            obj = new Appointment();
	            obj.setId(rs.getInt("id")); 
	            obj.setDoctor_id(rs.getInt("doctor_id"));
	            obj.setDoctorName(rs.getString("doctor_name")); 
	            obj.setHasta_id(rs.getInt("hasta_id"));
	            obj.setHastaName(rs.getString("hasta_name"));
	            obj.setAppDate(rs.getString("app_date"));
	            list.add(obj);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (rs != null) rs.close();
	        if (preparedStatement != null) preparedStatement.close();
	        if (con != null) con.close();
	    }

	    return list;
	}

	
	public ArrayList<Appointment> getDoctorList(int doctor_id) throws SQLException {
        ArrayList<Appointment> list = new ArrayList<>();

        Appointment obj;
        Connection con = conn.connDb();

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM appointment WHERE doctor_id = ?" + doctor_id);
            while (rs.next()) {
                obj = new Appointment();
                obj.setId(rs.getInt("clinic_id"));
                obj.setDoctor_id(rs.getInt("doctor_id"));
                obj.setDoctorName(rs.getString("doctor_ad"));
                obj.setHasta_id(rs.getInt("hasta_id"));
                obj.setHastaName(rs.getString("hasta_name"));
                obj.setAppDate(rs.getString("app_date"));
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) st.close();
            if (rs != null) rs.close();
            if (con != null) con.close();
        }

        return list;
    }
}
