package com.serviceconnect.dto;

import com.serviceconnect.entity.User;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String email;
    private String nom;
    private String prenom;
    private String role;
    private String specialite;
    private LocalDate dateNaissance;
    private String numeroTel;
    private String adresse;
    private String ville;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .role(user.getRole().name())
                .specialite(user.getSpecialite())
                .dateNaissance(user.getDateNaissance())
                .numeroTel(user.getNumeroTel())
                .adresse(user.getAdresse())
                .ville(user.getVille())
                .build();
    }
}
