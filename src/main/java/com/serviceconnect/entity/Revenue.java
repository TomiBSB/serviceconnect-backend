package com.serviceconnect.entity;

import com.serviceconnect.entity.RevenueStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "revenues")
public class Revenue {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 200)
    private String libelle;

    @Column(length = 36)
    private String missionId;

    @Column(length = 100)
    private String clientNom;

    @Column(length = 100)
    private String clientPrenom;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    @Builder.Default
    private Double commission = 0.0;

    @Column(nullable = false)
    private Double net;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private RevenueStatus statut = RevenueStatus.PAID;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
    }
}
