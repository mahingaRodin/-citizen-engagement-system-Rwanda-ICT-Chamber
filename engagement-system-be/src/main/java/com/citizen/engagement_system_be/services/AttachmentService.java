package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    AttachmentDTO uploadAttachment(Long complaintId, MultipartFile file);
    AttachmentDTO getAttachment(Long id);
    List<AttachmentDTO> getComplaintAttachments(Long complaintId);
    void deleteAttachment(Long id);
    byte[] downloadAttachment(Long id);
}
