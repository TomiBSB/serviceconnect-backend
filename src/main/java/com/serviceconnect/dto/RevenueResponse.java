package com.serviceconnect.dto;

import com.serviceconnect.entity.Revenue;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevenueResponse {

    private String id;
    private LocalDate date;
    private String libelle;
    private String missionId;
    private String clientNom;
    private String clientPrenom;
    private Double montant;
    private Double commission;
    private Double net;
    private String statut;

    public static RevenueResponse fromEntity(Revenue revenue) {
        return RevenueResponse.builder()
                .id(revenue.getId())
                .date(revenue.getDate())
                .libelle(revenue.getLibelle())
                .missionId(revenue.getMissionId())
                .clientNom(revenue.getClientNom())
                .clientPrenom(revenue.getClientPrenom())
                .montant(revenue.getMontant())
                .commission(revenue.getCommission())
                .net(revenue.getNet())
                .statut(revenue.getStatut().name())
                .build();
    }
}
