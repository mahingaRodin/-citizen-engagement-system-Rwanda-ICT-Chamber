package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.enums.NotificationType;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.models.Agency;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.repository.AgencyRepository;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.services.ComplaintRoutingService;
import com.citizen.engagement_system_be.services.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ComplaintRoutingServiceImpl implements ComplaintRoutingService {
    private final ComplaintRepository complaintRepository;
    private final AgencyRepository agencyRepository;
    private final NotificationService notificationService;

    public ComplaintRoutingServiceImpl(ComplaintRepository complaintRepository, AgencyRepository agencyRepository, NotificationService notificationService) {
        this.complaintRepository = complaintRepository;
        this.agencyRepository = agencyRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void routeComplaint(Complaint complaint) {
        // Find appropriate agency based on category
        Agency agency = agencyRepository.findByCategoryId(complaint.getCategoryId().getId())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No agency found for category"));

        complaint.setAgencyId(agency);
        complaint.setStatus(ComplaintStatus.ASSIGNED);
        complaintRepository.save(complaint);

        // Notify agency
        notificationService.sendNotification(
                agency.getId(),
                NotificationType.COMPLAINT_ASSIGNED,
                "New complaint assigned to your agency: " + complaint.getTitle(),
                complaint.getId()
        );
    }

    @Override
    @Transactional
    public void reassignComplaint(Long complaintId, Long newAgencyId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));
        Agency newAgency = agencyRepository.findById(newAgencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));

        Agency oldAgency = complaint.getAgencyId();
        complaint.setAgencyId(newAgency);
        complaintRepository.save(complaint);

        // Notify both agencies
        notificationService.sendNotification(
                oldAgency.getId(),
                NotificationType.COMPLAINT_REASSIGNED,
                "Complaint reassigned from your agency: " + complaint.getTitle(),
                complaint.getId()
        );

        notificationService.sendNotification(
                newAgency.getId(),
                NotificationType.COMPLAINT_ASSIGNED,
                "New complaint assigned to your agency: " + complaint.getTitle(),
                complaint.getId()
        );

    }

    @Override
    @Transactional
    public void escalateComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        complaint.setPriority(ComplaintPriority.HIGH);
        complaintRepository.save(complaint);

        // Notify relevant users
        notificationService.sendNotification(
                complaint.getAgencyId().getId(),
                NotificationType.COMPLAINT_ESCALATED,
                "Complaint escalated: " + complaint.getTitle(),
                complaint.getId()
        );
    }

    @Override
    @Transactional
    public void deescalateComplaint(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        complaint.setPriority(ComplaintPriority.MEDIUM);
        complaintRepository.save(complaint);

        // Notify relevant users
        notificationService.sendNotification(
                complaint.getAgencyId().getId(),
                NotificationType.COMPLAINT_DEESCALATED,
                "Complaint deescalated: " + complaint.getTitle(),
                complaint.getId()
        );
    }
}
