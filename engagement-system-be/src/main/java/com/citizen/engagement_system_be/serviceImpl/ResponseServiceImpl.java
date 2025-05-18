package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.ResponseCreateDTO;
import com.citizen.engagement_system_be.dtos.ResponseDTO;
import com.citizen.engagement_system_be.dtos.search.ResponseSearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.enums.NotificationType;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.ResponseMapper;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.models.Response;
import com.citizen.engagement_system_be.models.User;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.repository.ResponseRepository;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.NotificationService;
import com.citizen.engagement_system_be.services.ResponseService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ResponseServiceImpl implements ResponseService {
    private final ResponseRepository responseRepo;
    private final ComplaintRepository complaintRepo;
    private final UserRepository userRepo;
    private final ResponseMapper responseMapper;
    private final NotificationService notifyService;

    public ResponseServiceImpl(ResponseRepository responseRepo, ComplaintRepository complaintRepo, UserRepository userRepo, ResponseMapper responseMapper, NotificationService notifyService) {
        this.responseRepo = responseRepo;
        this.complaintRepo = complaintRepo;
        this.userRepo = userRepo;
        this.responseMapper = responseMapper;
        this.notifyService = notifyService;
    }

    @Override
    @Transactional
    public ResponseDTO createResponse(ResponseCreateDTO responseDTO, Long userId) {
        Complaint complaint = complaintRepo.findById(responseDTO.getComplaintId())
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Response response = responseMapper.toEntity(responseDTO);
        response.setComplaint(complaint);
        response.setUser(user);
        Response savedResponse = responseRepo.save(response);

        // Notify complaint owner about new response
        notifyService.sendNotification(
                complaint.getUserId().getId(),
                NotificationType.NEW_RESPONSE,
                "New response to your complaint: " + complaint.getTitle(),
                complaint.getId()
        );

        return responseMapper.toDTO(savedResponse);
    }

    @Override
    public ResponseDTO getResponse(Long id) {
        Response response = responseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found"));
        return responseMapper.toDTO(response);
    }

    @Override
    @Transactional
    public ResponseDTO updateResponse(Long id, ResponseDTO responseDTO) {
        Response existingResponse = responseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found"));

        existingResponse.setMessage(responseDTO.getMessage());
        Response savedResponse = responseRepo.save(existingResponse);
        return responseMapper.toDTO(savedResponse);
    }

    @Override
    @Transactional
    public void deleteResponse(Long id) {
        if (!responseRepo.existsById(id)) {
            throw new ResourceNotFoundException("Response not found");
        }
        responseRepo.deleteById(id);
    }

    @Override
    public SearchResultDTO<ResponseDTO> searchResponses(ResponseSearchDTO searchDTO) {
        PageRequest pageRequest = PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Sort.Direction.fromString(searchDTO.getSortDirection()),
                searchDTO.getSortBy()
        );

        Page<Response> responses = responseRepo.searchResponses(
                searchDTO.getKeyword(),
//                searchDTO.getComplaintId(),
//                searchDTO.getUserId(),
//                searchDTO.getStartDate(),
//                searchDTO.getEndDate(),
                pageRequest
        );

        SearchResultDTO<ResponseDTO> searchResultDTO;
        searchResultDTO = new SearchResultDTO<ResponseDTO>(
                responses.getContent().stream()
                        .map(responseMapper::toDTO)
                        .collect(Collectors.toList()),
                responses.getNumber(),
                responses.getSize(),
                responses.getTotalElements(),
                responses.getTotalPages(),
                responses.hasNext(),
                responses.hasPrevious()
        );
        return
                searchResultDTO;
    }

    @Override
    public SearchResultDTO<ResponseDTO> getResponsesByComplaint(Long complaintId, int page, int size) {
        if (!complaintRepo.existsById(complaintId)) {
            throw new ResourceNotFoundException("Complaint not found with id: " + complaintId);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Response> responses = responseRepo.findByComplaintId(complaintId, pageable);

        // Using the Page constructor
        return new SearchResultDTO<>(
                responses.map(responseMapper::toDTO)
        );
    }
}
