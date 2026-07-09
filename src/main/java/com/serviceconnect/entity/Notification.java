package com.serviceconnect.entity;

import com.serviceconnect.entity.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    @Builder.Default
    private Boolean lu = false;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    @Builder.Default
    private NotificationType type = NotificationType.INFO;

    @Column(length = 36)
    private String missionId;

    @Column(length = 100)
    private String emetteurNom;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        date = LocalDateTime.now();
    }
}
