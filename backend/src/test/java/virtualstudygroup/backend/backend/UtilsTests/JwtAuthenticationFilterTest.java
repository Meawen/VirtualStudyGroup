package virtualstudygroup.backend.backend.UtilsTests;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import virtualstudygroup.backend.backend.utils.JwtAuthenticationFilter;
import virtualstudygroup.backend.backend.utils.JwtUtil;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

class JwtAuthenticationFilterTest {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsService);
        SecurityContextHolder.clearContext(); // Clear SecurityContext before each test
    }

    @Test
    void testValidJwtToken() throws ServletException, IOException {
        String token = "valid-jwt-token";
        String email = "test@example.com";
        String authHeader = "Bearer " + token;

        // Mock UserDetails
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getAuthorities()).thenReturn(List.of());

        // Mock JwtUtil behavior
        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtUtil.extractEmail(token)).thenReturn(email);
        when(jwtUtil.isTokenValid(token, email)).thenReturn(true);

        // Mock UserDetailsService behavior
        when(userDetailsService.loadUserByUsername(email)).thenReturn(mockUserDetails);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify JwtUtil methods were called
        verify(jwtUtil).extractEmail(token);
        verify(jwtUtil).isTokenValid(token, email);

        // Verify SecurityContext contains authentication
        SecurityContext securityContext = SecurityContextHolder.getContext();
        assert securityContext.getAuthentication() != null : "Expected authentication to be set in SecurityContext.";

        // Verify the filter chain continues
        verify(filterChain).doFilter(request, response);
    }


    @Test
    void testInvalidJwtToken() throws ServletException, IOException {
        String token = "invalid-jwt-token";
        String authHeader = "Bearer " + token;

        when(request.getHeader("Authorization")).thenReturn(authHeader);
        when(jwtUtil.extractEmail(token)).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil).extractEmail(token);
        verifyNoInteractions(userDetailsService);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        assert securityContext.getAuthentication() == null : "Expected no authentication in SecurityContext.";

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testNoAuthorizationHeader() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtUtil, userDetailsService);

        SecurityContext securityContext = SecurityContextHolder.getContext();
        assert securityContext.getAuthentication() == null : "Expected no authentication in SecurityContext.";

        verify(filterChain).doFilter(request, response);
    }
}
