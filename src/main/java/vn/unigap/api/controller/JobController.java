package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.input.CreateEmployerRequest;
import vn.unigap.api.dto.input.CreateJobRequest;
import vn.unigap.api.dto.input.UpdateEmployerRequest;
import vn.unigap.api.dto.input.UpdateJobRequest;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.dto.output.EmployerDTO;
import vn.unigap.api.dto.output.JobByIdDTO;
import vn.unigap.api.dto.output.JobDTO;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.EmployerService;
import vn.unigap.api.service.JobService;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Job API")
@AllArgsConstructor
public class JobController {
    private final JobService jobService;

    @GetMapping
    public ResponseWrapper<PagingWrapper<?>> getAllJobByEmployerId(@RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page, @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size, @RequestParam(required = false, defaultValue = "-1") @Parameter(example = "-1") Integer employerId) {
        int adjustedPage = page - 1;
        Page<JobDTO> paginatedData = jobService.getAllJobByEmployerId(adjustedPage, size, employerId);
        List<JobDTO> JobDTOs = paginatedData.getContent();

        // Get the total number of elements and calculate the total number of pages
        long totalElements = paginatedData.getTotalElements();
        long totalPages = paginatedData.getTotalPages();
        PagingWrapper<?> pagingWrapper = new PagingWrapper<>(page, size, totalElements, totalPages, JobDTOs);

        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get jobs successfully", pagingWrapper);
    }

    @GetMapping("/{id}")
    public ResponseWrapper<JobByIdDTO> getJobById(@PathVariable Integer id) {
        JobByIdDTO jobByIdDTO = jobService.getJobById(id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get Employer successfully " + id, jobByIdDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper<JobDTO> createJob(@Valid @RequestBody CreateJobRequest createJobRequest) {
        jobService.createJob(createJobRequest);
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Job created successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseWrapper<JobDTO> updateJob(@PathVariable Integer id, @Valid @RequestBody UpdateJobRequest updateJobRequest) {
        jobService.updateJob(id, updateJobRequest);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Job updated successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<JobDTO> deleteJob(@PathVariable Integer id) {
        jobService.deleteJob(id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Job deleted successfully", null);
    }
}