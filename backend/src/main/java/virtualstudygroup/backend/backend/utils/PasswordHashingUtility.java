package virtualstudygroup.backend.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordHashingUtility {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String hashPassword(String password) {
        return encoder.encode(password);
    }
    public static boolean validatePassword(String password, String hashedPassword) {
        return encoder.matches(password, hashedPassword);
    }
}
