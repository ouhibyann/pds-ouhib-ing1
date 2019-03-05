package tests;

import serveur.host.ClientProcessor;
import serveur.host.Profils;

class testProfilsKO {

	public static void main(String[] args) {
		
		//Should return an error
		ClientProcessor cp = new ClientProcessor();
		Profils p = new Profils(0,cp.getC());
		System.out.println(p.getProfil());
	}

}
