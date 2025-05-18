package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.NotificationDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.enums.NotificationType;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.NotificationMapper;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.models.Notification;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.repository.NotificationRepository;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notifyRepo;
    private final ComplaintRepository complaintRepo;
    private final UserRepository userRepo;
    private final NotificationMapper notifyMapper;
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notifyRepo, ComplaintRepository complaintRepo, UserRepository userRepo, NotificationMapper notifyMapper, NotificationRepository notificationRepository) {
        this.notifyRepo = notifyRepo;
        this.complaintRepo = complaintRepo;
        this.userRepo = userRepo;
        this.notifyMapper = notifyMapper;
        this.notificationRepository = notificationRepository;
    }

    @Override
    @Transactional
    public void sendNotification(Long userId, NotificationType type, String message, Long complaintId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        Notification notify = new Notification();
//        notify.setUserId(user);                 // ✅ Set the user properly
        notify.setComplaint(complaint);       // ✅ Set the complaint if not null
        notify.setType(type);
        notify.setMessage(message);
        notify.setRead(false);                // Optional - already set in @PrePersist
        notify.setCreatedAt(LocalDateTime.now());

        notifyRepo.save(notify);
    }


    @Override
    @Transactional
    public NotificationDTO markAsRead(Long notificationId) {
        Notification notification = notifyRepo.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found"));

        notification.setRead(true);
        notifyRepo.save(notification);
        return null;
    }

    @Override
    @Transactional
    public void markAllAsRead(Long userId) {
        int page = 0;
        int size = 50; // adjust based on expected volume or performance
        Pageable pageable = PageRequest.of(page, size);
        Page<Notification> notificationPage;

        do {
            notificationPage = notifyRepo.findByUserIdAndIsRead(userId, false, pageable);
            List<Notification> unreadNotifications = notificationPage.getContent();

            unreadNotifications.forEach(notification -> notification.setRead(true));
            notifyRepo.saveAll(unreadNotifications);

            page++;
            pageable = PageRequest.of(page, size);
        } while (notificationPage.hasNext());
    }

    @Override
    public void deleteNotification(Long notificationId) {
        if (!notifyRepo.existsById(notificationId)) {
            throw new ResourceNotFoundException("Notification not found");
        }
        notifyRepo.deleteById(notificationId);
    }

    @Override
    public SearchResultDTO<NotificationDTO> getUserNotifications(Long userId, int page, int size) {
        if (!userRepo.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }

        // Create pageable object for pagination
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        // Get notifications from repository
        Page<Notification> notifications = notificationRepository.findByUserId(userId, pageable);

        // Convert to DTOs and create search result
        return new SearchResultDTO<>(
                notifications.getContent().stream()
                        .map(notifyMapper::toDTO)
                        .collect(Collectors.toList()),
                notifications.getTotalElements(),
                notifications.getTotalPages(),
                notifications.getSize(),
                notifications.getNumber()
        );
    }
}
