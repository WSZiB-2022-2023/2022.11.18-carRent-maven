package pl.edu.wszib.car.rent;

import org.apache.commons.codec.digest.DigestUtils;
import pl.edu.wszib.car.rent.core.Authenticator;

public class App2 {
    public static void main(String[] args) {
        String hash = DigestUtils.md5Hex("janusz" + Authenticator.seed);

        System.out.println(hash);
    }
}
