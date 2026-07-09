package com.serviceconnect.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueSummaryResponse {

    private Double totalMois;
    private Double totalAnnee;
    private Double totalGlobal;
    private Integer missionsMois;
    private Integer missionsAnnee;
    private Double progression;
    private Double moyenneParMission;
}
