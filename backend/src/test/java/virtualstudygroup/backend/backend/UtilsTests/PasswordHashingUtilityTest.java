package virtualstudygroup.backend.backend.UtilsTests;

import org.junit.jupiter.api.Test;
import virtualstudygroup.backend.backend.utils.PasswordHashingUtility;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashingUtilityTest {

    @Test
    void testHashPassword() {
        String rawPassword = "password123";
        String hashedPassword = PasswordHashingUtility.hashPassword(rawPassword);

        // Provjeravamo da hash nije null niti prazan
        assertNotNull(hashedPassword);
        assertFalse(hashedPassword.isEmpty());
        // Hash ne bi smio biti jednak sirovom passwordu
        assertNotEquals(rawPassword, hashedPassword);
    }

    @Test
    void testValidatePassword() {
        String rawPassword = "password123";
        String hashedPassword = PasswordHashingUtility.hashPassword(rawPassword);

        // Provjeravamo valjanost ispravnog passworda
        assertTrue(PasswordHashingUtility.validatePassword(rawPassword, hashedPassword));

        // Provjeravamo valjanost pogrešnog passworda
        assertFalse(PasswordHashingUtility.validatePassword("wrongPassword", hashedPassword));
    }

    @Test
    void testHashPasswordConsistency() {
        String rawPassword = "password123";

        // Hashiranje iste lozinke više puta daje različite hashove
        String hash1 = PasswordHashingUtility.hashPassword(rawPassword);
        String hash2 = PasswordHashingUtility.hashPassword(rawPassword);

        // Hashovi ne smiju biti jednaki zbog random salta
        assertNotEquals(hash1, hash2);
    }
}
