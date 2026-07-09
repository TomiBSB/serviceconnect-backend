package com.serviceconnect.service;

import com.serviceconnect.entity.Reservation;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.ReservationStatus;
import com.serviceconnect.dto.ReservationRequest;
import com.serviceconnect.dto.MissionResponse;
import com.serviceconnect.dto.ReservationResponse;
import com.serviceconnect.repository.ReservationRepository;
import com.serviceconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationResponse createReservation(String clientId, ReservationRequest request) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        User prestataire = userRepository.findById(request.getPrestataireId())
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));

        Reservation reservation = Reservation.builder()
                .client(client)
                .prestataire(prestataire)
                .serviceKey(request.getServiceKey())
                .serviceName(request.getServiceName())
                .dateIntervention(request.getDateIntervention())
                .heureSlot(request.getHeureSlot())
                .adresse(request.getAdresse())
                .description(request.getDescription())
                .montant(request.getMontant())
                .paymentMethod(request.getPaymentMethod())
                .statut(ReservationStatus.PENDING)
                .build();

        reservation = reservationRepository.save(reservation);
        return ReservationResponse.fromEntity(reservation);
    }

    public List<ReservationResponse> getClientReservations(String clientId) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return reservationRepository.findByClientOrderByDateReservationDesc(client)
                .stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getProviderReservations(String providerId) {
        User prestataire = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));
        return reservationRepository.findByPrestataireOrderByDateReservationDesc(prestataire)
                .stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MissionResponse> getProviderMissions(String providerId) {
        User prestataire = userRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Prestataire non trouvé"));
        return reservationRepository.findByPrestataireOrderByDateReservationDesc(prestataire)
                .stream()
                .map(MissionResponse::fromReservation)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponse updateReservationStatus(String reservationId, ReservationStatus status) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setStatut(status);

        if (status == ReservationStatus.CANCELLED || status == ReservationStatus.COMPLETED) {
            reservation.setAnnulable(false);
        }

        reservation = reservationRepository.save(reservation);
        return ReservationResponse.fromEntity(reservation);
    }

    @Transactional
    public void cancelReservation(String reservationId, String userId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));

        if (!reservation.getAnnulable()) {
            throw new RuntimeException("Cette réservation ne peut plus être annulée");
        }

        if (!reservation.getClient().getId().equals(userId) &&
            !reservation.getPrestataire().getId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à annuler cette réservation");
        }

        reservation.setStatut(ReservationStatus.CANCELLED);
        reservation.setAnnulable(false);
        reservationRepository.save(reservation);
    }

    public ReservationResponse getReservationById(String reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        return ReservationResponse.fromEntity(reservation);
    }

    public List<ReservationResponse> getClientReservationsByStatus(String clientId, ReservationStatus status) {
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
        return reservationRepository.findByClientAndStatutOrderByDateReservationDesc(client, status)
                .stream()
                .map(ReservationResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
