package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.AgencyStatsDTO;
import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.enums.ComplaintStatus;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    AgencyStatsDTO getAgencyStats(Long agencyId);
    List<AgencyStatsDTO> getAllAgencyStats();
    Map<ComplaintStatus, Long> getComplaintByStatus(Long agencyId);
    List<ComplaintDTO> getRecentComplaints(Long agencyId);
}
