package com.serviceconnect.repository;

import com.serviceconnect.entity.Reservation;
import com.serviceconnect.entity.User;
import com.serviceconnect.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByClientOrderByDateReservationDesc(User client);
    List<Reservation> findByPrestataireOrderByDateReservationDesc(User prestataire);
    List<Reservation> findByClientAndStatutOrderByDateReservationDesc(User client, ReservationStatus statut);
    List<Reservation> findByPrestataireAndStatutOrderByDateReservationDesc(User prestataire, ReservationStatus statut);
    long countByPrestataire(User prestataire);
    long countByPrestataireAndStatut(User prestataire, ReservationStatus statut);
}
