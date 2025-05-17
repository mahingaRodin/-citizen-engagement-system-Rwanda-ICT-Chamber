package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.ComplaintRatingCreateDTO;
import com.citizen.engagement_system_be.dtos.ComplaintRatingDTO;

public interface ComplaintRatingService {
    ComplaintRatingDTO createRating(ComplaintRatingCreateDTO ratingDTO);
    ComplaintRatingDTO getRating(Long id);
    ComplaintRatingDTO updateRating(Long id, ComplaintRatingDTO ratingDTO);
    void deleteRating(Long id);
    Double getAverageRatingByAgency(Long agencyId);
    Double getAverageRatingByCategory(Long categoryId);
}
