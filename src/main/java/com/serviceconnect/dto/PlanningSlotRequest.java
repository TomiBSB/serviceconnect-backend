package com.serviceconnect.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningSlotRequest {

    @NotNull(message = "La date est requise")
    private LocalDate date;

    @NotNull(message = "L'heure de début est requise")
    private LocalTime heureDebut;

    @NotNull(message = "L'heure de fin est requise")
    private LocalTime heureFin;

    private String type;
}
