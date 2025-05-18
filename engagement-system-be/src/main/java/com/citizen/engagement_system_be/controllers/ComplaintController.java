package com.citizen.engagement_system_be.controllers;

import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.dtos.search.ComplaintSearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.services.ComplaintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v3/user/complaints")
@Tag(name = "Complaint management", description = "APIs for managing complaints")
public class ComplaintController {
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @Operation(summary = "Uploading a complaint to a certain agency")
    @PostMapping("/create")
    public ResponseEntity<?> uploadComplaint(@RequestBody ComplaintDTO complaintDTO) {
        boolean success = complaintService.createComplaint(complaintDTO);
        if(success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get complaint by id")
    @GetMapping("/{id}")
    public ResponseEntity<ComplaintDTO> getComplaint(
            @Parameter(description = "Complaint ID", required= true)
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(complaintService.getComplaint(id));
    }

    @Operation(summary = "Update complaint")
    @PutMapping("/{id}")
    public ResponseEntity<ComplaintDTO> updateComplaint(
            @Parameter(description = "Complaint ID", required = true)
            @PathVariable Long id,
            @RequestBody ComplaintDTO complaintDTO) {
        return ResponseEntity.ok(complaintService.updateComplaint(id, complaintDTO));
    }

    @Operation(summary = "Delete complaint")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComplaint(
            @Parameter(description = "Complaint ID", required = true)
            @PathVariable Long id) {
        complaintService.deleteComplaint(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Search complaints")
    @GetMapping("/search")
    public ResponseEntity<SearchResultDTO<ComplaintDTO>> searchComplaints(ComplaintSearchDTO searchDTO) {
        return ResponseEntity.ok(complaintService.searchComplaints(searchDTO));
    }

    @Operation(summary = "Update complaint status")
    @PutMapping("/{id}/status")
    public ResponseEntity<ComplaintDTO> updateStatus(
            @Parameter(description = "Complaint ID", required = true)
            @PathVariable Long id,
            @RequestParam ComplaintStatus status) {
        return ResponseEntity.ok(complaintService.updateStatus(id, String.valueOf(status)));
    }

    @Operation(summary = "Assign complaint to agency")
    @PutMapping("/{id}/assign")
    public ResponseEntity<ComplaintDTO> assignToAgency(
            @Parameter(description = "Complaint ID", required = true)
            @PathVariable Long id,
            @RequestParam Long agencyId) {
        return ResponseEntity.ok(complaintService.assignToAgency(id, agencyId));
    }

    @Operation(summary = "Upload attachment")
    @PostMapping("/{id}/attachments")
    public ResponseEntity<Void> addAttachment(
            @Parameter(description = "Complaint ID", required = true)
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        complaintService.addAttachment(id, file);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Remove attachment")
    @DeleteMapping("/{id}/attachments/{attachmentId}")
    public ResponseEntity<Void> removeAttachment(
            @Parameter(description = "Complaint ID", required = true)
            @PathVariable Long id,
            @Parameter(description = "Attachment ID", required = true)
            @PathVariable Long attachmentId) {
        complaintService.removeAttachment(id, attachmentId);
        return ResponseEntity.ok().build();
    }


    @Operation(summary = "Get complaints by agency")
    @GetMapping("/agency/{agencyId}")
    public ResponseEntity<SearchResultDTO<ComplaintDTO>> getComplaintsByAgencyId(
            @Parameter(description = "Agency ID", required = true)
            @PathVariable Long agencyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(complaintService.getComplaintsByAgencyId(agencyId, page, size));
    }
}
