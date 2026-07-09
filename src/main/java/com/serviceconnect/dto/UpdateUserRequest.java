package com.serviceconnect.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserRequest {

    private String nom;
    private String prenom;
    private String specialite;
    private LocalDate dateNaissance;
    private String numeroTel;
    private String adresse;
    private String ville;
}
