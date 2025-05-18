package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.models.Complaint;

public interface ComplaintRoutingService {
    void routeComplaint(Complaint complaint);
    void reassignComplaint(Long complaintId, Long newAgencyId);
    void escalateComplaint(Long complaintId);
    void deescalateComplaint(Long complaintId);
}
