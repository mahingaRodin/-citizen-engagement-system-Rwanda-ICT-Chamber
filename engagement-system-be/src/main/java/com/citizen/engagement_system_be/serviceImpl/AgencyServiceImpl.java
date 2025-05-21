package com.citizen.engagement_system_be.serviceImpl;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.citizen.engagement_system_be.dtos.AgencyDTO;
import com.citizen.engagement_system_be.dtos.search.AgencySearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;
import com.citizen.engagement_system_be.exceptions.ResourceNotFoundException;
import com.citizen.engagement_system_be.mapper.AgencyMapper;
import com.citizen.engagement_system_be.models.Agency;
import com.citizen.engagement_system_be.models.Category;
import com.citizen.engagement_system_be.repository.AgencyRepository;
import com.citizen.engagement_system_be.repository.CategoryRepository;
import com.citizen.engagement_system_be.repository.UserRepository;
import com.citizen.engagement_system_be.services.AgencyService;

import jakarta.transaction.Transactional;

@Service
public class AgencyServiceImpl implements AgencyService {
    private final AgencyRepository agencyRepository;
    private final CategoryRepository categoryRepository;
    private final AgencyMapper agencyMapper;
    private final UserRepository userRepository;

    public AgencyServiceImpl(AgencyRepository agencyRepository, CategoryRepository categoryRepository, AgencyMapper agencyMapper, UserRepository userRepository, UserRepository userRepository1) {
        this.agencyRepository = agencyRepository;
        this.categoryRepository = categoryRepository;
        this.agencyMapper = agencyMapper;
        this.userRepository = userRepository1;
    }

    @Override
    @Transactional
    public AgencyDTO createAgency(AgencyDTO agency) {
        Agency ag = agencyMapper.toEntity(agency);
        Agency savedAgency = agencyRepository.save(ag);
        return agencyMapper.toDTO(savedAgency);
    }

    @Override
    public AgencyDTO getAgency(Long id) {
        Agency agency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));
        return agencyMapper.toDTO(agency);
    }

    @Override
    @Transactional
    public AgencyDTO updateAgency(Long id, AgencyDTO agencyDTO) {
        Agency existingAgency = agencyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));

        Agency updatedAgency = agencyMapper.toEntity(agencyDTO);
        updatedAgency.setId(id);
        Agency savedAgency = agencyRepository.save(updatedAgency);
        return agencyMapper.toDTO(savedAgency);
    }

    @Override
    @Transactional
    public void deleteAgency(Long id) {
        if (!agencyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agency not found");
        }
        agencyRepository.deleteById(id);
    }

    @Override
    public SearchResultDTO<AgencyDTO> searchAgencies(AgencySearchDTO searchDTO) {
        PageRequest pageRequest = PageRequest.of(
                searchDTO.getPage(),
                searchDTO.getSize(),
                Direction.fromString(searchDTO.getSortDirection()),
                searchDTO.getSortBy()
        );

        Page<Agency> agencies = agencyRepository.searchAgencies(
                searchDTO.getName(),
                searchDTO.getDescription(),
                searchDTO.getContactEmail(),
                searchDTO.getContactPhone(),
                pageRequest
        );

        return new SearchResultDTO<>(
                agencies.getContent().stream()
                        .map(agencyMapper::toDTO)
                        .collect(Collectors.toList()),
                agencies.getNumber(),
                agencies.getSize(),
                agencies.getTotalElements(),
                agencies.getTotalPages(),
                agencies.hasNext(),
                agencies.hasPrevious()
        );
    }

    @Override
    public AgencyDTO assignCategory(Long agencyId, Long categoryId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        agency.getCategory().setName(category.getName());
        agencyRepository.save(agency);

        return agencyMapper.toDTO(agency);
    }

    @Override
    public Optional<Agency> removeCategory(Long agencyId, Long categoryId) {
        Agency agency = agencyRepository.findById(agencyId)
                .orElseThrow(() -> new ResourceNotFoundException("Agency not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        agencyRepository.save(agency);
        return Optional.of(agency);
    }
}
