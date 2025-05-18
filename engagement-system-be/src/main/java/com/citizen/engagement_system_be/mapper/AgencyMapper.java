package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.AgencyDTO;
import com.citizen.engagement_system_be.models.Agency;
import org.springframework.stereotype.Component;

@Component
public class AgencyMapper {
    public AgencyDTO toDTO(Agency agency) {
        if (agency == null) {
            return null;
        }

        AgencyDTO dto = new AgencyDTO();
        dto.setId(agency.getId());
        dto.setName(agency.getName());
        dto.setDescription(agency.getDescription());
        dto.setContactEmail(agency.getContactEmail());
        dto.setContactPhone(agency.getContactPhone());
        return dto;
    }

    public Agency toEntity(AgencyDTO dto) {
        if (dto == null) {
            return null;
        }

        Agency agency = new Agency();
        agency.setName(dto.getName());
        agency.setDescription(dto.getDescription());
        agency.setContactEmail(dto.getContactEmail());
        agency.setContactPhone(dto.getContactPhone());

        return agency;
    }
}
