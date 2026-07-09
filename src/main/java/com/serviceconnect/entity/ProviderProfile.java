package com.serviceconnect.entity;

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
@Table(name = "provider_profiles")
public class ProviderProfile {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    @Builder.Default
    private Double note = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Integer missionsRealisees = 0;

    @Column(nullable = false)
    @Builder.Default
    private Integer missionsTotal = 0;

    @Column(nullable = false)
    @Builder.Default
    private Double tauxSatisfaction = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double revenuMensuel = 0.0;

    @Column(nullable = false)
    @Builder.Default
    private Double revenuTotal = 0.0;

    @Column(columnDefinition = "TEXT")
    private String competences;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String certifications;

    @Column(nullable = false)
    @Builder.Default
    private Boolean disponible = true;

    @Column(length = 100)
    private String pays;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateInscription;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        dateInscription = LocalDateTime.now();
    }
}
