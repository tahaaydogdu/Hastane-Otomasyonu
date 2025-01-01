package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Bashekim extends User{
	
	Statement st=null;
	ResultSet rs=null;
	Connection con = conn.connDb();
	PreparedStatement preparedStatement = null;
	
	public Bashekim(int id, String tc, String sifre, String ad, String tip) {
		super(id, tc, sifre, ad, tip);
	}

	public Bashekim() {}
	
	public ArrayList<User> getDoctorList() throws SQLException{
		ArrayList<User> list = new ArrayList<>();
		
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tip ='doktor'");
			while(rs.next()) {
				obj = new User(rs.getInt("id"),rs.getString("tc"),rs.getString("sifre"),rs.getString("ad"),rs.getString("tip"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<User> getClinicDoctorList(int clinic_id) throws SQLException{
		ArrayList<User> list = new ArrayList<>();
		
		User obj;
		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT u.id,u.tc,u.tip,u.ad,u.sifre FROM worker w LEFT JOIN user u ON w.user_id=u.id WHERE clinic_id = " + clinic_id);
			while(rs.next()) {
				obj = new User(rs.getInt("u.id"),rs.getString("u.tc"),rs.getString("u.tip"),rs.getString("u.ad"),rs.getString("u.sifre"));
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

public boolean addDoctor(String tc,String ad,String sifre) throws SQLException {
	
	String query = "INSERT INTO user (tc, sifre, ad, tip) VALUES (?, ?, ?, ?)";
    boolean key = false;
    try {
        preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, tc); // TC doğru sırada
        preparedStatement.setString(2, sifre); // Şifre ikinci sırada
        preparedStatement.setString(3, ad); // Ad üçüncü sırada
        preparedStatement.setString(4, "doktor"); // Tip "doktor"
        preparedStatement.executeUpdate();
        
        
        key = true;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return key;
}

public boolean deleteDoctor(int doctorId) throws SQLException {
    String query = "DELETE FROM user WHERE id = ?";
    boolean result = false;
    try (PreparedStatement ps = con.prepareStatement(query)) {
        ps.setInt(1, doctorId);  
        int rowsAffected = ps.executeUpdate();  
        if (rowsAffected > 0) {
            result = true;  
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return result;  
}

public boolean addWorker(int user_id, int clinic_id) throws SQLException {
    String query = "INSERT INTO worker "+"(user_id, clinic_id) VALUES" + "(?,?)";
    boolean key = false;
    int count=0;

    try {
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM worker WHERE clinic_id=" + clinic_id + " AND user_id=" + user_id);
        while(rs.next()) {
        	count++;
        }
        if (count==0) {  
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, user_id);  
            preparedStatement.setInt(2, clinic_id);
            preparedStatement.executeUpdate();
        }
        key=true;
    } catch (SQLException e) {
        e.printStackTrace();
    } 
    
    if(key)
    	return true;
    else 
    	return false;

}



}