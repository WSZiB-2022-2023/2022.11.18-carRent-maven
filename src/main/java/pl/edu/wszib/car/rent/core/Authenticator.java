package pl.edu.wszib.car.rent.core;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.car.rent.database.UserDAO;
import pl.edu.wszib.car.rent.model.User;

public class Authenticator {
    final UserDAO userDAO = UserDAO.getInstance();
    private User loggedUser = null;
    private final String seed = "OK4wkjJ15XD@T*41pO9M21t^rLhlt#&9srznHWyo";
    private static final Authenticator instance = new Authenticator();

    private Authenticator() {

    }
    public void authenticate(User user) {
        User userFromDB = this.userDAO.findByLogin(user.getLogin());
        if(userFromDB != null &&
                userFromDB.getPassword().equals(
                        DigestUtils.md5Hex(user.getPassword() + this.seed))) {
            this.loggedUser = userFromDB;
        }
    }

    public static Authenticator getInstance() {
        return instance;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public String getSeed() {
        return seed;
    }
}
