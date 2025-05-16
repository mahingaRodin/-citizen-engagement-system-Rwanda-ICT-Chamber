package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.enums.NotificationType;

public interface NotificationService {
    void sendNotification(Long userId, NotificationType type, String message, Long complaintId);
    void markAsRead(Long notificationId);
    void markAllAsRead(Long userId);
    void deleteNotification(Long notificationId);
}
