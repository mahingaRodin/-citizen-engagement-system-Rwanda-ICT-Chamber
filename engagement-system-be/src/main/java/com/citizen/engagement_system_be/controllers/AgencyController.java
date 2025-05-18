package com.citizen.engagement_system_be.controllers;

import com.citizen.engagement_system_be.dtos.AgencyDTO;
import com.citizen.engagement_system_be.dtos.search.AgencySearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.models.Agency;
import com.citizen.engagement_system_be.repository.AgencyRepository;
import com.citizen.engagement_system_be.services.AgencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v3/agencies")
@Tag(name = "Agency Management", description = "APIs for managing agencies")
public class AgencyController {
    private final AgencyService agencyService;
    private final AgencyRepository agencyRepo;

    public AgencyController(AgencyService agencyService, AgencyRepository agencyRepo) {
        this.agencyService = agencyService;
        this.agencyRepo = agencyRepo;
    }

    @Operation(summary = "Create new agency")
    @PostMapping("/create")
    public ResponseEntity<AgencyDTO> createAgency(@RequestBody AgencyDTO agencyDTO) {
        return ResponseEntity.ok(agencyService.createAgency(agencyDTO));
    }

    @Operation(summary = "Get agency by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Agency>> getAgencyById(@PathVariable Long id) {
        return ResponseEntity.ok(agencyRepo.findById(id));
    }

    @Operation(summary = "Update agency")
    @PutMapping("/{id}")
    public ResponseEntity<AgencyDTO> updateAgency(
            @PathVariable Long id,
            @RequestBody AgencyDTO agencyDTO) {
        return ResponseEntity.ok(agencyService.updateAgency(id, agencyDTO));
    }

    @Operation(summary = "Delete agency")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long id) {
        agencyService.deleteAgency(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Search agencies")
    @GetMapping("/search")
    public ResponseEntity<SearchResultDTO<AgencyDTO>> searchAgencies(AgencySearchDTO searchDTO) {
        return ResponseEntity.ok(agencyService.searchAgencies(searchDTO));
    }

    @Operation(summary = "Add category to agency")
    @PostMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<AgencyDTO> addCategory(
            @PathVariable Long id,
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(agencyService.assignCategory(id, categoryId));
    }

    @Operation(summary = "Remove category from agency")
    @DeleteMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<Optional<Agency>> removeCategory(
            @PathVariable Long id,
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(agencyService.removeCategory(id, categoryId));
    }


}
