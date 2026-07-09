package com.serviceconnect.dto;

import com.serviceconnect.entity.Payment;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private String id;
    private String reservationId;
    private String methode;
    private Double montant;
    private Double commission;
    private Double net;
    private String statut;
    private LocalDateTime datePaiement;

    public static PaymentResponse fromEntity(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .reservationId(payment.getReservation().getId())
                .methode(payment.getMethode() != null ? payment.getMethode().name() : "")
                .montant(payment.getMontant())
                .commission(payment.getCommission())
                .net(payment.getNet())
                .statut(payment.getStatut().name())
                .datePaiement(payment.getDatePaiement())
                .build();
    }
}
