package serveur.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ClientProcessor implements Runnable{
	
	private static Socket socketduserveur;
	
	
	public ClientProcessor(Socket Sock) {
		socketduserveur = Sock;
	}

	
	public void run() {
	
		
		while(!socketduserveur.isClosed()) {	
			try {
				PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());
				Gson gson = new GsonBuilder().create(); //On initialise l'objet Json
				BufferedReader in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));	
				String received = in.readLine();
				
				switch(received.toLowerCase()) { //Lorsque le client demande l'envoie, on lui affiche les profils
				
					case "envoie":
						
						System.out.println("Commande envoie detectee");
						Profil p = new Profil();
						gson.toJson(p.getProfil(), out); //Serialisation
						out.flush();
						socketduserveur.close();
						break;
					
					case "close":
						System.err.println("Commande close detectee");
						gson.toJson("La connexion va s'arreter", out);
						out.flush();
						in.close();
						out.close();
						socketduserveur.close();
						break;
				}
				
			}catch (SocketException e) {
				System.err.println("LA CONNEXION A ETE INTERROMPUE !");
				break;
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}
}
