package serveur.host;

//import java.net.InetAddress;

public class MainServeur {
	
	public static void main(String args[]) {
		System.out.println("Serveur initialisé");
		ClientProcessor cp = new ClientProcessor();
		cp.run();
	}
	
}
