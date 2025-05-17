package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.ComplaintTrackingDTO;

import java.util.List;

public interface ComplaintTrackingService {
    ComplaintTrackingDTO createTracking(ComplaintTrackingDTO trackingDTO);
    List<ComplaintTrackingDTO> getComplaintHistory(Long complaintId);
    void deleteTracking(Long id);
}
