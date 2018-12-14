package serveur.host;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Profil {
	
	public Profil() {
		try {
			
			
			Connection c = new Connection();
			
			String result = null;
			String sql = "SELECT \"Profil\", customer_name, shop_bookmarked, customer_id\n" + 
					"	FROM public.\"Profils\";";
			
			Statement st = c.getConnection().createStatement();
			
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				
				String profil = rs.getString("profil");
				String shop_name =  rs.getString("shop_bookmarked");  //shop_name
				String customer_name = rs.getString("customer_name");  //customer_name
				int customer_id =  rs.getInt("customer_id"); //customer_id
				
				result += profil + "" + shop_name + "" + customer_name + "" + customer_id + "\n";
			
			}
			
			System.out.println(result);
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
