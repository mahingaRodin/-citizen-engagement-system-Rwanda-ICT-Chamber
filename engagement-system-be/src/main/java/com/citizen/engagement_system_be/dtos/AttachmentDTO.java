package com.citizen.engagement_system_be.dtos;

import com.citizen.engagement_system_be.fileHandling.File;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttachmentDTO {
    private Long id;
    private Long complaintId;
    private File file;
    private LocalDateTime uploadedAt;
}
