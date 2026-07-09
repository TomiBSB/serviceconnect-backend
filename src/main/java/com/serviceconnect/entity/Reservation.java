package com.serviceconnect.entity;

import com.serviceconnect.entity.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false, unique = true, length = 20)
    private String reference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prestataire_id", nullable = false)
    private User prestataire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    @Column(length = 50)
    private String serviceKey;

    @Column(length = 100)
    private String serviceName;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateReservation;

    private LocalDate dateIntervention;

    @Column(length = 50)
    private String heureSlot;

    @Column(length = 255)
    private String adresse;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double montant;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus statut = ReservationStatus.PENDING;

    @Column(length = 30)
    private String paymentMethod;

    @Column(nullable = false)
    @Builder.Default
    private Boolean annulable = true;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        dateReservation = LocalDateTime.now();
        if (reference == null || reference.isBlank()) {
            reference = "RES" + System.currentTimeMillis();
        }
    }
}
