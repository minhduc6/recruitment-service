package vn.unigap.api.service;

import org.springframework.data.domain.Page;

import vn.unigap.api.dto.input.CreateJobRequest;
import vn.unigap.api.dto.input.UpdateJobRequest;
import vn.unigap.api.dto.output.JobByIdDTO;
import vn.unigap.api.dto.output.JobDTO;

public interface JobService {
    Page<JobDTO> getAllJobByEmployerId(int pageNumber, int pageSize, Integer employer_id);
    JobByIdDTO getJobById(Integer id);
    void createJob(CreateJobRequest createJobRequest);
    void updateJob(Integer id ,UpdateJobRequest updateJobRequest);
    void deleteJob(Integer id);
}
