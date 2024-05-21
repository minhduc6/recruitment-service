package vn.unigap.api.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.unigap.api.dto.input.BaseJobRequest;
import vn.unigap.api.dto.input.CreateJobRequest;
import vn.unigap.api.dto.input.UpdateJobRequest;
import vn.unigap.api.dto.output.JobByIdDTO;
import vn.unigap.api.dto.output.JobDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.JobField;
import vn.unigap.api.entity.Province;
import vn.unigap.api.exceptions.BadRequestException;
import vn.unigap.api.exceptions.NotFoundException;
import vn.unigap.api.mapper.JobMapper;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.JobFieldRepository;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.api.repository.ProvinceRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImple implements JobService {
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final JobFieldRepository jobFieldRepository;
    private final ProvinceRepository provinceRepository;

    @Override
    public Page<JobDTO> getAllJobByEmployerId(int pageNumber, int pageSize, Integer employerId) {
        Page<Job> jobPage = null;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("title").ascending());
        if (employerId == -1) {
            jobPage = jobRepository.findAll(pageRequest);
        } else {
            jobPage = jobRepository.findByEmployerId(employerId, pageRequest);
        }

        return jobPage.map(JobMapper::convertToDTO);
    }

    @Override
    public JobByIdDTO getJobById(Integer id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        return JobMapper.convertToJobByIdDTO(job);
    }

    @Transactional
    public void createJob(CreateJobRequest createJobRequest) {
        Job job = new Job();
        createAndSaveJob(job, createJobRequest);
    }

    @Transactional
    public void updateJob(Integer id, UpdateJobRequest updateJobRequest) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        createAndSaveJob(job, updateJobRequest);
    }


    private void createAndSaveJob(Job job, BaseJobRequest baseJobRequest) {
        // Fetch employer
        Employer employer = employerRepository.findById(baseJobRequest.getEmployerId())
                .orElseThrow(() -> new NotFoundException("Employer not found with ID: " + baseJobRequest.getEmployerId()));

        // Fetch job fields and identify missing IDs
        List<JobField> jobFields = jobFieldRepository.findAllById(baseJobRequest.getFieldIds());
        Set<Integer> foundJobFieldIds = jobFields.stream()
                .map(JobField::getId)
                .collect(Collectors.toSet());
        Set<Integer> missingJobFieldIds = baseJobRequest.getFieldIds().stream()
                .filter(id -> !foundJobFieldIds.contains(id))
                .collect(Collectors.toSet());
        if (!missingJobFieldIds.isEmpty()) {
            throw new NotFoundException("Job Fields not found: " + missingJobFieldIds);
        }

        // Fetch provinces and identify missing IDs
        List<Province> provinces = provinceRepository.findAllById(baseJobRequest.getProvinceIds());
        Set<Integer> foundProvinceIds = provinces.stream()
                .map(Province::getId)
                .collect(Collectors.toSet());
        Set<Integer> missingProvinceIds = baseJobRequest.getProvinceIds().stream()
                .filter(id -> !foundProvinceIds.contains(id))
                .collect(Collectors.toSet());
        if (!missingProvinceIds.isEmpty()) {
            throw new NotFoundException("Provinces not found: " + missingProvinceIds);
        }

        // Parse the expiredAt date
        LocalDate expiredAt = null;
        try {
            expiredAt = LocalDate.parse(baseJobRequest.getExpiredAt());
        } catch (DateTimeParseException e) {
            throw new BadRequestException("Invalid date format for expired at");
        }


        // Populate job fields
        job.setTitle(baseJobRequest.getTitle());
        job.setDescription(baseJobRequest.getDescription());
        job.setQuantity(baseJobRequest.getQuantity());
        job.setSalary(baseJobRequest.getSalary());
        job.setExpired_at(expiredAt.atStartOfDay());
        job.setEmployer(employer);
        job.setJobFields(new HashSet<>(jobFields));
        job.setProvinces(new HashSet<>(provinces));

        // Save job entity
        jobRepository.save(job);
    }

    @Override
    public void deleteJob(Integer id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        // Remove associations from the job_jobfield join table
        job.setJobFields(null);

        // Remove associations from the job_province join table
        job.setProvinces(null);

        jobRepository.delete(job);
    }
}
