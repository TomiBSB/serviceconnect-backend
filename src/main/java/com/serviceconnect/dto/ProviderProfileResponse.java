package com.serviceconnect.dto;

import com.serviceconnect.entity.ProviderProfile;
import com.serviceconnect.entity.User;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderProfileResponse {

    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String photoUrl;
    private String specialite;
    private Double note;
    private Integer missionsRealisees;
    private Integer missionsTotal;
    private Double tauxSatisfaction;
    private Double revenuMensuel;
    private Double revenuTotal;
    private String adresse;
    private String ville;
    private String pays;
    private String competences;
    private String description;
    private String certifications;
    private LocalDateTime dateInscription;
    private Boolean disponible;

    public static ProviderProfileResponse fromEntity(ProviderProfile profile, User user) {
        return ProviderProfileResponse.builder()
                .id(profile.getId())
                .nom(user.getNom())
                .prenom(user.getPrenom())
                .email(user.getEmail())
                .phone(user.getNumeroTel() != null ? user.getNumeroTel() : "")
                .photoUrl("")
                .specialite(user.getSpecialite() != null ? user.getSpecialite() : "")
                .note(profile.getNote())
                .missionsRealisees(profile.getMissionsRealisees())
                .missionsTotal(profile.getMissionsTotal())
                .tauxSatisfaction(profile.getTauxSatisfaction())
                .revenuMensuel(profile.getRevenuMensuel())
                .revenuTotal(profile.getRevenuTotal())
                .adresse(user.getAdresse() != null ? user.getAdresse() : "")
                .ville(user.getVille() != null ? user.getVille() : "")
                .pays(profile.getPays() != null ? profile.getPays() : "")
                .competences(profile.getCompetences() != null ? profile.getCompetences() : "")
                .description(profile.getDescription() != null ? profile.getDescription() : "")
                .certifications(profile.getCertifications() != null ? profile.getCertifications() : "")
                .dateInscription(profile.getDateInscription())
                .disponible(profile.getDisponible())
                .build();
    }
}
