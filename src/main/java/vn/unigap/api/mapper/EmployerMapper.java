package vn.unigap.api.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.dto.output.EmployerDTO;
import vn.unigap.api.entity.Employer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class EmployerMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static EmployerDTO convertToDTO(Employer employer) {
        EmployerDTO dto = objectMapper.convertValue(employer, EmployerDTO.class);
        if (employer.getProvince() != null) {
            dto.setProvinceId(employer.getProvince().getId());
            dto.setProvinceName(employer.getProvince().getName());
        } else {
            dto.setProvinceId(null);
            dto.setProvinceName(null);
        }
        return dto;
    }

    public static EmployerByIdDto convertToEmployerByIdDto(Employer employer) {
        EmployerByIdDto dto = objectMapper.convertValue(employer, EmployerByIdDto.class);
        if (employer.getProvince() != null) {
            dto.setProvinceId(employer.getProvince().getId());
            dto.setProvinceName(employer.getProvince().getName());
        } else {
            dto.setProvinceId(null);
            dto.setProvinceName(null);
        }
        return dto;
    }
}