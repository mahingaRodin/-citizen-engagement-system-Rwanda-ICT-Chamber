package com.citizen.engagement_system_be.repository;

import com.citizen.engagement_system_be.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    List<Attachment> findByComplaintId(Long complaintId);
    void deleteByComplaintId(Long complaintId);
}
