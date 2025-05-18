package com.citizen.engagement_system_be.controllers;

import com.citizen.engagement_system_be.dtos.AgencyStatsDTO;
import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.services.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v3/dashboard")
@Tag(name = "Dashboard", description = "APIs for dashboard statistics")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Operation(summary = "Get agency Statistics")
    @GetMapping("/agency/{agencyId}/stats")
    public ResponseEntity<AgencyStatsDTO> getAgencyStats(
            @Parameter(description = "Agency Id", required = true)
            @PathVariable Long agencyId
    ) {
        return ResponseEntity.ok(dashboardService.getAgencyStats(agencyId));
    }


    @Operation(summary = "Get all agencies statistics")
    @GetMapping("/agencies/stats")
    public ResponseEntity<List<AgencyStatsDTO>> getAllAgenciesStats() {
        return ResponseEntity.ok(dashboardService.getAllAgencyStats());
    }

    @Operation(summary = "Get complaints by status for an agency")
    @GetMapping("/agency/{agencyId}/complaints/status")
    public ResponseEntity<Map<ComplaintStatus, Long>> getComplaintsByStatus(
            @Parameter(description = "Agency ID", required = true)
            @PathVariable Long agencyId) {
        return ResponseEntity.ok(dashboardService.getComplaintByStatus(agencyId));
    }

    @Operation(summary = "Get recent complaints for an agency")
    @GetMapping("/agency/{agencyId}/complaints/recent")
    public ResponseEntity<List<ComplaintDTO>> getRecentComplaints(
            @Parameter(description = "Agency ID", required = true)
            @PathVariable Long agencyId,
            @RequestParam(defaultValue = "10") Long limit) {
        return ResponseEntity.ok(dashboardService.getRecentComplaints(agencyId));
    }
}
