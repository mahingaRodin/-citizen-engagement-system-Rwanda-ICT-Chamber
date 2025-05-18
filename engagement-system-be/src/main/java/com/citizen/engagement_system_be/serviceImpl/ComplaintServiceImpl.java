package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.ComplaintDTO;
import com.citizen.engagement_system_be.dtos.search.ComplaintSearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.enums.ComplaintPriority;
import com.citizen.engagement_system_be.enums.ComplaintStatus;
import com.citizen.engagement_system_be.enums.ComplaintType;
import com.citizen.engagement_system_be.enums.NotificationType;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.fileHandling.File;
import com.citizen.engagement_system_be.fileHandling.FileStorageService;
import com.citizen.engagement_system_be.mapper.ComplaintMapper;
import com.citizen.engagement_system_be.models.*;
import com.citizen.engagement_system_be.repository.*;
import com.citizen.engagement_system_be.services.ComplaintService;
import com.citizen.engagement_system_be.services.NotificationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final AgencyRepository agencyRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final NotificationService notificationService;
    private final FileStorageService fileStorageService;
    private final ComplaintMapper complaintMapper;

    @Value("${uploads.directory}")
    private String uploadDir;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository, UserRepository userRepository, AgencyRepository agencyRepository, CategoryRepository categoryRepository, AttachmentRepository attachmentRepository, NotificationService notificationService, FileStorageService fileStorageService, ComplaintMapper complaintMapper) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.agencyRepository = agencyRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
        this.notificationService = notificationService;
        this.fileStorageService = fileStorageService;
        this.complaintMapper = complaintMapper;
    }

    @Override
    @Transactional
    public boolean createComplaint(ComplaintDTO complaint) {
        Optional<Category> category = categoryRepository.findById(complaint.getCategoryId());
        Optional<Agency> agency = agencyRepository.findById(complaint.getAgencyId().getId());

//        Agency agency = agencyRepository.findById(complaint.getAgencyId())
//                .orElseThrow(() -> new RuntimeException("Agency Not Found"));


        if(category.isPresent() && agency.isPresent()) {
            Complaint compl = new Complaint();
            compl.setTitle(complaint.getTitle());
            compl.setDescription(complaint.getDescription());
            compl.setPriority(ComplaintPriority.MEDIUM);
            compl.setCreatedAt(LocalDateTime.now());
            compl.setType(ComplaintType.OTHER);
            compl.setLocation(complaint.getLocation());
            compl.setAgencyId(complaint.getAgencyId());

            complaintRepository.save(compl);
            return true;
        }

//        Complaint c = new Complaint();
//        c.setTitle(complaint.getTitle());
//        complaint.setDescription(complaint.getDescription());
//        complaint.setLocation(complaint.getLocation());
//        complaint.setCategoryId(complaint.getCategoryId());
//        complaint.setAgencyId(agency.getId());
//        complaint.setStatus(ComplaintStatus.OPEN);
//        complaint.setPriority(complaint.getPriority());
//
//        Complaint savedComplaint = complaintRepository.save(c);
//        //send notification to the agency
//        notificationService.sendNotification(
//                agency.getId(),
//                NotificationType.COMPLAINT_CREATED,
//                "New Complaint Received: " + complaint.getTitle(),
//                savedComplaint.getId()
//        );
        return false;
    }

    @Override
    @Transactional
    public SearchResultDTO<ComplaintDTO> getComplaintsByAgencyId(Long agencyId, int page, int size) {
        if (!agencyRepository.existsById(agencyId)) {
            throw new ResourceNotFoundException("Agency not found with id: " + agencyId);
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Complaint> complaints = complaintRepository.findByAgencyId(agencyId, pageRequest);

        return new SearchResultDTO<>(
                complaints.getContent().stream()
                        .map(complaintMapper::toDTO)
                        .collect(Collectors.toList()),
                complaints.getNumber(),
                complaints.getSize(),
                complaints.getTotalElements(),
                complaints.getTotalPages(),
                complaints.hasNext(),
                complaints.hasPrevious()
        );
    }

    @Override
    public ComplaintDTO getComplaint(Long complaintId) {
        return null;
    }

    @Override
    public ComplaintDTO updateComplaint(Long userId, ComplaintDTO complaint) {
        return null;
    }

    @Override
    public void deleteComplaint(Long complaintId) {

    }

    @Override
    public SearchResultDTO<ComplaintDTO> searchComplaints(ComplaintSearchDTO searchDTO) {
        return null;
    }

    @Override
    public ComplaintDTO updateStatus(Long id, String status) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        ComplaintStatus newStatus = ComplaintStatus.valueOf(status.toUpperCase());
        complaint.setStatus(newStatus);

        if (newStatus == ComplaintStatus.RESOLVED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }

        Complaint updatedComplaint = complaintRepository.save(complaint);

        // Notify user about status change
        notificationService.sendNotification(
                complaint.getUserId().getId(),
                NotificationType.STATUS_CHANGED,
                "Your complaint status has been updated to: " + status,
                complaint.getId()
        );

        return complaintMapper.toDTO(updatedComplaint);
    }

    @Override
    public ComplaintDTO assignToAgency(Long id, Long agencyId) {
        return null;
    }

    @Override
    @Transactional
    public void addAttachment(Long complaintId, MultipartFile file) {
        Complaint c = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint Not Found"));

        String filePath = fileStorageService.save(file,uploadDir);
        Attachment a = new Attachment();
        a.setComplaint(c);
        a.setFile((File) file);
        a.setUploadedAt(LocalDateTime.now());
        attachmentRepository.save(a);
    }

    @Override
    public void removeAttachment(Long complaintId, Long attachmentId) {

    }
}
