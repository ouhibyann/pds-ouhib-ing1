package tests;

import java.util.Properties;
import properties.LoadProperties;
import serveur.host.ConnectionPool;
import static org.junit.Assert;

class TestConnectionPool {
	
	public TestConnectionPool() {
	Properties config = LoadProperties.load("src/properties/config.properties");
	Class.forName(config.getProperty("driver"));
	String url = config.getProperty("url");
	String user = config.getProperty("user");
	String password = config.getProperty("password");
	
	ConnectionPool con = new ConnectionPool(url, user, password);
	con.create(url, user, password);
	
	assertTrue aT = new assertTrue("ConnectionPool okay", con.getConnection().isValid(1));
	
	}
}
