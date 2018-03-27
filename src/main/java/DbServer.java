import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;

import java.io.IOException;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class DbServer {

//    La base de datos tambien se puede levantar desde la terminal
//    usando:
//    java -cp hsqldb.jar org.hsqldb.server.Server --dbname.0 cursago --database.0 file:~/db/rsago;user=cursago;password=cursago
//    corre esa linea estando en un directorio donde tenes a tu .jar de hsqldb

    public static void main(String[] args) throws IOException, ServerAcl.AclFormatException {

        final String URL = "file:~/db/cursago";
        String user = "cursago";
        String password = "cursago";
        HsqlProperties p = new HsqlProperties();
        p.setProperty("server.database.0",URL+";user="+user+";password="+password);
        p.setProperty("server.dbname.0","cursago");

        Server server = new Server();
        server.setProperties(p);
        server.setLogWriter(null); // can use custom writer
        server.setErrWriter(null); // can use custom writer
        server.start();
        System.out.println("Database is running with path: " + URL);
        System.out.println("Username: " + user+", Password: " + password);

    }
}
