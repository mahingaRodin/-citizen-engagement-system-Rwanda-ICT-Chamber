package com.citizen.engagement_system_be.controllers;


import com.citizen.engagement_system_be.dtos.NotificationDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.models.Notification;
import com.citizen.engagement_system_be.repository.NotificationRepository;
import com.citizen.engagement_system_be.services.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v3/notifications")
@Tag(name = "Notification Management", description = "APIs for managing notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;


    public NotificationController(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

    @Operation(summary = "Get notification by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Notification>> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationRepository.findById(id));
    }

    @Operation(summary = "Mark notification as read")
    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    @Operation(summary = "Mark all notifications as read")
    @PutMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(@RequestAttribute("userId") Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete notification")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
//    @Operation(summary = "Search notifications")
//    @GetMapping("/search")
//    public ResponseEntity<SearchResultDTO<NotificationDTO>> searchNotifications(NotificationSearchDTO searchDTO) {
//        return ResponseEntity.ok(notificationService.searchNotifications(searchDTO));
//    }

    @Operation(summary = "Get user notifications")
    @GetMapping("/user")
    public ResponseEntity<SearchResultDTO<NotificationDTO>> getUserNotifications(
            @RequestAttribute("userId") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(notificationService.getUserNotifications(userId, page, size));
    }

//    @Operation(summary = "Get unread notification count")
//    @GetMapping("/unread/count")
//    public ResponseEntity<Long> getUnreadCount(@RequestAttribute("userId") Long userId) {
//        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
//    }
}
