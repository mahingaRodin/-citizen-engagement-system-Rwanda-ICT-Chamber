package com.citizen.engagement_system_be.models;

import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.enums.ComplaintType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    private User userId;

    @ManyToOne
    @JoinColumn(name= "category_id", nullable = false)
    private Category categoryId;

    @ManyToOne
    @JoinColumn(name= "agency_id", nullable = false)
    private Agency agencyId;

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

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public User getUserId() {
        return userId;
    }
    public void setUserId(User userId) {
        this.userId = userId;
    }
    public Category getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
    public Agency getAgencyId() {
        return agencyId;
    }
    public void setAgencyId(Agency agencyId) {
        this.agencyId = agencyId;
    }
    public ComplaintStatus getStatus() {
        return status;
    }
    public void setStatus(ComplaintStatus status) {
        this.status = status;
    }
    public ComplaintPriority getPriority() {
        return priority;
    }
    public void setPriority(ComplaintPriority priority) {
        this.priority = priority;
    }
    public ComplaintType getType() {
        return type;
    }
    public void setType(ComplaintType type) {
        this.type = type;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }
    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
}
