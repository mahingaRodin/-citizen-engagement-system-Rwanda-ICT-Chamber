package com.citizen.engagement_system_be.services;

import com.citizen.engagement_system_be.dtos.ResponseCreateDTO;
import com.citizen.engagement_system_be.dtos.ResponseDTO;
import com.citizen.engagement_system_be.dtos.search.ResponseSearchDTO;
import com.citizen.engagement_system_be.dtos.search.SearchResultDTO;

public interface ResponseService {
    ResponseDTO createResponse(ResponseCreateDTO responseDTO, Long userId);
    ResponseDTO getResponse(Long id);
    ResponseDTO updateResponse(Long id, ResponseDTO responseDTO);
    void deleteResponse(Long id);
    SearchResultDTO<ResponseDTO> searchResponses(ResponseSearchDTO searchDTO);

    SearchResultDTO<ResponseDTO> getResponsesByComplaint(Long complaintId, int page, int size);
}
