package client.host;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;


public class MainClient {

	public static void main(String[] args) {
	
		Gson gson = new Gson();
		
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(),2019); //Ici, on est en local donc l'adresse IP s'obtient via getLocalHost
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    PrintWriter out = new PrintWriter(socket.getOutputStream());
		    Scanner sc  = new Scanner(System.in);
		    
				while (!socket.isClosed()) {
			    
			    System.out.println("Passez votre commande"); 
			    String commande = sc.nextLine(); // On demande au serveur de nous envoyer les profils
			    out.write(commande +"\n");
			    out.flush();
			    
			    String received = gson.fromJson(in, String.class); //On desérialise
			    System.out.println(received); //on récupère ce que le serveur nous envoie 
			    
			    //sc.close();
			    if (received == null) {
			    	//System.out.println("fin");
			    	socket.close();
			    	}
				}
				
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
