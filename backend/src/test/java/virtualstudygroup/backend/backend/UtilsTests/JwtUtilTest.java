package virtualstudygroup.backend.backend.UtilsTests;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import virtualstudygroup.backend.backend.utils.JwtUtil;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;
    private final String secretKey = Base64.getEncoder().encodeToString("abcdefghijklmnopqrstuvwxyz123456".getBytes());
    private final long expirationTime = 1000 * 60 * 60; // 1 sat

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();

        // Postavljanje privatnih polja u klasi JwtUtil koristeći ReflectionTestUtils
        ReflectionTestUtils.setField(jwtUtil, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtUtil, "expirationTime", expirationTime);
    }

    @Test
    void testGenerateToken() {
        String username = "testuser@example.com";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        Claims claims = jwtUtil.extractClaims(token);
        assertEquals(username, claims.getSubject());
    }

    @Test
    void testExtractEmail() {
        String username = "testuser@example.com";
        String token = jwtUtil.generateToken(username);

        String extractedEmail = jwtUtil.extractEmail(token);
        assertEquals(username, extractedEmail);
    }

    @Test
    void testIsTokenValid() {
        String username = "testuser@example.com";
        String token = jwtUtil.generateToken(username);

        assertTrue(jwtUtil.isTokenValid(token, username));
    }



    @Test
    void testInvalidTokenThrowsException() {
        String invalidToken = "invalid.token.here";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> jwtUtil.extractClaims(invalidToken));
        assertTrue(exception.getMessage().contains("Invalid JWT Token"));
    }
}
