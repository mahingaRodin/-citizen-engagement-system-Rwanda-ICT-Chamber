package com.citizen.engagement_system_be.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaint_ratings")
public class ComplaintRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Column(nullable = false)
    private Integer rating; // 1-5 scale

    private String feedback;
    private LocalDateTime ratedAt;

    @PrePersist
    protected void onCreate() {
        ratedAt = LocalDateTime.now();
    }
}
