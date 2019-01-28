package serveur.host;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool 
  implements IConnectionPool {
 
    private String url;
    private String user;
    private String password;
    private List<java.sql.Connection> connectionPool;
    private List<java.sql.Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;
     
    public ConnectionPool(String url, String user, String password) {}
    public ConnectionPool create(String url, String user, String password) throws SQLException {
  
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new ConnectionPool(url, user, password);
    }
     
    // standard constructors
     
    @Override
    public java.sql.Connection getConnection() {
        java.sql.Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }
     
    @Override
    public boolean releaseConnection(java.sql.Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }
     
    public Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
     
    
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
 
    
}
