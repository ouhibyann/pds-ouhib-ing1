package serveur.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	ConnectionPool c;

	public ClientProcessor() {
		try {
			config = LoadProperties.load("src/properties/config.properties");
			Class.forName(config.getProperty("driver"));
			this.url = config.getProperty("url");
			this.user = config.getProperty("user");
			this.password = config.getProperty("password");
				
			c = new ConnectionPool(url, user, password);
			c.create(url, user, password);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	public void run() {
	
		PrintAvailableConnection();
		try {
			serversocket = new ServerSocket(2019);
			socketduserveur = serversocket.accept();
			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socketduserveur.getOutputStream()), true);
			Gson gson = new GsonBuilder().create(); //On initialise l'objet Json
			BufferedReader in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));	
			String received = "";
			String tosend = "";
			
			while(!socketduserveur.isClosed()) {	

				if (in.ready()) {
					//received = in.readLine();
					received = gson.fromJson(in.readLine(), String.class);
					
					if (received == null) {
						socketduserveur.close();
					}
					
					switch(received.toLowerCase()) { //Lorsque le client demande l'envoie, on lui affiche les profils
						
						case "envoie":
							
							System.out.println("Commande envoie detectee");
							tosend = getProfil();
							System.out.println(tosend);
							
							//out.flush();
							//out.close();
							//socketduserveur.close();
							break;
						
						case "close":
							System.err.println("Commande close detectee");
							gson.toJson("La connexion va s'arreter", out);
							out.flush();
							in.close();
							//out.close();
							//socketduserveur.close();
							break;			
					}
					
					out.println(gson.toJson(tosend));
					out.flush();
					out.close();
					tosend = "";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	private String getProfil() {
		try {
			
			String result = "";

			
			String sql = "SELECT \"Profil\", customer_name, shop_bookmarked, customer_id\n" + 
					"	FROM public.\"Profils\";";
			
			java.sql.Connection con = c.getConnection();
			Statement st = con.createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				String profil = rs.getString("profil");
				String shop_name =  rs.getString("shop_bookmarked");  //shop_name
				String customer_name = rs.getString("customer_name");  //customer_name
				int customer_id =  rs.getInt("customer_id"); //customer_id
				
				result += profil + " " + shop_name + " " + customer_name + " " + customer_id + "\n";
				
			}
			Thread.sleep(5000);
			c.releaseConnection(con);
			st.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch(InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void PrintAvailableConnection() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {
						System.out.println("Les connexions disponibles sont au nombre de : " + c.getSize());
						Thread.sleep(2000);
					} catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		});
		
		t.start();
		
	}
	
}

