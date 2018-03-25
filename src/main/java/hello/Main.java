package hello;

import java.sql.*;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) throws SQLException {

        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        } catch (Exception e) {
            System.err.println("ERROR: failed to load HSQLDB JDBC driver.");
            return;
        }

        final String url = "jdbc:hsqldb:file:/Users/agustinbettati/db/testdb";

        Connection c = DriverManager.getConnection(
                url, "SA", "");

        ResultSet rs = null;
        try {
            PreparedStatement ps = c.prepareStatement("select * from CUSTOMERS");
            rs = ps.executeQuery();
        }
        catch(Exception e){
            System.out.println("Error al ejecutar query");
        }


        while(rs.next()){
            System.out.println("id: " + rs.getString(1) + ", name: " + rs.getString(1)
            + ", age: " + rs.getString(3));
        }


    }
}
