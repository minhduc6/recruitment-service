package vn.unigap.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.model.ResponseWrapper;
import vn.unigap.api.repository.JobFieldRepository;

import java.util.List;

@RestController
@RequestMapping("/job_fields")
@Tag(name = "Job Field API")
@AllArgsConstructor
public class JobFieldController {
    private  final JobFieldRepository jobFieldRepository;

    @GetMapping
    public ResponseWrapper<List<JobField>> getAllJobFields() {
        List<JobField> data = jobFieldRepository.findAll();
        return new ResponseWrapper<>(0, HttpStatus.CREATED.value(), "Success", data);
    }
}
