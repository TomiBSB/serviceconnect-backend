package com.serviceconnect.repository;

import com.serviceconnect.entity.Payment;
import com.serviceconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByUserOrderByDatePaiementDesc(User user);
}
