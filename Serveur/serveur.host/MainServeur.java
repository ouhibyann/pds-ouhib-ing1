package serveur.host;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MainServeur {
	
	public static void main(String[] zero) {
		
		
		try {
		
			ServerSocket serversocket = new ServerSocket(2019, 2); //num_port, nb_max_of_co
			Thread t = new Thread(new ThreadPool(serversocket)); //on initialise le Pool de Thread avec notre Serveur socket
			t.start();
			System.out.println("En attente de connexion");
			Socket socketduserveur = serversocket.accept(); 
			PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());

			Connection c = new Connection();
			
			out.println("Vous êtes connecte !"); //on affiche à l'utilisateur qu'on est bien co
	       
			
			out.flush();    
			serversocket.close();
	        socketduserveur.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
