package serveur.host;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import properties.LoadProperties;

public class Connection {
	
	
	Properties config;
	String url;
	String user;
	String password;
	
	public Connection() {
		
				
			try {
				
				config = LoadProperties.load("src/properties/config.properties");
				Class.forName(config.getProperty("driver"));
				this.url = config.getProperty("url");
				this.user = config.getProperty("user");
				this.password = config.getProperty("password");
	
				java.sql.Connection con = DriverManager.getConnection(url, user, password);
				
				String result = null;
				String sql = "SELECT \"Profil\", customer_name, shop_bookmarked, customer_id\n" + 
						"	FROM public.\"Profils\";";
				
				Statement req = con.createStatement();
				
				ResultSet rs = req.executeQuery(sql);
				while(rs.next()) {
					
					String profil = rs.getString("profil");
					String shop_name =  rs.getString("shop_bookmarked");  //shop_name
					String customer_name = rs.getString("customer_name");  //customer_name
					int customer_id =  rs.getInt("customer_id"); //customer_id
					
					result += profil + "" + shop_name + "" + customer_name + "" + customer_id + "\n";
					
				}
				System.out.println(result);
				} catch(ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
