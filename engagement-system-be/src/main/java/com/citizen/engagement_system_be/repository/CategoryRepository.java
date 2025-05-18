package com.citizen.engagement_system_be.repository;

import com.citizen.engagement_system_be.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    @Query("SELECT c FROM Category c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:description IS NULL OR LOWER(c.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
            "(:active IS NULL OR c.status = :active)")
    List<Category> searchCategories(@Param("name") String name,
                                    @Param("description") String description,
                                    @Param("active") Boolean active);

}
