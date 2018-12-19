package client.host;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.Gson;


public class MainClient {

	public static void main(String[] args) {
	
		Gson gson = new Gson();
		
		try {
			
		    Socket socket = new Socket(InetAddress.getLocalHost(),2019); //Ici, on est en local donc l'adresse IP s'obtient via getLocalHost
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		    String received = gson.fromJson(in, String.class);
		    System.out.println(received); //on récupère ce que le serveur nous envoie 
		    socket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
