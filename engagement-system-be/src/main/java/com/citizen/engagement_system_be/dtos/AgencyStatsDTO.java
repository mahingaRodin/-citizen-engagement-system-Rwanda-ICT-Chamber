package com.citizen.engagement_system_be.dtos;

import com.citizen.engagement_system_be.enums.ComplaintStatus;

import java.util.List;
import java.util.Map;

public class AgencyStatsDTO {
   private Long agencyId;
   private String agencyName;
   private Long totalComplaints;
   private Map<ComplaintStatus, Long> complaintByStatus;
   private List<ComplaintDTO> recentComplaints;

    public Long getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Long agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Long getTotalComplaints() {
        return totalComplaints;
    }

    public void setTotalComplaints(Long totalComplaints) {
        this.totalComplaints = totalComplaints;
    }

    public Map<ComplaintStatus, Long> getComplaintByStatus() {
        return complaintByStatus;
    }

    public void setComplaintByStatus(Map<ComplaintStatus, Long> complaintByStatus) {
        this.complaintByStatus = complaintByStatus;
    }

    public List<ComplaintDTO> getRecentComplaints() {
        return recentComplaints;
    }

    public void setRecentComplaints(List<ComplaintDTO> recentComplaints) {
        this.recentComplaints = recentComplaints;
    }
}
