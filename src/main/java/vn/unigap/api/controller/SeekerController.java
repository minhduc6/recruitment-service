package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vn.unigap.api.dto.input.CreateSeekerRequest;
import vn.unigap.api.dto.input.UpdateSeekerRequest;
import vn.unigap.api.dto.output.SeekerDTO;
import vn.unigap.api.model.PagingWrapper;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.service.SeekerService;

import java.util.List;

@RestController
@RequestMapping("/seekers")
@Tag(name = "Seeker API")
@AllArgsConstructor
public class SeekerController {
    private final SeekerService seekerService;

    @GetMapping
    public ResponseWrapper<PagingWrapper<?>> getAllSeekerByProvinceID(@RequestParam(required = false, defaultValue = "1") @Parameter(example = "1") Integer page, @RequestParam(required = false, defaultValue = "10") @Parameter(example = "10") Integer size, @RequestParam(defaultValue = "-1") Integer provinceId) {
        int adjustedPage = page - 1;
        Page<SeekerDTO> paginatedData = seekerService.getAllSeekerByProvince(adjustedPage, size, provinceId);
        List<SeekerDTO> seekerDTOSS = paginatedData.getContent();
        PagingWrapper<?> pagingWrapper = new PagingWrapper<>(page, size, paginatedData.getTotalElements(), paginatedData.getTotalPages(), seekerDTOSS);

        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Get seekers successfully", pagingWrapper);
    }

    @GetMapping("/{id}")
    public ResponseWrapper<SeekerDTO> getSeekerById(@PathVariable Integer id) {
        SeekerDTO seekerDTO = seekerService.findById(id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Get Employer successfully " + id, seekerDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseWrapper<SeekerDTO> createSeeker(@Valid @RequestBody CreateSeekerRequest createSeekerRequest) {
        seekerService.createSeeker(createSeekerRequest);
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Seeker created successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseWrapper<SeekerDTO> updateSeeker(@PathVariable Integer id, @Valid @RequestBody UpdateSeekerRequest updateSeekerRequest) {
        seekerService.updateSeeker(id, updateSeekerRequest);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Seeker updated successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseWrapper<SeekerDTO> deleteSeeker(@PathVariable Integer id) {
        seekerService.deleteSeeker(id);
        return new ResponseWrapper<>(0, HttpStatus.OK.value(), "Seeker deleted successfully", null);
    }
}
