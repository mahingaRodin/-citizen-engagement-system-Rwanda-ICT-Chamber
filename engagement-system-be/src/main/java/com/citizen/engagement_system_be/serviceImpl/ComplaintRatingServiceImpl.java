package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.ComplaintRatingCreateDTO;
import com.citizen.engagement_system_be.dtos.ComplaintRatingDTO;
import com.citizen.engagement_system_be.enums.NotificationType;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.ComplaintRatingMapper;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.models.ComplaintRating;
import com.citizen.engagement_system_be.repository.ComplaintRatingRepository;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.services.ComplaintRatingService;
import com.citizen.engagement_system_be.services.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ComplaintRatingServiceImpl implements ComplaintRatingService {
    private final ComplaintRatingRepository ratingRepository;
    private final ComplaintRepository complaintRepository;
    private final ComplaintRatingMapper ratingMapper;
    private final NotificationService notificationService;

    public ComplaintRatingServiceImpl(ComplaintRatingRepository ratingRepository, ComplaintRepository complaintRepository, ComplaintRatingMapper ratingMapper, NotificationService notificationService) {
        this.ratingRepository = ratingRepository;
        this.complaintRepository = complaintRepository;
        this.ratingMapper = ratingMapper;
        this.notificationService = notificationService;
    }


    @Override
    @Transactional
    public ComplaintRatingDTO createRating(ComplaintRatingCreateDTO ratingDTO) {
        Complaint complaint = complaintRepository.findById(ratingDTO.getComplaintId())
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        // Validate rating value
        if (ratingDTO.getRating() < 1 || ratingDTO.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        ComplaintRating rating = ratingMapper.toEntity(ratingDTO);
        rating.setComplaint(complaint);
        ComplaintRating savedRating = ratingRepository.save(rating);

        // Notify agency about new rating
        notificationService.sendNotification(
                complaint.getAgencyId().getId(),
                NotificationType.COMPLAINT_RATED,
                "New rating received for complaint: " + complaint.getTitle(),
                complaint.getId()
        );

        return ratingMapper.toDTO(savedRating);
    }

    @Override
    public ComplaintRatingDTO getRating(Long id) {
        ComplaintRating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));
        return ratingMapper.toDTO(rating);
    }

    @Override
    @Transactional
    public ComplaintRatingDTO updateRating(Long id, ComplaintRatingDTO ratingDTO) {
        ComplaintRating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rating not found"));

        // Manually convert DTO to ComplaintRatingCreateDTO
        ComplaintRatingCreateDTO createDTO = new ComplaintRatingCreateDTO();
        createDTO.setRating(ratingDTO.getRating());
        createDTO.setFeedback(ratingDTO.getFeedback());

        // Reuse the existing toEntity() method
        ComplaintRating updatedRating = ratingMapper.toEntity(createDTO);
        updatedRating.setId(id);

        ComplaintRating savedRating = ratingRepository.save(updatedRating);
        return ratingMapper.toDTO(savedRating);
    }


    @Override
    @Transactional
    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new ResourceNotFoundException("Rating not found");
        }
        ratingRepository.deleteById(id);
    }

    @Override
    public Double getAverageRatingByAgency(Long agencyId) {
        return ratingRepository.getAverageRatingByAgency(agencyId);
    }

    @Override
    public Double getAverageRatingByCategory(Long categoryId) {
        return ratingRepository.getAverageRatingByCategory(categoryId);
    }
}
