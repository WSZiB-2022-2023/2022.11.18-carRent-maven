package pl.edu.wszib.car.rent.database;

import pl.edu.wszib.car.rent.model.User;

public class UserDB {
    private final User[] users = new User[2];
    private static final UserDB instance = new UserDB();

    private UserDB() {
        this.users[0] = new User("admin",
                "eb0468abcd9f88e9844fd140fbb6acff", User.Role.ADMIN);
        this.users[1] = new User("janusz",
                "6fff9bb96e12805ea3ccb8ec27e8206f", User.Role.USER);
    }

    public User findByLogin(String login) {
        for(User user : this.users) {
            if(user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public static UserDB getInstance() {
        return instance;
    }
}
