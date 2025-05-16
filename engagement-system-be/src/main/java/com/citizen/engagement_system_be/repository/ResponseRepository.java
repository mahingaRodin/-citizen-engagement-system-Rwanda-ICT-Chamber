package com.citizen.engagement_system_be.repository;


import com.citizen.engagement_system_be.models.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findByComplaintId(Long complaintId, Pageable pageable);
    Page<Response> findByUserId(Long userId, Pageable pageable);

    // Search responses by content
    @Query("SELECT r FROM Response r WHERE " +
            "LOWER(r.message) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Response> searchResponses(
            @Param("keyword") String keyword,
            Pageable pageable);

    // Search responses by date range
    @Query("SELECT r FROM Response r WHERE " +
            "r.createdAt BETWEEN :startDate AND :endDate")
    Page<Response> searchResponsesByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
}
