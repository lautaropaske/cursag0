package securityfilter;

import org.securityfilter.realm.SimpleSecurityRealmBase;

/**
 * @author Agustin Bettati
 * @version 1.0
 */
public class SecurityRealm extends SimpleSecurityRealmBase{

    @Override
    public boolean booleanAuthenticate(String username, String password) {
        //TODO aca preguntaria en la base de datos
        if("registeredUser@gmail.com".equals(username))
            return true;

        return false;

    }

    @Override
    public boolean isUserInRole(String username, String rolename) {
        return true;
    }
}
