package com.citizen.engagement_system_be.dtos;

import com.citizen.engagement_system_be.models.Category;

public class AgencyDTO {
    private Long id;
    private String name;
    private Category categoryId;
    private String description;
    private String contactEmail;
    private String contactPhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    public Category getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }
}
