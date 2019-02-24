package Scripts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import properties.LoadProperties;
import serveur.host.ConnectionPool;


public class Delete_Purchases {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Properties config;
		String url;
		String user;
		String password;
		
		try {
			
			config = LoadProperties.load("src/properties/config.properties");
			Class.forName(config.getProperty("driver"));
			url = config.getProperty("url");
			user = config.getProperty("user");
			password = config.getProperty("password");
			
			String sql = "TRUNCATE TABLE public.purchase";
		   
			ConnectionPool pool = new ConnectionPool(url,user,password);
			pool.create(url, user, password);
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
