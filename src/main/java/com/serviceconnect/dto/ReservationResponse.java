package com.serviceconnect.dto;

import com.serviceconnect.entity.Reservation;
import com.serviceconnect.entity.ReservationStatus;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

    private String id;
    private String reference;
    private String serviceKey;
    private String serviceName;
    private String providerName;
    private String providerInitials;
    private String providerPhoto;
    private Double montant;
    private LocalDateTime dateReservation;
    private LocalDate dateIntervention;
    private String heureSlot;
    private String adresse;
    private String description;
    private ReservationStatus statut;
    private String paymentMethod;
    private Boolean annulable;

    public static ReservationResponse fromEntity(Reservation reservation) {
        String providerNom = reservation.getPrestataire().getNom();
        String providerPrenom = reservation.getPrestataire().getPrenom();
        String providerFullName = providerPrenom + " " + providerNom;
        String initials = (providerPrenom.isBlank() ? "" : String.valueOf(providerPrenom.charAt(0)).toUpperCase())
                + (providerNom.isBlank() ? "" : String.valueOf(providerNom.charAt(0)).toUpperCase());

        return ReservationResponse.builder()
                .id(reservation.getId())
                .reference(reservation.getReference())
                .serviceKey(reservation.getServiceKey() != null ? reservation.getServiceKey() : "")
                .serviceName(reservation.getServiceName() != null ? reservation.getServiceName() : "")
                .providerName(providerFullName)
                .providerInitials(initials)
                .providerPhoto("")
                .montant(reservation.getMontant())
                .dateReservation(reservation.getDateReservation())
                .dateIntervention(reservation.getDateIntervention())
                .heureSlot(reservation.getHeureSlot() != null ? reservation.getHeureSlot() : "")
                .adresse(reservation.getAdresse() != null ? reservation.getAdresse() : "")
                .description(reservation.getDescription() != null ? reservation.getDescription() : "")
                .statut(reservation.getStatut())
                .paymentMethod(reservation.getPaymentMethod() != null ? reservation.getPaymentMethod() : "")
                .annulable(reservation.getAnnulable())
                .build();
    }
}
