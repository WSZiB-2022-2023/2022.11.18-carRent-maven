package pl.edu.wszib.car.rent.core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.car.rent.database.UserDB;
import pl.edu.wszib.car.rent.model.User;

public class Authenticator {
    public static User loggedUser = null;
    public static final String seed = "OK4wkjJ15XD@T*41pO9M21t^rLhlt#&9srznHWyo";
    public static void authenticate(User user, UserDB userDB) {
        User userFromDB = userDB.findByLogin(user.getLogin());
        if(userFromDB != null &&
                userFromDB.getPassword().equals(
                        DigestUtils.md5Hex(user.getPassword() + seed))) {
            loggedUser = userFromDB;
        }
    }
}
