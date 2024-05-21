package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.input.CreateResumeRequest;
import vn.unigap.api.dto.input.UpdateResumeRequest;
import vn.unigap.api.dto.output.ResumeByIdDTO;
import vn.unigap.api.dto.output.ResumeDTO;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.ResumeService;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@Tag(name = "Resume API")
@AllArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    public ResponseWrapper<PagingWrapper<?>> getAllResumeBySeekerId(@RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page, @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size, @RequestParam(defaultValue = "-1") Integer seekerId) {
        int adjustedPage = page - 1;
        Page<ResumeDTO> paginatedData = resumeService.getAllResumeBySeekerId(adjustedPage, size, seekerId);

        List<ResumeDTO> resumeDTOS = paginatedData.getContent();
        PagingWrapper<?> pagingWrapper = new PagingWrapper<>(page, size, paginatedData.getTotalElements(), paginatedData.getTotalPages(), resumeDTOS);

        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get resumes successfully", pagingWrapper);
    }

    @GetMapping("/{id}")
    public ResponseWrapper<ResumeByIdDTO> getSeekerById(@PathVariable Integer id) {
        ResumeByIdDTO resumeByIdDTO = resumeService.getResumeById(id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get Resume successfully " + id, resumeByIdDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper<ResumeDTO> createResume(@Valid @RequestBody CreateResumeRequest createResumeRequest) {
        resumeService.createResume(createResumeRequest);
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Resume created successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseWrapper<ResumeDTO> updateResume(@PathVariable Integer id, @Valid @RequestBody UpdateResumeRequest updateResumeRequest) {
        resumeService.updateResume(id,updateResumeRequest);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Resume updated successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<ResumeDTO> deleteResume(@PathVariable Integer id) {
        resumeService.deleteResume(id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Resume deleted successfully", null);
    }
}
