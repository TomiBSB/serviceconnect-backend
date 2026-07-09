package com.serviceconnect.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRequest {

    @NotBlank(message = "Le prestataire est requis")
    private String prestataireId;

    private String reservationId;

    @NotNull(message = "La note est requise")
    @Min(1)
    @Max(5)
    private Integer note;

    private String commentaire;
}
