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
import vn.unigap.api.dto.input.CreateSeekerRequest;
import vn.unigap.api.dto.input.UpdateSeekerRequest;
import vn.unigap.api.dto.output.SeekerDTO;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.seeker.SeekerService;

@RestController
@RequestMapping("/seekers")
@Tag(name = "Seeker API")
@AllArgsConstructor
public class SeekerController {
  private static final Logger logger = LoggerFactory.getLogger(SeekerController.class);
  private final SeekerService seekerService;

  @GetMapping
  public ResponseWrapper<PagingWrapper<?>> getAllSeekerByProvinceID(
      @RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size,
      @RequestParam(defaultValue = "-1") Integer provinceId) {
    logger.info(
        "Received request: GET /seekers?page={}, size={}, provinceId={}", page, size, provinceId);
    int adjustedPage = page - 1;
    Page<SeekerDTO> paginatedData =
        seekerService.getAllSeekerByProvince(adjustedPage, size, provinceId);
    List<SeekerDTO> seekerDTOSS = paginatedData.getContent();
    PagingWrapper<?> pagingWrapper =
        new PagingWrapper<>(
            page,
            size,
            paginatedData.getTotalElements(),
            paginatedData.getTotalPages(),
            seekerDTOSS);
    logger.info(
        "Returning response: GET /seekers?page={}, size={} ,provinceId={}  {} ",
        page,
        size,
        provinceId,
        pagingWrapper);
    return new ResponseWrapper<>(
        0, HttpStatus.CREATED.value(), "Get seekers successfully", pagingWrapper);
  }

  @GetMapping("/{id}")
  public ResponseWrapper<SeekerDTO> getSeekerById(@PathVariable Integer id) {
    logger.info("Received request GET /seekers/{id} to get seeker by ID: {}", id);
    SeekerDTO seekerDTO = seekerService.findById(id);
    logger.info("Returning response GET /seekers/{id} with seeker details for ID: {}", id);
    return new ResponseWrapper<>(
        0, HttpStatus.OK.value(), "Get Employer successfully " + id, seekerDTO);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseWrapper<SeekerDTO> createSeeker(
      @Valid @RequestBody CreateSeekerRequest createSeekerRequest) {
    logger.info(
        "Received request POST /seekers to create seeker. Payload: {}", createSeekerRequest);
    seekerService.createSeeker(createSeekerRequest);
    logger.info("Returning response POST /seekers Seeker created successfully");
    return new ResponseWrapper<>(
        0, HttpStatus.CREATED.value(), "Seeker created successfully", null);
  }

  @PutMapping("/{id}")
  public ResponseWrapper<SeekerDTO> updateSeeker(
      @PathVariable Integer id, @Valid @RequestBody UpdateSeekerRequest updateSeekerRequest) {
    logger.info(
        "Received request PUT /seekers to update seeker with ID: {}. Payload: {}",
        id,
        updateSeekerRequest);
    seekerService.updateSeeker(id, updateSeekerRequest);
    logger.info("Returning response PUT /seekers Seeker updated successfully");
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Seeker updated successfully", null);
  }

  @DeleteMapping("/{id}")
  public ResponseWrapper<SeekerDTO> deleteSeeker(@PathVariable Integer id) {
    logger.info("Received request DELETE /seekers to delete seeker with ID: {}", id);
    seekerService.deleteSeeker(id);
    logger.info("Received request DELETE /seekers to delete seeker with ID: {}", id);
    return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Seeker deleted successfully", null);
  }
}
