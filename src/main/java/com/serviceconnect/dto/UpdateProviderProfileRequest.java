package com.serviceconnect.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProviderProfileRequest {

    private String description;
    private String competences;
    private String certifications;
    private Boolean disponible;
    private String pays;
    private String specialite;
}
