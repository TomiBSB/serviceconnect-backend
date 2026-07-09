package com.serviceconnect.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueChartPointResponse {

    private String mois;
    private Double montant;
    private Integer missions;
}
