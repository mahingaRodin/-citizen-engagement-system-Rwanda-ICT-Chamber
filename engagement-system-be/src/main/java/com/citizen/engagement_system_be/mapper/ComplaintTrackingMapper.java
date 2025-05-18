package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.ComplaintTrackingDTO;
import com.citizen.engagement_system_be.models.ComplaintTracking;
import org.springframework.stereotype.Component;

@Component
public class ComplaintTrackingMapper {
    public ComplaintTrackingDTO toDTO(ComplaintTracking tracking) {
        if (tracking == null) {
            return null;
        }

        ComplaintTrackingDTO dto = new ComplaintTrackingDTO();
        dto.setId(tracking.getId());
        dto.setComplaintId(tracking.getComplaint().getId());
        dto.setPreviousStatus(tracking.getPreviousStatus().name());
        dto.setNewStatus(tracking.getNewStatus().name());
        dto.setComment(tracking.getComment());
        dto.setChangedAt(tracking.getChangedAt());

        return dto;
    }

    public ComplaintTracking toEntity(ComplaintTrackingDTO dto) {
        if (dto == null) {
            return null;
        }

        ComplaintTracking tracking = new ComplaintTracking();
        tracking.setComment(dto.getComment());

        return tracking;
    }
}
