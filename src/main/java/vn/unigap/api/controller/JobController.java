package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.input.CreateJobRequest;
import vn.unigap.api.dto.input.UpdateJobRequest;
import vn.unigap.api.dto.output.JobByIdDTO;
import vn.unigap.api.dto.output.JobDTO;
import vn.unigap.api.dto.output.JobInformation;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.job.JobService;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job API")
@AllArgsConstructor
public class JobController {
  private static final Logger logger = LoggerFactory.getLogger(JobController.class);
  private final JobService jobService;

  @GetMapping
  public ResponseWrapper<PagingWrapper<?>> getAllJobByEmployerId(
      @RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size,
      @RequestParam(required = false, defaultValue = "-1") @Parameter(example = "-1")
          Integer employerId) {
    int adjustedPage = page - 1;
    logger.info(
        "Received request: GET /jobs?page={}, size={}, employerId={}", page, size, employerId);
    Page<JobDTO> paginatedData = jobService.getAllJobByEmployerId(adjustedPage, size, employerId);
    List<JobDTO> jobDTOs = paginatedData.getContent();
    // Get the total number of elements and calculate the total number of pages
    long totalElements = paginatedData.getTotalElements();
    long totalPages = paginatedData.getTotalPages();
    PagingWrapper<?> pagingWrapper =
        new PagingWrapper<>(page, size, totalElements, totalPages, jobDTOs);
    logger.info(
        "Returning response: GET /jobs?page={}, size={} , employerId={},{} ",
        page,
        size,
        employerId,
        pagingWrapper);
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get jobs successfully", pagingWrapper);
  }

  @GetMapping("/job-information")
  public ResponseWrapper<JobInformation> getJobInformationAndSeekerRightFit(
      @RequestParam(required = false, defaultValue = "-1") @Parameter(example = "-1")
          Integer jobId) {
    logger.info("Received request: GET /job-information? jobId={}", jobId);
    JobInformation jobInformation = jobService.getJobInformationAndSeekerRightFit(jobId);
    logger.info("Returning response: GET /job-information? jobId={}  {} ", jobId, jobInformation);
    return new ResponseWrapper<>(
        0, HttpStatus.OK.value(), "Get Job Information successfully " + jobId, jobInformation);
  }

  @GetMapping("/{id}")
  public ResponseWrapper<JobByIdDTO> getJobById(@PathVariable Integer id) {
    logger.info("Received request: GET /jobs/{}", id);
    JobByIdDTO jobByIdDTO = jobService.getJobById(id);
    logger.info("Returning response: GET /jobs/{} {}", id, jobByIdDTO);
    return new ResponseWrapper<>(
        0, HttpStatus.OK.value(), "Get Job successfully " + id, jobByIdDTO);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseWrapper<JobDTO> createJob(@Valid @RequestBody CreateJobRequest createJobRequest) {
    logger.info("Received request POST /jobs to create job. Payload: {}", createJobRequest);
    jobService.createJob(createJobRequest);
    logger.info("Returning response POST /jobs Job created successfully");
    return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Job created successfully", null);
  }

  @PutMapping("/{id}")
  public ResponseWrapper<JobDTO> updateJob(
      @PathVariable Integer id, @Valid @RequestBody UpdateJobRequest updateJobRequest) {
    logger.info(
        "Received request PUT /jobs to update job with ID: {}. Payload: {}", id, updateJobRequest);
    jobService.updateJob(id, updateJobRequest);
    logger.info("Returning response PUT /jobs Job updated successfully");
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Job updated successfully", null);
  }

  @DeleteMapping("/{id}")
  public ResponseWrapper<JobDTO> deleteJob(@PathVariable Integer id) {
    logger.info("Received request DELETE /jobs to delete job with ID: {}", id);
    jobService.deleteJob(id);
    logger.info("Returning response DELETE /jobs Job deleted successfully");
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Job deleted successfully", null);
  }
}
