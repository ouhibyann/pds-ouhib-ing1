package serveur.host;

import java.io.IOException;

//import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;

public class ThreadPool {

	private ServerSocket serversocket;
	
	private int nb_connexions = 1;
	private boolean isRunning = true;

	
	public ThreadPool() { //Pool de thread qui s'occupe de gérer les clients 

		try {
			serversocket = new ServerSocket(2019);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void open() { //C'est ici que la gestion des connexions s'effectue

		Thread t = new Thread(new Runnable() { //On lance notre serveur 
			public void run() {
				while(isRunning == true && nb_connexions < 3) {
					try {
					Socket client = serversocket.accept(); //On attend une connexion d'un client
					
					System.out.println("Le client numéro :" +nb_connexions+ "est connecte !"); //une fois reçue, on la traite dans un thread séparé
					Thread t = new Thread(new ClientProcessor(client)); 
					t.start();
					nb_connexions++;
					
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
				
				try {
					serversocket.close();
				} catch (IOException e) {
					e.printStackTrace();
					serversocket = null;
				}
				
			}
		});
		t.start();
	}
	
	public void close() {
		isRunning = false;
	}
}
