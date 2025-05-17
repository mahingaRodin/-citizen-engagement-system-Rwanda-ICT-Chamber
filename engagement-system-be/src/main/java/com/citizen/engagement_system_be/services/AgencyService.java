package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.AgencyDTO;
import com.citizen.engagement_system_be.dtos.search.AgencySearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;

public interface AgencyService {
    AgencyDTO createAgency(AgencyDTO agency);
    AgencyDTO getAgency(Long id);
    AgencyDTO updateAgency(Long id, AgencyDTO agencyDTO);
    void deleteAgency(Long id);
    SearchResultDTO<AgencyDTO> searchAgencies(AgencySearchDTO searchDTO);
    void assignCategory(Long agencyId, Long categoryId);
    void removeCategory(Long agencyId, Long categoryId);
}
