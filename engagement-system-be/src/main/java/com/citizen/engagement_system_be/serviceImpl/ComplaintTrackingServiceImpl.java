package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.ComplaintTrackingDTO;
import com.citizen.engagement_system_be.enums.NotificationType;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.ComplaintTrackingMapper;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.models.ComplaintTracking;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.repository.ComplaintTrackingRepository;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.ComplaintTrackingService;
import com.citizen.engagement_system_be.services.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintTrackingServiceImpl implements ComplaintTrackingService {
    private final ComplaintTrackingRepository trackingRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final ComplaintTrackingMapper trackingMapper;
    private final NotificationService notificationService;

    public ComplaintTrackingServiceImpl(ComplaintTrackingRepository trackingRepository, ComplaintRepository complaintRepository, UserRepository userRepository, ComplaintTrackingMapper trackingMapper, NotificationService notificationService) {
        this.trackingRepository = trackingRepository;
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.trackingMapper = trackingMapper;
        this.notificationService = notificationService;
    }

    @Override
    @Transactional
    public ComplaintTrackingDTO createTracking(ComplaintTrackingDTO trackingDTO) {
        Complaint complaint = complaintRepository.findById(trackingDTO.getComplaintId())
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));
        User user = userRepository.findById(trackingDTO.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ComplaintTracking tracking = trackingMapper.toEntity(trackingDTO);
        tracking.setComplaint(complaint);
        tracking.setUserId(user);
        ComplaintTracking savedTracking = trackingRepository.save(tracking);

        // Notify relevant users about status change
        notificationService.sendNotification(
                complaint.getUserId().getId(),
                NotificationType.STATUS_CHANGED,
                "Complaint status updated to: " + tracking.getNewStatus(),
                complaint.getId()
        );

        return trackingMapper.toDTO(savedTracking);
    }

    @Override
    public List<ComplaintTrackingDTO> getComplaintHistory(Long complaintId) {
        if (!complaintRepository.existsById(complaintId)) {
            throw new ResourceNotFoundException("Complaint not found");
        }

        return trackingRepository.findByComplaintId(complaintId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(trackingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteTracking(Long id) {
        if (!trackingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tracking record not found");
        }
        trackingRepository.deleteById(id);
    }
    }
