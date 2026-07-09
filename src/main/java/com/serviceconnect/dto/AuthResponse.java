package com.serviceconnect.dto;

import com.serviceconnect.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    private String id;
    private String email;
    private String nom;
    private String prenom;
    private String role;
    private String specialite;

    public static AuthResponse fromUser(User user, String token) {
        return AuthResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .role(user.getRole().name())
                .specialite(user.getSpecialite())
                .build();
    }
}
