package serveur.host;

import java.io.IOException;

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
				
				Profil p = new Profil();
				gson.toJson(p.getProfil(), out); //Serialisation
				out.flush();
				socketduserveur.close();
			}catch (SocketException e) {
				System.err.println("LA CONNEXION A ETE INTERROMPUE !");
			} catch (IOException e) {
				e.printStackTrace();
			} 
			
		}
		
	}
}
