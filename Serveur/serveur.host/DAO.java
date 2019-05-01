package serveur.host;

import java.sql.*;

public class DAO {

    private static ConnectionPool connectionPool;

    // Connection pool init
    static void initPool() throws SQLException, ClassNotFoundException {
        connectionPool = new ConnectionPool();
    }

    public static void create() {
        //Computation of profils
        int c_id;
        int i =1;
        try {
            Connection con = connectionPool.getConnection();
            Statement st = con.createStatement();
            st.execute("DELETE FROM public.\"Profils\";");
            ResultSet rs1 = st.executeQuery("SELECT customer_id FROM public.purchase");
            PreparedStatement ps = con.prepareStatement("INSERT INTO public.\"Profils\"(\r\n" +
                    "	\"Profil\", customer_id)\r\n" +
                    "	VALUES (?, ?);");

            while(rs1.next()) {
                c_id = rs1.getInt(1);
                if(c_id == i) {
                    System.out.println(c_id);
                    Profils p = new Profils(c_id);
                    System.out.println(p.getProfil());
                    ps.setString(1, p.getProfil());
                    ps.setInt(2, c_id);
                    i++;
                    ps.executeUpdate();
                    connectionPool.releaseConnection(con);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getProfil() {
        try {

            String result = "";


            String sql = "SELECT \"Profil\", customer_name, shop_bookmarked, customer_id\n" +
                    "	FROM public.\"Profils\";";


            Connection con = connectionPool.getConnection();
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
            connectionPool.releaseConnection(con);
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

}
