package serveur.host;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import properties.LoadProperties;

public class Connection {
	

	Properties config;
	String url;
	String user;
	String password;
	
	public Connection() {
		try { //On charge les propriétés depuis le fichier texte config
			
			config = LoadProperties.load("src/properties/config.properties");
			Class.forName(config.getProperty("driver"));
			this.url = config.getProperty("url");
			this.user = config.getProperty("user");
			this.password = config.getProperty("password");
			
			
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	

	public java.sql.Connection getConnection() {
		try {
			return DriverManager.getConnection(url, user, password);
		
		} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

}
