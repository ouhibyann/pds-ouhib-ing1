package tests;

import serveur.host.ClientProcessor;
import serveur.host.Profils;

class testProfilsKO {

	//checks that getProfil is OK
	public static void main(String[] args) {
		
		//Should return an error because there is no client with the id 0
		ClientProcessor cp = new ClientProcessor();
		Profils p = new Profils(0,cp.getC());
		System.out.println(p.getProfil());
	}

}
