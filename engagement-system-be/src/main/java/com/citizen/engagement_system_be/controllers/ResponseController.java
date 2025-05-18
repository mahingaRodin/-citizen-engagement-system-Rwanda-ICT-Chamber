package com.citizen.engagement_system_be.controllers;


import com.citizen.engagement_system_be.dtos.ResponseCreateDTO;
import com.citizen.engagement_system_be.dtos.ResponseDTO;
import com.citizen.engagement_system_be.dtos.search.ResponseSearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.models.Response;
import com.citizen.engagement_system_be.repository.ResponseRepository;
import com.citizen.engagement_system_be.services.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v3/responses")
@Tag(name = "Response management", description = "APIs for responses management")
public class ResponseController {
    private final ResponseService responseService;
    private final ResponseRepository responseRepository;

    public ResponseController(ResponseService responseService, ResponseRepository responseRepository) {
        this.responseService = responseService;
        this.responseRepository = responseRepository;
    }

    @Operation(summary = "Create new response")
    @PostMapping
    public ResponseEntity<ResponseDTO> createResponse(
            @RequestBody ResponseCreateDTO responseDTO,
            @RequestAttribute("userId") Long userId) {
        return ResponseEntity.ok(responseService.createResponse(responseDTO, userId));
    }

    @Operation(summary = "Get response by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Response>> getResponseById(@PathVariable Long id) {
        return ResponseEntity.ok(responseRepository.findById(id));
    }

    @Operation(summary = "Update response")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateResponse(
            @PathVariable Long id,
            @RequestBody ResponseDTO responseDTO) {
        return ResponseEntity.ok(responseService.updateResponse(id, responseDTO));
    }


    @Operation(summary = "Delete response")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id) {
        responseService.deleteResponse(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Search responses")
    @GetMapping("/search")
    public ResponseEntity<SearchResultDTO<ResponseDTO>> searchResponses(ResponseSearchDTO searchDTO) {
        return ResponseEntity.ok(responseService.searchResponses(searchDTO));
    }

    @Operation(summary = "Get responses by complaint")
    @GetMapping("/complaint/{complaintId}")
    public ResponseEntity<SearchResultDTO<ResponseDTO>> getResponsesByComplaint(
            @PathVariable Long complaintId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(responseService.getResponsesByComplaint(complaintId, page, size));
    }
}
