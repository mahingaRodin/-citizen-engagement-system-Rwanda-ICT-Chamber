package com.citizen.engagement_system_be.models;

import com.citizen.engagement_system_be.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaint_tracking")
@Data
public class ComplaintTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "complaint_id", nullable = false)
    private Complaint complaint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus previousStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintStatus newStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User updatedBy;

    private String comment;
    private LocalDateTime changedAt;

    @PrePersist
    protected void onCreate() {
        changedAt = LocalDateTime.now();
    }
}