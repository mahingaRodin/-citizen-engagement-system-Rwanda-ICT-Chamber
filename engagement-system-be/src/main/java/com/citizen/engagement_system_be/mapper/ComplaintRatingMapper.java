package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.ComplaintRatingCreateDTO;
import com.citizen.engagement_system_be.dtos.ComplaintRatingDTO;
import com.citizen.engagement_system_be.models.ComplaintRating;
import org.springframework.stereotype.Component;

@Component
public class ComplaintRatingMapper {
    public ComplaintRatingDTO toDTO(ComplaintRating rating) {
        if (rating == null) {
            return null;
        }

        ComplaintRatingDTO dto = new ComplaintRatingDTO();
        dto.setId(rating.getId());
        dto.setComplaintId(rating.getComplaint().getId());
        dto.setRating(rating.getRating());
        dto.setFeedback(rating.getFeedback());
        dto.setRatedAt(rating.getRatedAt());

        return dto;
    }

    public ComplaintRating toEntity(ComplaintRatingCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        ComplaintRating rating = new ComplaintRating();
        rating.setRating(dto.getRating());
        rating.setFeedback(dto.getFeedback());

        return rating;
    }
}
