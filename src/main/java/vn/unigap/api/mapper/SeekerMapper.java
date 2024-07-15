package vn.unigap.api.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import vn.unigap.api.dto.output.SeekerDTO;
import vn.unigap.api.dto.output.SeekerInfomation;
import vn.unigap.api.entity.Seeker;

public class SeekerMapper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static SeekerDTO convertToDTO(Seeker seeker) {
    SeekerDTO dto = objectMapper.convertValue(seeker, SeekerDTO.class);
    if (seeker.getProvince() != null) {
      dto.setProvinceId(seeker.getProvince().getId());
      dto.setProvinceName(seeker.getProvince().getName());
    } else {
      dto.setProvinceId(null);
      dto.setProvinceName(null);
    }
    return dto;
  }

  public static SeekerInfomation convertToSeekerInfomation(Seeker seeker) {
    SeekerInfomation dto = objectMapper.convertValue(seeker, SeekerInfomation.class);
    return dto;
  }
}
