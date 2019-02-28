package serveur.host;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class Profils {
	
	Properties config;
	String url;
	String user;
	String password;
	ConnectionPool c;
	
	private int customer_id;
	private String profil = "";
	
	public Profils(int customer_id, ConnectionPool pool) {

		this.customer_id = customer_id;
		String categories[] = {"food","beaute", "mode", "culture", "maison", "hightech"};
		double L_price[] = new double[6];
		int L_shops[] = new int[6]; 
		//double valeur;
		double freq;
		double magP;
		int magC;
		double price;
		String shop_category;  
		
		try {
			
			c= pool;
			Statement st = c.getConnection().createStatement(); //get a connection from the pool
			ResultSet rs = st.executeQuery("SELECT price, shop_category, shop_name FROM public.purchase WHERE customer_id = " + customer_id);
			int f = 0, b = 0, m = 0, c = 0, mais = 0, high = 0;
			while(rs.next()) { //We get all the purchases and their price for 1 customer
				
			
				//System.out.println(L_price);
				shop_category = rs.getString("shop_category");
				price = rs.getDouble("price");
				switch(shop_category) {
					case "food" :
						f++;
						L_shops[0] = f;
						L_price[0] = price + L_price[0];
						break;
					case "beaute" :
						b++;
						L_shops[1] = b;
						L_price[1] = price + L_price[1];
						break;
					case "mode" :
						m++;
						L_shops[2] = m;
						L_price[2] = price + L_price[2];
						break;
					case "culture":
						c++;
						L_shops[3] = c;
						L_price[3] = price + L_price[3];
						break;
					case "maison":
						mais++;
						L_shops[4] = mais;
						L_price[4] = price + L_price[4];
						break;
					case "hightech":
						high++;
						L_shops[5] = high;
						L_price[5] = price + L_price[5];
						break;
				}


			}
			//System.out.println(Arrays.toString(L_price)+""+Arrays.toString(L_shops));
			//valeur = (max(L_price)/sum(L_price));
			magP = find(L_price, max(L_price)); 
			freq = max(L_shops)/sum(L_shops);
			magC = find(L_shops, max(L_shops));
			/*System.out.println("valeur =" +valeur);
			System.out.println("freq =" +freq);
			System.out.println(magP);
			System.out.println(magC);
			*/
			if(magP == magC) { //there is a match, so no need to choose
				profil = categories[magC];
			} else if(freq < 0.5) { //we focus on prices if frequency is less than 0.5 
				profil = categories[(int)magP];
			} else if(freq >= 0.5) { //we focus on frequency if greater than 0.5
				profil = categories[magC];
			} 
			
			//System.out.print(profil);
		} catch(SQLException e) {
			e.getMessage();
		}

	}
	
	public String getProfil() {
		return profil;
	}
	
	public int getCustomer_id() {
		return customer_id;
	}
	
	
	private double max(double Array[]) {
		double max = 0;
		for(int i = 0; i < Array.length; i++) {
			if(Array[i] > max) {
				max = Array[i];
			}
		}
		return max;
	}

	private double find(double[] a, double target) {
		
		for (int i = 0; i < a.length; i++) {
			if (a[i] == target)
				return i;
		}
		return -1;
		
	}
	
	/*
	private double sum(double Array[]) {
		double sum = 0;
			for(int i = 0; i < Array.length; i++) {
				sum = Array[i] + sum;
			}
		return sum;
	}
	*/
	
	private int max(int Array[]) {
		int max = 0;
		for(int i = 0; i < Array.length; i++) {
			if(Array[i] > max) {
				max = Array[i];
			}
		}
		return max;
	}
	
	
	private int sum(int Array[]) {
		int sum = 0;
			for(int i = 0; i < Array.length; i++) {
				sum = Array[i] + sum;
			}
		return sum;
	}
	
	
	private int find(int[] a, int target) {
		
		for (int i = 0; i < a.length; i++) {
			if (a[i] == target)
				return i;
		}
		return -1;
		
	}

}
