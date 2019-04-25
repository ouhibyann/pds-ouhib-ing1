package Scripts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import serveur.host.ConnectionPool;


public class Delete_Purchases {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			
			String sql = "TRUNCATE TABLE public.purchase";
		   
			ConnectionPool pool = new ConnectionPool();
			Connection conn = pool.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.executeQuery();
			System.out.println("Les achats ont été effacés");
			st.close();

		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.getMessage();
		}
	}

}
