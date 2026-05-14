package se.jensen.miljana.cloudstoreuserservice.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.miljana.cloudstoreuserservice.model.dto.AuthResponse;
import se.jensen.miljana.cloudstoreuserservice.model.dto.LoginRequest;
import se.jensen.miljana.cloudstoreuserservice.model.dto.RegisterRequest;
import se.jensen.miljana.cloudstoreuserservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody @Valid RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }


}
