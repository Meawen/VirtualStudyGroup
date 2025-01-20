package virtualstudygroup.backend.backend.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import virtualstudygroup.backend.backend.dto.AuthResponse;
import virtualstudygroup.backend.backend.dto.LoginRequest;
import virtualstudygroup.backend.backend.models.User;
import virtualstudygroup.backend.backend.repo.UserRepository;
import virtualstudygroup.backend.backend.service.UserService;
import virtualstudygroup.backend.backend.utils.JwtUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        Authentication authMock = Mockito.mock(Authentication.class);
        org.springframework.security.core.userdetails.User mockUser =
                new org.springframework.security.core.userdetails.User(
                        loginRequest.getEmail(), "password", new ArrayList<>());

        Mockito.when(authMock.getPrincipal()).thenReturn(mockUser);
        Mockito.when(authenticationManager.authenticate(Mockito.any())).thenReturn(authMock);
        Mockito.when(jwtUtil.generateToken(Mockito.anyString())).thenReturn("dummyToken");

        ResponseEntity<?> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("dummyToken", ((AuthResponse) response.getBody()).getToken());
    }


    @Test
    public void testRegisterUser() {
        Map<String, String> request = Map.of("email", "test@example.com", "password", "password");

        User createdUser = new User();
        createdUser.setId(1);
        createdUser.setEmail("test@example.com");
        createdUser.setName("John Doe");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        Mockito.when(userService.create(Mockito.any(User.class))).thenReturn(createdUser);
        Mockito.when(jwtUtil.generateToken(createdUser.getEmail())).thenReturn("dummyToken");

        ResponseEntity<Map<String, Object>> response = authController.registerUser(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().containsKey("user"));
        assertEquals("dummyToken", response.getBody().get("token"));
    }

}
