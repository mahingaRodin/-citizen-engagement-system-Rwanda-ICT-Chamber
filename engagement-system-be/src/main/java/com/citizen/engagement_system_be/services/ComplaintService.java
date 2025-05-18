package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.dtos.search.ComplaintSearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ComplaintService {
    boolean createComplaint(ComplaintDTO complaint);
    SearchResultDTO<ComplaintDTO> getComplaintsByAgencyId(Long agencyId, int page, int size);
    ComplaintDTO getComplaint(Long complaintId);
    ComplaintDTO updateComplaint(Long userId, ComplaintDTO complaint);
    void deleteComplaint(Long complaintId);
    SearchResultDTO<ComplaintDTO> searchComplaints(ComplaintSearchDTO searchDTO);
    ComplaintDTO updateStatus(Long id, String status);
    ComplaintDTO assignToAgency(Long id, Long agencyId);
    void addAttachment(Long complaintId, MultipartFile file);
    void removeAttachment(Long complaintId, Long attachmentId);
}
