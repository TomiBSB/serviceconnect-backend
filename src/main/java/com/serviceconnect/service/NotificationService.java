package com.serviceconnect.service;

import com.serviceconnect.entity.Notification;
import com.serviceconnect.entity.User;
import com.serviceconnect.dto.NotificationResponse;
import com.serviceconnect.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<NotificationResponse> getUserNotifications(String userId) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId))
                .map(NotificationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificationResponse markAsRead(String notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification non trouvée"));
        notification.setLu(true);
        notification = notificationRepository.save(notification);
        return NotificationResponse.fromEntity(notification);
    }

    @Transactional
    public void markAllAsRead(String userId) {
        notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId) && !n.getLu())
                .forEach(n -> {
                    n.setLu(true);
                    notificationRepository.save(n);
                });
    }

    public long getUnreadCount(String userId) {
        return notificationRepository.findAll().stream()
                .filter(n -> n.getUser().getId().equals(userId) && !n.getLu())
                .count();
    }

    @Transactional
    public NotificationResponse createNotification(User user, String titre, String message,
                                                    String type, String missionId, String emetteurNom) {
        Notification notification = Notification.builder()
                .user(user)
                .titre(titre)
                .message(message)
                .build();

        notification = notificationRepository.save(notification);
        return NotificationResponse.fromEntity(notification);
    }

    public List<NotificationResponse> getGroupedNotifications(String userId) {
        return getUserNotifications(userId);
    }
}
