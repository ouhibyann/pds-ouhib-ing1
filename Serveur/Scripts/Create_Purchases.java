package Scripts;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import serveur.host.ConnectionPool;

public class Create_Purchases {

	static Random r = new Random();
	
	private static int getRandomNumberInRange(int min, int max) {
		
		
		
		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		
		return r.nextInt((max - min) + 1) + min;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String categories[] = {"food","beaute", "mode", "culture", "maison", "hightech"};
		String magasins_food[] = {"quick", "burgerKing", "Mcdo", "DelArte", "Vapiano"};
		String magasins_beaute[] = {"optic2000", "parashop", "franck provost", "Afflelou", "sephora"};
		String magasins_mode[] = {"nike", "h&m", "zara", "adidas", "reebook"};
		String magasins_culture[] = {"fnac", "jd", "la grande recree", "king jouet", "maxxigame" };
		String magasins_maison[] = {"hema", "la chaise longue", "petland", "maison du monde", "monceau fleur"};
		String magasins_hightech[] = {"free", "bouygues", "darty", "sfr", "orange", "boulanger" };
		
		 try {
			 
			Class.forName("org.postgresql.Driver");
			ConnectionPool pool = new ConnectionPool();
							
			String sql = "INSERT INTO public.purchase(\r\n" + 
			   		"	shop_category, shop_name, customer_id, price, date)\r\n" + 
			   		"	VALUES (?, ?, ?, ?, ?);";
			Connection conn = pool.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			for (int i = 1; i <= 10; i++) {
				int j = 0;
				while (j < 100) { //on génère l'équivalent de 100 achats (par client)
					String c = categories[getRandomNumberInRange(0,5)];
					st.setObject(1, c);
					switch(c) {
				   
					case "food":
						st.setObject(2, magasins_food[getRandomNumberInRange(0,4)]);
					break;
					case "beaute":
						st.setObject(2, magasins_beaute[getRandomNumberInRange(0,4)]);
					break;
					case "mode": 
						st.setObject(2, magasins_mode[getRandomNumberInRange(0,4)]);
					break;
					case "culture":
						st.setObject(2, magasins_culture[getRandomNumberInRange(0,4)]);
					break;
					case "maison":
						st.setObject(2, magasins_maison[getRandomNumberInRange(0,4)]);
					break;
					case "hightech":
						st.setObject(2, magasins_hightech[getRandomNumberInRange(0,4)]);
					break;
					}
					st.setObject(3, i);
					st.setObject(4, getRandomNumberInRange(0,1000));
					st.setObject(5, r.nextInt(30));
					st.executeUpdate();
					j++;
				}
			} 
			System.out.println("Les achats sont en cours");
			st.close();
	 	} catch(SQLException e) {
	 		e.printStackTrace();
	 	} catch (ClassNotFoundException e) {
	 		e.getMessage();
	 	} 
	}

}
