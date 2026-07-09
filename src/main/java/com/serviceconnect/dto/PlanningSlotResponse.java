package com.serviceconnect.dto;

import com.serviceconnect.entity.PlanningSlot;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningSlotResponse {

    private String id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String missionId;
    private String missionTitre;
    private String clientNom;
    private String adresse;
    private String type;

    public static PlanningSlotResponse fromEntity(PlanningSlot slot) {
        return PlanningSlotResponse.builder()
                .id(slot.getId())
                .date(slot.getDate())
                .heureDebut(slot.getHeureDebut())
                .heureFin(slot.getHeureFin())
                .missionId(slot.getMissionId())
                .missionTitre(slot.getMissionTitre())
                .clientNom(slot.getClientNom())
                .adresse(slot.getAdresse())
                .type(slot.getType().name())
                .build();
    }
}
