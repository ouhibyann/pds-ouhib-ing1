package client.host;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;


public class MainClient {

	public static void main(String[] args) {
	
		Gson gson = new Gson();
		
		try {
			InetSocketAddress inet = new InetSocketAddress("192.168.20.13",1042);
			Socket socket = new Socket(); //Ici, on n'est pas en local donc l'adresse IP s'obtient via getLocalHost
		    System.out.println(inet.getHostName());
		    socket.connect(inet);
			
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
			    	sc.close();
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
