package com.serviceconnect.service;

import com.serviceconnect.entity.*;
import com.serviceconnect.entity.PaymentStatus;
import com.serviceconnect.dto.PaymentResponse;
import com.serviceconnect.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponse createPayment(Reservation reservation, User user, String methode) {
        double commission = reservation.getMontant() * 0.10;
        double net = reservation.getMontant() - commission;

        Payment payment = Payment.builder()
                .reservation(reservation)
                .user(user)
                .montant(reservation.getMontant())
                .commission(commission)
                .net(net)
                .statut(PaymentStatus.PAID)
                .build();

        payment = paymentRepository.save(payment);
        return PaymentResponse.fromEntity(payment);
    }

    public List<PaymentResponse> getUserPayments(String userId) {
        return paymentRepository.findAll().stream()
                .filter(p -> p.getUser().getId().equals(userId))
                .map(PaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public PaymentResponse getPaymentByReservation(String reservationId) {
        Payment payment = paymentRepository.findAll().stream()
                .filter(p -> p.getReservation().getId().equals(reservationId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé pour cette réservation"));
        return PaymentResponse.fromEntity(payment);
    }
}
