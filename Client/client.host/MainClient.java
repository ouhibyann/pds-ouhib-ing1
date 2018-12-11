package client.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient {

	public static void main(String[] args) {

		try {
			
		    Socket socket = new Socket(InetAddress.getLocalHost(),2019); //Ici, on est en local donc l'adresse IP s'obtient via getLocalHost
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    System.out.println(in.readLine()); //on récupère ce que le serveur nous envoie 
	        
		    socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
