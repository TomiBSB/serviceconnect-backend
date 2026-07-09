package com.serviceconnect.repository;

import com.serviceconnect.entity.Notification;
import com.serviceconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByUserOrderByDateDesc(User user);
    long countByUserAndLuFalse(User user);
}
