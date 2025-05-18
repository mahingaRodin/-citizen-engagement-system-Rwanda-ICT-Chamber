package com.citizen.engagement_system_be.repository;

import com.citizen.engagement_system_be.models.ComplaintTracking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplaintTrackingRepository extends JpaRepository<ComplaintTracking, Long> {
    Page<ComplaintTracking> findByComplaintId(Long complaintId, Pageable pageable);
    Page<ComplaintTracking> findByUserId(Long userId, Pageable pageable);
}
