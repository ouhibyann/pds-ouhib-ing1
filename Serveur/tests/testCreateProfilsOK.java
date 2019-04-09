package tests;

import serveur.host.ClientProcessor;

class testCreateProfilsOK {
	//Checks that createPorfil is OK
	public static void main(String[] args) {
		

		ClientProcessor cp = new ClientProcessor();
		cp.create();
		System.out.println(cp.getProfil());
	}

}
