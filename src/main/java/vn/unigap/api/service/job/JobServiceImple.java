package vn.unigap.api.service.job;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.unigap.api.dto.input.BaseJobRequest;
import vn.unigap.api.dto.input.CreateJobRequest;
import vn.unigap.api.dto.input.UpdateJobRequest;
import vn.unigap.api.dto.output.*;
import vn.unigap.api.entity.*;
import vn.unigap.api.exceptions.BadRequestException;
import vn.unigap.api.exceptions.NotFoundException;
import vn.unigap.api.mapper.JobMapper;
import vn.unigap.api.mapper.SeekerMapper;
import vn.unigap.api.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobServiceImple implements JobService {
    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final JobFieldRepository jobFieldRepository;
    private final ProvinceRepository provinceRepository;
    private final ResumeRepository resumeRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        System.out.println("ID" + id);
        Job job = jobRepository.findByIdCheck(id).orElseThrow(() -> new NotFoundException("Job not found with ID: " + id));
        System.out.println("Job employer" + job.getEmployer());
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

    @Override
    public JobInformation getJobInformationAndSeekerRightFit(Integer jobId) {
        Job job = jobRepository.findByIdCheck(jobId)
                .orElseThrow(() -> new NotFoundException("Job not found with ID: " + jobId));

        String sql = "WITH job_fields_and_province_of_job AS (" +
                "    SELECT j.id, j.salary, jj.job_field_id, jp.province_id" +
                "    FROM jobs j" +
                "             INNER JOIN job_db.job_jobfield jj ON j.id = jj.job_id" +
                "             INNER JOIN job_db.job_province jp ON j.id = jp.job_id" +
                "    WHERE j.id = :jobId" +
                ")" +
                "SELECT r.id, jj.job_field_id, jp.province_id" +
                " FROM resume r" +
                "         INNER JOIN resume_jobfield jj ON r.id = jj.resume_id" +
                "         INNER JOIN resume_province jp ON r.id = jp.resume_id" +
                " WHERE jj.job_field_id IN (SELECT job_field_id FROM job_fields_and_province_of_job)" +
                " AND jp.province_id IN (SELECT province_id FROM job_fields_and_province_of_job)" +
                " AND r.salary < (SELECT salary FROM job_fields_and_province_of_job WHERE id = :jobId GROUP BY id, salary)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("jobId", jobId);

        List<Map<String, Object>> result = namedParameterJdbcTemplate.queryForList(sql, paramMap);

        List<Integer> resumeListId = new ArrayList<>();
        List<SeekerInfomation> seekerInfomationList = new ArrayList<>();

        // Process the results
        for (Map<String, Object> row : result) {
            resumeListId.add((Integer) row.get("id"));
        }

        for (Integer resumeId : resumeListId) {
            resumeRepository.findById(resumeId).ifPresent(resume -> {
                SeekerInfomation seekerInfomation = SeekerMapper.convertToSeekerInfomation(resume.getSeeker());
                if (seekerInfomation != null) {
                    seekerInfomationList.add(seekerInfomation);
                }
            });
        }
        JobInformation jobInformation = JobMapper.covertToJobInformation(job);
        jobInformation.setSeekerInfomationList(seekerInfomationList);
        return  jobInformation;
    }
}
