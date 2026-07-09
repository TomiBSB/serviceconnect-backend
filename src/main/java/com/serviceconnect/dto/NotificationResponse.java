package com.serviceconnect.dto;

import com.serviceconnect.entity.Notification;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private String id;
    private String titre;
    private String message;
    private LocalDateTime date;
    private Boolean lu;
    private String type;
    private String missionId;
    private String emetteurNom;

    public static NotificationResponse fromEntity(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .titre(notification.getTitre())
                .message(notification.getMessage())
                .date(notification.getDate())
                .lu(notification.getLu())
                .type(notification.getType().name())
                .missionId(notification.getMissionId())
                .emetteurNom(notification.getEmetteurNom())
                .build();
    }
}
