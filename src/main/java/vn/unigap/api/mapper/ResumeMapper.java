package vn.unigap.api.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.ArrayList;
import java.util.List;
import vn.unigap.api.dto.output.*;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.entity.Province;
import vn.unigap.api.entity.Resume;

public class ResumeMapper {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static ResumeDTO convertToDTO(Resume resume) {
    ResumeDTO dto = objectMapper.convertValue(resume, ResumeDTO.class);
    if (resume.getSeeker() != null) {
      dto.setSeekerId(resume.getSeeker().getId());
      dto.setSeekerName(resume.getSeeker().getName());
    }
    return dto;
  }

  public static ResumeByIdDTO convertToResumeByIdDTO(Resume resume) {
    ResumeByIdDTO dto = objectMapper.convertValue(resume, ResumeByIdDTO.class);
    if (resume.getSeeker() != null) {
      dto.setSeekerId(resume.getSeeker().getId());
      dto.setSeekerName(resume.getSeeker().getName());
    }
    if (resume.getProvinces() != null) {
      List<ProvinceDT0> provinceDT0List = new ArrayList<>();
      for (Province province : resume.getProvinces()) {
        ProvinceDT0 provinceDT0 = new ProvinceDT0();
        provinceDT0.setId(province.getId());
        provinceDT0.setName(province.getName());
        provinceDT0List.add(provinceDT0);
      }
      dto.setProvinces(provinceDT0List);
    }
    if (resume.getJobFields() != null) {
      List<JobFiedsDTO> jobFieldsDTOList = new ArrayList<>();
      for (JobField jobField : resume.getJobFields()) {
        JobFiedsDTO jobFieldDTO = new JobFiedsDTO();
        jobFieldDTO.setId(jobField.getId());
        jobFieldDTO.setName(jobField.getName());
        jobFieldsDTOList.add(jobFieldDTO);
      }
      dto.setFields(jobFieldsDTOList);
    }
    return dto;
  }
}
