package com.serviceconnect.controller;

import com.serviceconnect.entity.User;
import com.serviceconnect.dto.ForgotPasswordRequest;
import com.serviceconnect.dto.LoginRequest;
import com.serviceconnect.dto.RegisterRequest;
import com.serviceconnect.dto.AuthResponse;
import com.serviceconnect.dto.UserResponse;
import com.serviceconnect.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/connexion")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken())
                .header("X-Auth-Token", response.getToken())
                .body(response);
    }

    @PostMapping("/inscription/client")
    public ResponseEntity<UserResponse> registerClient(@Valid @RequestBody RegisterRequest request) {
        request.setRole("CLIENT");
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/inscription/prestataire")
    public ResponseEntity<UserResponse> registerProvider(@Valid @RequestBody RegisterRequest request) {
        request.setRole("PRESTATAIRE");
        UserResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal User user) {
        UserResponse response = authService.getCurrentUser(user.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value = "Authorization", required = false) String token) {
        if (token != null && token.startsWith("Bearer ")) {
            authService.logout(token.substring(7));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@AuthenticationPrincipal User user) {
        String token = authService.refreshToken(user);
        AuthResponse response = AuthResponse.fromUser(user, token);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .header("X-Auth-Token", token)
                .body(response);
    }
}
