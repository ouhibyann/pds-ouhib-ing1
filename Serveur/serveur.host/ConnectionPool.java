package serveur.host;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import properties.LoadProperties;

public class ConnectionPool implements IConnectionPool {

    private List<java.sql.Connection> connectionPool;
    private static int INITIAL_POOL_SIZE = 2;

    //loading properties here is more common sense
    public ConnectionPool() throws SQLException, ClassNotFoundException {
        Properties config = LoadProperties.load("src/properties/config.properties");

        Class.forName(config.getProperty("driver"));

        connectionPool = new ArrayList<>(INITIAL_POOL_SIZE);

        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            connectionPool.add(DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("user"),
                    config.getProperty("password"))
            );
        }
    }


    public Connection getConnection() {
        return connectionPool.remove(connectionPool.size()-1);
    }


    public boolean releaseConnection(Connection connection) {
        if (connectionPool.size() < INITIAL_POOL_SIZE) {
            connectionPool.add(connection);
            return true;
        }
        return false;
    }

    //the method speak for itself
    public void PrintAvailableConnection() {
        new Thread(() -> {
            while(true) {
                try {
                    System.out.println("Les connexions disponibles sont au nombre de : " + connectionPool.size());
                    Thread.sleep(2000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
