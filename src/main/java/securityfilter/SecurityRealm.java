package securityfilter;

import org.securityfilter.realm.SimpleSecurityRealmBase;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class SecurityRealm extends SimpleSecurityRealmBase{

    @Override
    public boolean booleanAuthenticate(String username, String password) {
        System.out.println("Se hizo la authentication");
        return true;
        //TODO aca preguntaria en la base de datos
    }

    @Override
    public boolean isUserInRole(String username, String rolename) {
        return true;
    }
}
