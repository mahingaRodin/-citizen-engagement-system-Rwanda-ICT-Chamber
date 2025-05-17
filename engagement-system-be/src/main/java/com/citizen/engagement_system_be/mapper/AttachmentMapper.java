package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.AttachmentDTO;
import com.citizen.engagement_system_be.models.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {

    public AttachmentDTO toDTO(Attachment attachment) {
        if (attachment == null) {
            return null;
        }

        AttachmentDTO dto = new AttachmentDTO();
        dto.setId(attachment.getId());
        dto.setComplaintId(attachment.getComplaint().getId());
        dto.setFile(attachment.getFile());
        dto.setUploadedAt(attachment.getUploadedAt());

        return dto;
    }
}
