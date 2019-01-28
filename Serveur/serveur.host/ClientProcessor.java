package serveur.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import properties.LoadProperties;


public class ClientProcessor extends Thread{
	
	private Socket socketduserveur = new Socket();
	private ServerSocket serversocket;
	Properties config;
	String url;
	String user;
	String password;


	public ClientProcessor() {
		
	}
	
	public void run() {
	
		try {
			serversocket = new ServerSocket(2019);
			socketduserveur = serversocket.accept();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		while(!socketduserveur.isClosed()) {	
			try {
				PrintWriter out = new PrintWriter(socketduserveur.getOutputStream());
				Gson gson = new GsonBuilder().create(); //On initialise l'objet Json
				BufferedReader in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));	
				String received = in.readLine();
				
				switch(received.toLowerCase()) { //Lorsque le client demande l'envoie, on lui affiche les profils
				
					case "envoie":
						
						System.out.println("Commande envoie detectee");
						gson.toJson(getProfil(), out); //Serialisation
						out.flush();
						//out.close();
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
	
	private String getProfil() {
		try {
			
			String result = "";
			try {
				
				config = LoadProperties.load("src/properties/config.properties");
				Class.forName(config.getProperty("driver"));
				this.url = config.getProperty("url");
				this.user = config.getProperty("user");
				this.password = config.getProperty("password");
				
				
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
			}
			ConnectionPool c = new ConnectionPool(url, user, password);
			c.create(url, user, password);
			
			String sql = "SELECT \"Profil\", customer_name, shop_bookmarked, customer_id\n" + 
					"	FROM public.\"Profils\";";
			
			Statement st = ((java.sql.Connection) c.createConnection(c.getUrl(), c.getUser(), c.getPassword())).createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				String profil = rs.getString("profil");
				String shop_name =  rs.getString("shop_bookmarked");  //shop_name
				String customer_name = rs.getString("customer_name");  //customer_name
				int customer_id =  rs.getInt("customer_id"); //customer_id
				
				result += profil + " " + shop_name + " " + customer_name + " " + customer_id + "\n";
				
			}
			st.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

