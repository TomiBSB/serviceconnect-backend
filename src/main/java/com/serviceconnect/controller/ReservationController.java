package com.serviceconnect.controller;

import com.serviceconnect.entity.User;
import com.serviceconnect.entity.ReservationStatus;
import com.serviceconnect.dto.ReservationRequest;
import com.serviceconnect.dto.MissionResponse;
import com.serviceconnect.dto.ReservationResponse;
import com.serviceconnect.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> createReservation(@AuthenticationPrincipal User user,
                                                                  @Valid @RequestBody ReservationRequest request) {
        return ResponseEntity.ok(reservationService.createReservation(user.getId(), request));
    }

    @GetMapping("/client")
    public ResponseEntity<List<ReservationResponse>> getClientReservations(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reservationService.getClientReservations(user.getId()));
    }

    @GetMapping("/provider")
    public ResponseEntity<List<ReservationResponse>> getProviderReservations(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reservationService.getProviderReservations(user.getId()));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable String reservationId) {
        return ResponseEntity.ok(reservationService.getReservationById(reservationId));
    }

    @PutMapping("/{reservationId}/status")
    public ResponseEntity<ReservationResponse> updateStatus(@PathVariable String reservationId,
                                                            @RequestParam ReservationStatus status) {
        return ResponseEntity.ok(reservationService.updateReservationStatus(reservationId, status));
    }

    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable String reservationId,
                                                  @AuthenticationPrincipal User user) {
        reservationService.cancelReservation(reservationId, user.getId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/provider/missions")
    public ResponseEntity<List<MissionResponse>> getProviderMissions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reservationService.getProviderMissions(user.getId()));
    }
}
