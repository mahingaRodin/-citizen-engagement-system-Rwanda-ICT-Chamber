package com.citizen.engagement_system_be.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "agency_categories")
@Data
public class AgencyCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agency_id", nullable = false)
    private Agency agency;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category_id;

    @Column(nullable = false)
    private boolean isPrimary;
}
