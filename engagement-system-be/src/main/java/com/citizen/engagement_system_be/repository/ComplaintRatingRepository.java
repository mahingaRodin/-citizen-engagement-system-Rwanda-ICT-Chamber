package com.citizen.engagement_system_be.repository;

import com.citizen.engagement_system_be.models.ComplaintRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintRatingRepository extends JpaRepository<ComplaintRating, Long> {
    @Query("SELECT AVG(r.rating) FROM ComplaintRating r WHERE r.complaint.agencyId = :agencyId")
    Double getAverageRatingByAgency(Long agencyId);

    @Query("SELECT AVG(r.rating) FROM ComplaintRating r WHERE r.complaint.categoryId = :categoryId")
    Double getAverageRatingByCategory(Long categoryId);
}