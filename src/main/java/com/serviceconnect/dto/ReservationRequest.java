package com.serviceconnect.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequest {

    @NotBlank(message = "Le prestataire est requis")
    private String prestataireId;

    private String serviceKey;
    private String serviceName;

    @NotNull(message = "La date d'intervention est requise")
    private LocalDate dateIntervention;

    private String heureSlot;
    private String adresse;
    private String description;

    @NotNull(message = "Le montant est requis")
    private Double montant;

    private String paymentMethod;
}
