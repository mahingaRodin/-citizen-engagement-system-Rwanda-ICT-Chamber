package com.citizen.engagement_system_be.repository;

import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.models.Complaint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    Page<Complaint> findByUserId(Long userId, Pageable pageable);
    Page<Complaint> findByAgencyId(Long agencyId, Pageable pageable);
    Page<Complaint> findByStatus(ComplaintStatus status, Pageable pageable);
    Page<Complaint> findByPriority(ComplaintPriority priority, Pageable pageable);

    @Query("select c from Complaint c where c.agencyId.id = :agencyId")
    Page<Complaint> findByAgencyId(@Param("agencyId") long agencyId, Pageable pageable);

    @Query("select c from Complaint c where c.agencyId = :agencyId and c.status =: status")
    Page<Complaint> findByAgencyAndStatus(@Param("agencyId") Long agencyId,
                                          @Param("status") ComplaintStatus status,
                                          Pageable pageable);


    @Query("select c from Complaint c where c.createdAt between :startDate and :endDate")
    Page<Complaint> findByDateRange(@Param("startDate")LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate,
                                    Pageable pageable
    );

    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.agencyId = :agencyId AND c.status = :status")
    Long countByAgencyAndStatus(@Param("agencyId") Long agencyId,
                                @Param("status") ComplaintStatus status);

    // Search by multiple criteria
    @Query("SELECT c FROM Complaint c WHERE " +
            "(:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:description IS NULL OR LOWER(c.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
            "(:status IS NULL OR c.status = :status) AND " +
            "(:priority IS NULL OR c.priority = :priority) AND " +
            "(:agencyId IS NULL OR c.agencyId = :agencyId) AND " +
            "(:categoryId IS NULL OR c.categoryId = :categoryId) AND " +
            "(:startDate IS NULL OR c.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR c.createdAt <= :endDate)")
    Page<Complaint> searchComplaints(
            @Param("title") String title,
            @Param("description") String description,
            @Param("status") ComplaintStatus status,
            @Param("priority") ComplaintPriority priority,
            @Param("agencyId") Long agencyId,
            @Param("categoryId") Long categoryId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);


    // Search by location
    @Query("SELECT c FROM Complaint c WHERE " +
            "(:location IS NULL OR LOWER(c.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    Page<Complaint> searchByLocation(@Param("location") String location, Pageable pageable);

    // Search for similar complaints
    @Query("SELECT c FROM Complaint c WHERE " +
            "c.id != :complaintId AND " +
            "(LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Complaint> findSimilarComplaints(
            @Param("complaintId") Long complaintId,
            @Param("keyword") String keyword,
            Pageable pageable);


    @Query("select count(c) from  Complaint c where c.agencyId.id = ?1")
    long countByAgencyId(Long agencyId);

    @Query("select c from  Complaint c where c.agencyId.id = ?1")
    List<Complaint> findAllByAgencyId(Long agencyId);

    @Query("select c from Complaint c where c.agencyId.id = ?1 ORDER BY c.createdAt DESC ")
    List<Complaint> findTop10ByAgencyIdOrderByCreatedAtDesc(Long agencyId);
}


