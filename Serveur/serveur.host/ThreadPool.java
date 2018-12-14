package serveur.host;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadPool implements Runnable{

	private ServerSocket serversocket;
	private Socket socket;
	private int nb_connexions = 1;
	
	public ThreadPool(ServerSocket s) { //Pool de thread qui s'occupe de gérer les clients 
		serversocket = s; 
	}
	
	public void run() {
		try {
			while(true) {
				socket = serversocket.accept();
				System.out.println("Le client numéro :" +nb_connexions+ "est connecte !");
				nb_connexions++;
				socket.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
