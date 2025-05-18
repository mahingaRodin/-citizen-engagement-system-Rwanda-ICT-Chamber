package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.NotificationDTO;
import com.citizen.engagement_system_be.models.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDTO toDTO(Notification notification) {
        if (notification == null) {
            return null;
        }

        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setUserId(notification.getUserId().getId());
        dto.setType(notification.getType().name());
        dto.setMessage(notification.getMessage());
        dto.setComplaintId(notification.getComplaint().getId());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());

        return dto;
    }
}
