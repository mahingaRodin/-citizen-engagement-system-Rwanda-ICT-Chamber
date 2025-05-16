package com.citizen.engagement_system_be.dtos;

import com.citizen.engagement_system_be.fileHandling.File;
import lombok.Data;

@Data
public class AttachmentCreateDTO {
    private Long complaintId;
    private File file;
}
