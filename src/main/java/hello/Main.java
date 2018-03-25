package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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


        System.out.println("Se hizo la coneccion a la base");


    }
}
