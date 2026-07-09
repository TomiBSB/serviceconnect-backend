package com.serviceconnect.repository;

import com.serviceconnect.entity.PlanningSlot;
import com.serviceconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PlanningSlotRepository extends JpaRepository<PlanningSlot, String> {
    List<PlanningSlot> findByProviderAndDateBetweenOrderByDateAscHeureDebutAsc(User provider, LocalDate debut, LocalDate fin);
    List<PlanningSlot> findByProviderAndDateOrderByHeureDebutAsc(User provider, LocalDate date);
}
