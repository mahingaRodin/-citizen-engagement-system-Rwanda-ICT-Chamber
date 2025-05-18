package com.citizen.engagement_system_be.serviceImpl;

import com.citizen.engagement_system_be.dtos.AttachmentDTO;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.fileHandling.File;
import com.citizen.engagement_system_be.fileHandling.FileStorageService;
import com.citizen.engagement_system_be.mapper.AttachmentMapper;
import com.citizen.engagement_system_be.models.Attachment;
import com.citizen.engagement_system_be.models.Complaint;
import com.citizen.engagement_system_be.repository.AttachmentRepository;
import com.citizen.engagement_system_be.repository.ComplaintRepository;
import com.citizen.engagement_system_be.services.AttachmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final ComplaintRepository complaintRepository;
    private final AttachmentMapper attachmentMapper;
    private final FileStorageService fileStorageService;

    @Value("${uploads.directory}")
    private String dir;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, ComplaintRepository complaintRepository, AttachmentMapper attachmentMapper, FileStorageService fileStorageService) {
        this.attachmentRepository = attachmentRepository;
        this.complaintRepository = complaintRepository;
        this.attachmentMapper = attachmentMapper;
        this.fileStorageService = fileStorageService;
    }

    @Override
    @Transactional
    public AttachmentDTO uploadAttachment(Long complaintId, MultipartFile file) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        Attachment attachment = new Attachment();
        attachment.setComplaint(complaint);
        attachment.setFile((File) file);

        Attachment savedAttachment = attachmentRepository.save(attachment);
        return attachmentMapper.toDTO(savedAttachment);
    }

    @Override
    public AttachmentDTO getAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found"));
        return attachmentMapper.toDTO(attachment);
    }

    @Override
    public List<AttachmentDTO> getComplaintAttachments(Long complaintId) {
        if (!complaintRepository.existsById(complaintId)) {
            throw new ResourceNotFoundException("Complaint not found");
        }

        return attachmentRepository.findByComplaintId(complaintId).stream()
                .map(attachmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAttachment(Long id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found"));
//
//        // Delete file from storage
//        fileStorageService.deleteFile(attachment.getFilePath());

        // Delete database record
        attachmentRepository.delete(attachment);
    }

    @Override
    public byte[] downloadAttachment(Long id) {
        return new byte[0];
    }
}
