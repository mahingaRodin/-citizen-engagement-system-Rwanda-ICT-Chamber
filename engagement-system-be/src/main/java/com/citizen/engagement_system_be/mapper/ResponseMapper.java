package com.citizen.engagement_system_be.mapper;

import com.citizen.engagement_system_be.dtos.ResponseCreateDTO;
import com.citizen.engagement_system_be.dtos.ResponseDTO;
import com.citizen.engagement_system_be.models.Response;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {

    public ResponseDTO toDTO(Response response) {
        if (response == null) {
            return null;
        }

        ResponseDTO dto = new ResponseDTO();
        dto.setId(response.getId());
        dto.setComplaintId(response.getComplaint().getId());
        dto.setUserId(response.getUser().getId());
        dto.setMessage(response.getMessage());
        dto.setResponseType(response.getType());
        dto.setCreatedAt(response.getCreatedAt());

        return dto;
    }

    public Response toEntity(ResponseCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        Response response = new Response();
        response.setMessage(dto.getMessage());

        return response;
    }
}
