package vn.unigap.api.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import vn.unigap.api.dto.output.JobByIdDTO;
import vn.unigap.api.dto.output.JobDTO;
import vn.unigap.api.dto.output.JobFiedsDTO;
import vn.unigap.api.dto.output.ProvinceDT0;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.entity.Province;

import java.util.ArrayList;
import java.util.List;

public class JobMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static JobDTO convertToDTO(Job job) {
        JobDTO dto = objectMapper.convertValue(job, JobDTO.class);
        if (job.getEmployer() != null) {
            dto.setEmployerId(job.getEmployer().getId());
            dto.setEmployerName(job.getEmployer().getName());
        }
        return dto;
    }


    public static JobByIdDTO convertToJobByIdDTO(Job job) {
        JobByIdDTO dto = objectMapper.convertValue(job, JobByIdDTO.class);
        if (job.getEmployer() != null) {
            dto.setEmployerId(job.getEmployer().getId());
            dto.setEmployerName(job.getEmployer().getName());
        }
        if (job.getProvinces() != null) {
            List<ProvinceDT0> provinceDT0List = new ArrayList<>();
            for (Province province : job.getProvinces()) {
                ProvinceDT0 provinceDT0 = new ProvinceDT0();
                provinceDT0.setId(province.getId());
                provinceDT0.setName(province.getName());
                // Set other properties of ProvinceDT0 if needed
                provinceDT0List.add(provinceDT0);
            }
            dto.setProvinces(provinceDT0List);
        }
        if (job.getJobFields() != null) {
            List<JobFiedsDTO> jobFieldsDTOList = new ArrayList<>();
            for (JobField jobField : job.getJobFields()) {
                JobFiedsDTO jobFieldDTO = new JobFiedsDTO();
                jobFieldDTO.setId(jobField.getId());
                jobFieldDTO.setName(jobField.getName());
                // Set other properties of JobFiedsDTO if needed
                jobFieldsDTOList.add(jobFieldDTO);
            }
            dto.setFields(jobFieldsDTOList);
        }
        return dto;
    }
}
