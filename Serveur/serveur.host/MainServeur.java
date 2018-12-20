package serveur.host;

//import java.net.InetAddress;

public class MainServeur {
	
	public static void main(String args[]) {
		ThreadPool tp = new ThreadPool();
		tp.open();
		System.out.println("Serveur initialisé");
		
	}
}
