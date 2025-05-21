package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.models.Complaint;
import org.springframework.stereotype.Component;

@Component
public class ComplaintMapper {
    public ComplaintDTO toDTO(Complaint complaint) {
        if (complaint == null) {
            return null;
        }

        ComplaintDTO dto = new ComplaintDTO();
        dto.setId(complaint.getId());
        dto.setTitle(complaint.getTitle());
        dto.setDescription(complaint.getDescription());
        dto.setStatus(ComplaintStatus.OPEN);
        dto.setPriority(ComplaintPriority.MEDIUM);
        dto.setLocation(complaint.getLocation());
        dto.setUserId(complaint.getUserId().getId());
        dto.setCategoryId(complaint.getCategoryId().getId());
        dto.setType(complaint.getType().name());
        dto.setAgencyId(complaint.getAgencyId().getId());
        dto.setCreatedAt(complaint.getCreatedAt());
        dto.setUpdatedAt(complaint.getUpdatedAt());
        dto.setResolvedAt(complaint.getResolvedAt());

        return dto;
    }
}
