package com.citizen.engagement_system_be.dtos;

import com.citizen.engagement_system_be.fileHandling.File;


public class AttachmentCreateDTO {
    private Long complaintId;
    private File file;

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
