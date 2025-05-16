package com.citizen.engagement_system_be.models;

import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.enums.ComplaintType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "complaints")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name= "user_id", nullable = false)
    private Agency agency;

    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ComplaintType type;

    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = ComplaintStatus.OPEN;
        priority = ComplaintPriority.MEDIUM;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
