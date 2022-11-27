package pl.edu.wszib.car.rent;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.car.rent.core.Authenticator;
import pl.edu.wszib.car.rent.model.User;

public class App2 {
    public static void main(String[] args) {
        String hash = DigestUtils.md5Hex("janusz" + Authenticator.getInstance().getSeed());

        System.out.println(hash);

        //User user = new User("admin", "asdlui43iu4hj5", User.Role.ADMIN);

        User.Role role = User.Role.ADMIN;
        User.Role role2 = User.Role.USER;

        /*Console ps5 = Console.PS5;
        System.out.println(ps5.getBrand());
        System.out.println(ps5.getModel());
        System.out.println(ps5.getYear());*/

        Outer.Inner zmienna = new Outer.Inner();
        Outer.Inner zmienna2;

        Outer.a = 5;
    }
}
