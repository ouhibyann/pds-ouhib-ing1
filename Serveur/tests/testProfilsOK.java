package tests;

import serveur.host.ClientProcessor;
import serveur.host.Profils;

class testProfilsOK {

	//checks that getProfil is OK
	public static void main(String[] args) {
		
		//Should return a correct profil
		//We test the  get of a Client with the id 4 (in our DB it's a 'maison' profil)
		ClientProcessor cp = new ClientProcessor();
		Profils p = new Profils(4, cp.getC());
		System.out.println(p.getProfil());
		
	}

}
