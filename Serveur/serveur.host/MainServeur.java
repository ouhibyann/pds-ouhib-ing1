package serveur.host;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainServeur {
	
	
	public static void main(String[] zero) {
		
		
		try {
			

			Gson gson = new GsonBuilder().create(); //On initialise l'objet Json
			ServerSocket serversocket = new ServerSocket(2019, 2); //num_port, nb_max_of_co
			Thread t = new Thread(new ThreadPool(serversocket));
			
			t.start();
			System.out.println("En attente de connexion");
			Socket socketduserveur = serversocket.accept(); 
			
			PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());

			
			Profil p = new Profil();
			gson.toJson(p.getProfil(), out); //Serialisation
			
			out.flush();
			socketduserveur.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
