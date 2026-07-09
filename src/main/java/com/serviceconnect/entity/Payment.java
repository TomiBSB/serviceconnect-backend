package com.serviceconnect.entity;

import com.serviceconnect.entity.PaymentMethod;
import com.serviceconnect.entity.PaymentStatus;
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
@Table(name = "payments")
public class Payment {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    @Builder.Default
    private Double commission = 0.0;

    @Column(nullable = false)
    private Double net;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private PaymentMethod methode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private PaymentStatus statut = PaymentStatus.PENDING;

    @Column(nullable = false, updatable = false)
    private LocalDateTime datePaiement;

    @PrePersist
    public void prePersist() {
        if (id == null || id.isBlank()) {
            id = UUID.randomUUID().toString();
        }
        datePaiement = LocalDateTime.now();
    }
}
