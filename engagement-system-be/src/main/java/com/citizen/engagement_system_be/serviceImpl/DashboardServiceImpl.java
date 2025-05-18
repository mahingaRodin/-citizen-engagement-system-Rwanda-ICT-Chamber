package com.citizen.engagement_system_be.serviceImpl;


import com.citizen.engagement_system_be.dtos.AgencyStatsDTO;
import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.ComplaintMapper;
import com.citizen.engagement_system_be.models.Agency;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.repository.AgencyRepository;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.services.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final ComplaintRepository complaintRepository;
    private final AgencyRepository agencyRepository;
    private final ComplaintMapper complaintMapper;

    public DashboardServiceImpl(ComplaintRepository complaintRepository, AgencyRepository agencyRepository, ComplaintMapper complaintMapper) {
        this.complaintRepository = complaintRepository;
        this.agencyRepository = agencyRepository;
        this.complaintMapper = complaintMapper;
    }

    @Override
    public AgencyStatsDTO getAgencyStats(Long agencyId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));

        AgencyStatsDTO stats = new AgencyStatsDTO();
        stats.setAgencyId(agencyId);
        stats.setAgencyName(agency.getName());

        //get total complaitns
        long totalComplaints = complaintRepository.countByAgencyId(agencyId);
        stats.setTotalComplaints(totalComplaints);

        //get complaints by status
        Map<ComplaintStatus,Long> statusCounts = getComplaintByStatus(agencyId);
        stats.setComplaintByStatus(statusCounts);

        //get recent complaints
        List<Complaint> recentComplaints = complaintRepository
                .findTop10ByAgencyIdOrderByCreatedAtDesc(agencyId);
        stats.setRecentComplaints(recentComplaints.stream()
                .map(complaintMapper::toDTO)
                .collect(Collectors.toList())
        );

        return stats;
    }

    @Override
    public List<AgencyStatsDTO> getAllAgencyStats() {
        List<Agency> agencies = agencyRepository.findAll();
        return agencies.stream()
                .map(agency -> getAgencyStats(agency.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Map<ComplaintStatus, Long> getComplaintByStatus(Long agencyId) {
        return complaintRepository.findAllByAgencyId(agencyId).stream()
                .collect(Collectors.groupingBy(
                        Complaint::getStatus,
                        Collectors.counting()
                ));
    }

    @Override
    public List<ComplaintDTO> getRecentComplaints(Long agencyId) {
        return complaintRepository.findTop10ByAgencyIdOrderByCreatedAtDesc(agencyId).stream()
                .map(complaintMapper::toDTO)
                .collect(Collectors.toList());
    }
}
