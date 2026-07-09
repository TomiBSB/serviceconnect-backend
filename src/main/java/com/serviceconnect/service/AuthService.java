package com.serviceconnect.service;

import com.serviceconnect.entity.ProviderProfile;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.RoleType;
import com.serviceconnect.dto.LoginRequest;
import com.serviceconnect.dto.RegisterRequest;
import com.serviceconnect.dto.AuthResponse;
import com.serviceconnect.dto.UserResponse;
import com.serviceconnect.repository.ProviderProfileRepository;
import com.serviceconnect.repository.UserRepository;
import com.serviceconnect.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ProviderProfileRepository providerProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMotDePasse()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String token = jwtTokenProvider.generateToken(user.getId(), user.getEmail());
        return AuthResponse.fromUser(user, token);
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Un compte existe déjà avec cet email");
        }

        RoleType role;
        try {
            role = RoleType.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Le rôle '" + request.getRole() + "' est invalide. Utilisez CLIENT ou PRESTATAIRE");
        }

        if (role == RoleType.PRESTATAIRE && (request.getSpecialite() == null || request.getSpecialite().isBlank())) {
            throw new RuntimeException("La spécialité est obligatoire pour un prestataire");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getMotDePasse()))
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .role(role)
                .specialite(request.getSpecialite())
                .dateNaissance(request.getDateNaissance())
                .numeroTel(request.getNumeroTel())
                .adresse(request.getAdresse())
                .ville(request.getVille())
                .build();

        user = userRepository.save(user);

        if (role == RoleType.PRESTATAIRE) {
            ProviderProfile profile = ProviderProfile.builder()
                    .user(user)
                    .build();
            providerProfileRepository.save(profile);
        }

        return UserResponse.fromEntity(user);
    }

    public UserResponse getCurrentUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return UserResponse.fromEntity(user);
    }

    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Aucun compte trouvé avec cet email"));
        // TODO: Send email with reset link
    }

    public void logout(String token) {
        // With stateless JWT, logout is handled client-side by deleting the token.
        // For blacklist approach, we would need a token blacklist store.
    }

    public String refreshToken(User user) {
        return jwtTokenProvider.generateToken(user.getId(), user.getEmail());
    }
}
