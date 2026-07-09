package com.serviceconnect.controller;

import com.serviceconnect.entity.Reservation;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.Review;
import com.serviceconnect.entity.ReservationStatus;
import com.serviceconnect.dto.ReservationResponse;
import com.serviceconnect.dto.ReviewResponse;
import com.serviceconnect.repository.ReservationRepository;
import com.serviceconnect.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historique")
@RequiredArgsConstructor
public class HistoriqueController {

    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getHistorique(@AuthenticationPrincipal User user) {
        List<Reservation> reservations = reservationRepository.findByClientOrderByDateReservationDesc(user);
        List<ReservationResponse> responses = reservations.stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
