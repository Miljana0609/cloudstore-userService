package se.jensen.miljana.cloudstoreuserservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import se.jensen.miljana.cloudstoreuserservice.model.dto.AuthResponse;
import se.jensen.miljana.cloudstoreuserservice.model.dto.LoginRequest;
import se.jensen.miljana.cloudstoreuserservice.model.dto.RegisterRequest;
import se.jensen.miljana.cloudstoreuserservice.model.user.AppUser;
import se.jensen.miljana.cloudstoreuserservice.repository.AppUserRepository;
import se.jensen.miljana.cloudstoreuserservice.security.JwtService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldReturnTokenWhenUserDoesNotExist() {
        RegisterRequest request = new RegisterRequest("Test User", "test@test.com", "password123");

        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(jwtService.generateToken("test@test.com")).thenReturn("jwt-token");

        AuthResponse response = authService.register(request);

        assertEquals("jwt-token", response.token());
        assertEquals("test@test.com", response.email());
        verify(userRepository).save(any(AppUser.class));
    }

    @Test
    void register_shouldThrowWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest("Test User", "test@test.com", "password123");

        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> authService.register(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_shouldReturnTokenWhenCredentialsAreValid() {
        LoginRequest request = new LoginRequest("test@test.com", "password123");

        when(jwtService.generateToken("test@test.com")).thenReturn("jwt-token");

        AuthResponse response = authService.login(request);

        assertEquals("jwt-token", response.token());
        assertEquals("test@test.com", response.email());
    }
}

