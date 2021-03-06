package client.host;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.google.gson.Gson;

import ihm.Fenetre;


public class MainClient {

	public static void main(String[] args) {
	
		Gson gson = new Gson();
		
		try {
			
			//Socket socket = new Socket(InetAddress.getLocalHost(),1042);
			//System.out.println(socket.getInetAddress());
			InetSocketAddress inet = new InetSocketAddress("192.168.20.13",1042);
			Socket socket = new Socket(); //Ici, on n'est pas en local donc l'adresse IP s'obtient via getLocalHost
		    System.out.println(inet.getHostName());
		    socket.connect(inet);
			
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    BufferedWriter out = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
		    Fenetre f = new Fenetre();
		    
		    f.getJbProfil().addMouseListener(new MouseListener() {
		    	
		    	public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				
				public void mouseClicked(MouseEvent e) {
					
					String r = "";
					try {
						out.write("envoie \n");
						out.flush();
						r = in.readLine();
						String received = gson.fromJson(r, String.class);//On desérialise
						f.getshowProfil().setText(received);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
		    	
		    });
				
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
