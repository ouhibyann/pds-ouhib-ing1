package tests;

import org.codehaus.jackson.map.ObjectMapper;

import serveur.host.ThreadPool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestMockServeur {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

				
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValueAsString(new File("D:\\Yann\\Téléchargements"));
			
			
			ServerSocket serversocket = new ServerSocket(2019, 2); //num_port, nb_max_of_co
			//Thread t = new Thread(new ThreadPool(serversocket)); //on initialise le Pool de Thread avec notre Serveur socket
			System.out.println("En attente de connexion");
			
			Socket socketduserveur = serversocket.accept(); 
			PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());

			//t.start();
			

			String toClient = mapper.writeValueAsString(null); //On sereialise
			out.write(toClient); //on envoie au client 
			out.flush();  
	        socketduserveur.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
