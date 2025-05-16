package com.citizen.engagement_system_be.repository;

import com.citizen.engagement_system_be.models.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {
    List<Agency> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);

    @Query("SELECT a FROM AgencyCategory a JOIN a.category_id c WHERE c.id = :categoryId")
    List<Agency> findByCategoryId(Long categoryId);

    // Search agencies by multiple criteria
    @Query("SELECT a FROM Agency a WHERE " +
            "(:name IS NULL OR LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:description IS NULL OR LOWER(a.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
            "(:contactEmail IS NULL OR LOWER(a.contactEmail) LIKE LOWER(CONCAT('%', :contactEmail, '%'))) AND " +
            "(:contactPhone IS NULL OR a.contactPhone LIKE CONCAT('%', :contactPhone, '%'))")
    Page<Agency> searchAgencies(
            @Param("name") String name,
            @Param("description") String description,
            @Param("contactEmail") String contactEmail,
            @Param("contactPhone") String contactPhone,
            Pageable pageable);

    // Search agencies by categoryId
    @Query("select a from  AgencyCategory a where a.category_id = :categoryId")
    Page<Agency> searchAgenciesByCategoryId(
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );
}
