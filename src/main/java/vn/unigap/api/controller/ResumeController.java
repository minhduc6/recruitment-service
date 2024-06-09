package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.input.CreateResumeRequest;
import vn.unigap.api.dto.input.UpdateResumeRequest;
import vn.unigap.api.dto.output.ResumeByIdDTO;
import vn.unigap.api.dto.output.ResumeDTO;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.resume.ResumeService;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@Tag(name = "Resume API")
@AllArgsConstructor
public class ResumeController {
    private static final Logger logger = LoggerFactory.getLogger(ResumeController.class);
    private final ResumeService resumeService;

    @GetMapping
    public ResponseWrapper<PagingWrapper<?>> getAllResumeBySeekerId(@RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page, @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size, @RequestParam(defaultValue = "-1") Integer seekerId) {
        int adjustedPage = page - 1;
        logger.info("Received request: GET /resumes?page={}, size={}, seekerId={}", page, size, seekerId);
        Page<ResumeDTO> paginatedData = resumeService.getAllResumeBySeekerId(adjustedPage, size, seekerId);
        List<ResumeDTO> resumeDTOS = paginatedData.getContent();
        PagingWrapper<?> pagingWrapper = new PagingWrapper<>(page, size, paginatedData.getTotalElements(), paginatedData.getTotalPages(), resumeDTOS);
        logger.info("Returning response: GET /resumes?page={}, size={} ,seekerId={}  {} ", page, size, seekerId, pagingWrapper);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get resumes successfully", pagingWrapper);
    }

    @GetMapping("/{id}")
    public ResponseWrapper<ResumeByIdDTO> getResumeById(@PathVariable Integer id) {
        logger.info("Received request GET /resumes/{id} to get resume by ID: {}", id);
        ResumeByIdDTO resumeByIdDTO = resumeService.getResumeById(id);
        logger.info("Returning response GET /resumes/{id} with resume details for ID: {}", id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get Resume successfully " + id, resumeByIdDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper<ResumeDTO> createResume(@Valid @RequestBody CreateResumeRequest createResumeRequest) {
        logger.info("Received request POST /resumes to create resume. Payload: {}", createResumeRequest);
        resumeService.createResume(createResumeRequest);
        logger.info("Returning response POST /resumes Resume created successfully");
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Resume created successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseWrapper<ResumeDTO> updateResume(@PathVariable Integer id, @Valid @RequestBody UpdateResumeRequest updateResumeRequest) {
        logger.info("Received request PUT /resumes to update resume with ID: {}. Payload: {}", id, updateResumeRequest);
        resumeService.updateResume(id, updateResumeRequest);
        logger.info("Returning response PUT /resumes Employer updated successfully");
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Resume updated successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<ResumeDTO> deleteResume(@PathVariable Integer id) {
        logger.info("Received request DELETE /resumes to delete resume with ID: {}", id);
        resumeService.deleteResume(id);
        logger.info("Returning response DELETE /resumes Resume deleted successfully");
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Resume deleted successfully", null);
    }
}
