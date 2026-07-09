package com.serviceconnect.dto;

import com.serviceconnect.entity.Reservation;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MissionResponse {

    private String id;
    private String clientNom;
    private String clientPrenom;
    private String service;
    private LocalDateTime date;
    private LocalDate heureDebut;
    private LocalDate heureFin;
    private Double montant;
    private String adresse;
    private String ville;
    private String description;
    private String statut;
    private String clientPhoto;

    public static MissionResponse fromReservation(Reservation reservation) {
        return MissionResponse.builder()
                .id(reservation.getId())
                .clientNom(reservation.getClient().getNom())
                .clientPrenom(reservation.getClient().getPrenom())
                .service(reservation.getServiceName() != null ? reservation.getServiceName() : "")
                .date(reservation.getDateReservation())
                .heureDebut(reservation.getDateIntervention())
                .heureFin(reservation.getDateIntervention())
                .montant(reservation.getMontant())
                .adresse(reservation.getAdresse() != null ? reservation.getAdresse() : "")
                .ville(reservation.getPrestataire().getVille() != null ? reservation.getPrestataire().getVille() : "")
                .description(reservation.getDescription() != null ? reservation.getDescription() : "")
                .statut(reservation.getStatut().name())
                .clientPhoto("")
                .build();
    }
}
