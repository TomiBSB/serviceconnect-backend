package com.serviceconnect.repository;

import com.serviceconnect.entity.Revenue;
import com.serviceconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, String> {
    List<Revenue> findByProviderOrderByDateDesc(User provider);
    List<Revenue> findByProviderAndDateBetweenOrderByDateAsc(User provider, LocalDate debut, LocalDate fin);
    List<Revenue> findByProviderAndDate(User provider, LocalDate date);
}
