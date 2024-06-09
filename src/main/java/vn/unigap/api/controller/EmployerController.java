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
import vn.unigap.api.dto.input.CreateEmployerRequest;
import vn.unigap.api.dto.input.UpdateEmployerRequest;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.dto.output.EmployerDTO;
import vn.unigap.api.exceptions.GlobalExceptionHandler;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.employer.EmployerService;

import java.util.List;

@RestController
@RequestMapping("/employers")
@Tag(name = "Employer API")
@AllArgsConstructor
public class EmployerController {
    private static final Logger logger = LoggerFactory.getLogger(EmployerController.class);
    private final EmployerService employerService;

    @GetMapping
    public ResponseWrapper<PagingWrapper<?>> getAllEmployer(@RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page, @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size) {
        int adjustedPage = page - 1;
        logger.info("Received request: GET /employers?page={}, size={}", page, size);
        Page<EmployerDTO> paginatedData = employerService.getAllEmployersSortedByName(adjustedPage, size);
        List<EmployerDTO> employerDTOS = paginatedData.getContent();
        // Get the total number of elements and calculate the total number of pages
        long totalElements = paginatedData.getTotalElements();
        long totalPages = paginatedData.getTotalPages();
        PagingWrapper<?> pagingWrapper = new PagingWrapper<>(page, size, totalElements, totalPages, employerDTOS);
        logger.info("Returning response: GET /employers?page={}, size={} {} ", page, size, pagingWrapper);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get employers successfully", pagingWrapper);
    }

    @GetMapping("/{id}")
    public ResponseWrapper<EmployerByIdDto> getEmployerById(@PathVariable Integer id) {
        logger.info("Received request GET /employers/{id} to get employer by ID: {}", id);
        EmployerByIdDto employerDTO = employerService.getEmployerById(id);
        logger.info("Returning response GET /employers/{id} with employer details for ID: {}", id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get Employer successfully " + id, employerDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper<EmployerDTO> createEmployer(@Valid @RequestBody CreateEmployerRequest createEmployerRequest) {
        logger.info("Received request POST /employers to create employer. Payload: {}", createEmployerRequest);
        employerService.createEmployer(createEmployerRequest);
        logger.info("Returning response POST /employers Employer created successfully");
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Employer created successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseWrapper<EmployerDTO> updateEmployer(@PathVariable Integer id, @Valid @RequestBody UpdateEmployerRequest updateEmployerRequest) {
        logger.info("Received request PUT /employers to update employer with ID: {}. Payload: {}", id, updateEmployerRequest);
        employerService.updateEmployer(id, updateEmployerRequest);
        logger.info("Returning response PUT /employers Employer updated successfully");
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Employer updated successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<EmployerDTO> deleteEmployer(@PathVariable Integer id) {
        logger.info("Received request DELETE /employers to delete employer with ID: {}", id);
        employerService.deleteEmployer(id);
        logger.info("Returning response DELETE /employers Employer deleted successfully");
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Employer deleted successfully", null);
    }
}
