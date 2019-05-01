package serveur.host;


import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class MainServeur {

    private ServerSocket socketserver;

    //Init the server here is easier for multiThreading handling
    private MainServeur() throws IOException, SQLException, ClassNotFoundException {
        socketserver = new ServerSocket(1042);
        DAO.initPool();
    }

    //we create a seperated thread for each client
    private void AcceptConnection() throws IOException {
            new Thread(new ClientProcessor(this.socketserver.accept())).start();
    }

    //accept until the server socket is closed, which should never happen
    private void run() throws IOException {
        while(!socketserver.isClosed()) AcceptConnection();
    }

    //launching the server
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Le serveur est initialisé");
    	new MainServeur().run();
    }
}
