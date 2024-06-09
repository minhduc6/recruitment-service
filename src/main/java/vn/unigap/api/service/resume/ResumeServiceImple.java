package vn.unigap.api.service.resume;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.input.CreateResumeRequest;
import vn.unigap.api.dto.input.UpdateResumeRequest;
import vn.unigap.api.dto.output.ResumeByIdDTO;
import vn.unigap.api.dto.output.ResumeDTO;
import vn.unigap.api.entity.*;
import vn.unigap.api.exceptions.NotFoundException;
import vn.unigap.api.mapper.ResumeMapper;
import vn.unigap.api.repository.JobFieldRepository;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.repository.ResumeRepository;
import vn.unigap.api.repository.SeekerRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ResumeServiceImple implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final JobFieldRepository jobFieldRepository;
    private final ProvinceRepository provinceRepository;
    private final SeekerRepository seekerRepository;

    @Override
    public Page<ResumeDTO> getAllResumeBySeekerId(int pageNumber, int pageSize, Integer seekerId) {
        Page<Resume> resumePage = null;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("title").ascending());
        if (seekerId == -1) {
            resumePage = resumeRepository.findAll(pageRequest);
        } else {
            resumePage = resumeRepository.findBySeekerId(seekerId, pageRequest);
        }

        return resumePage.map(ResumeMapper::convertToDTO);
    }

    @Override
    public ResumeByIdDTO getResumeById(Integer id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new NotFoundException("Resume not found with ID: " + id));
        return ResumeMapper.convertToResumeByIdDTO(resume);
    }

    @Override
    public void createResume(CreateResumeRequest createResumeRequest) {
        Resume resume = new Resume();

        // Fetch Seeker
        Seeker seeker = seekerRepository.findById(createResumeRequest.getSeekerId())
                .orElseThrow(() -> new NotFoundException("Seeker not found with ID: " + createResumeRequest.getSeekerId()));

        // Fetch job fields and provinces
        Set<JobField> jobFields = fetchJobFields(createResumeRequest.getFieldIds());
        Set<Province> provinces = fetchProvinces(createResumeRequest.getProvinceIds());

        resume.setTitle(createResumeRequest.getTitle());
        resume.setCareerObj(createResumeRequest.getCareerObj());
        resume.setSalary(createResumeRequest.getSalary());
        resume.setSeeker(seeker);
        resume.setJobFields(jobFields);
        resume.setProvinces(provinces);

        resumeRepository.save(resume);
    }

    @Override
    public void updateResume(Integer resumeId, UpdateResumeRequest updateResumeRequest) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new NotFoundException("Resume not found with ID: " + resumeId));

        // Fetch job fields and provinces
        Set<JobField> jobFields = fetchJobFields(updateResumeRequest.getFieldIds());
        Set<Province> provinces = fetchProvinces(updateResumeRequest.getProvinceIds());

        resume.setTitle(updateResumeRequest.getTitle());
        resume.setCareerObj(updateResumeRequest.getCareerObj());
        resume.setSalary(updateResumeRequest.getSalary());
        resume.setJobFields(jobFields);
        resume.setProvinces(provinces);

        resumeRepository.save(resume);
    }

    private Set<JobField> fetchJobFields(List<Integer> fieldIds) {
        List<JobField> jobFields = jobFieldRepository.findAllById(fieldIds);
        Set<Integer> foundJobFieldIds = jobFields.stream()
                .map(JobField::getId)
                .collect(Collectors.toSet());
        Set<Integer> missingJobFieldIds = fieldIds.stream()
                .filter(id -> !foundJobFieldIds.contains(id))
                .collect(Collectors.toSet());
        if (!missingJobFieldIds.isEmpty()) {
            throw new NotFoundException("Job Fields not found: " + missingJobFieldIds);
        }
        return new HashSet<>(jobFields);
    }

    private Set<Province> fetchProvinces(List<Integer> provinceIds) {
        List<Province> provinces = provinceRepository.findAllById(provinceIds);
        Set<Integer> foundProvinceIds = provinces.stream()
                .map(Province::getId)
                .collect(Collectors.toSet());
        Set<Integer> missingProvinceIds = provinceIds.stream()
                .filter(id -> !foundProvinceIds.contains(id))
                .collect(Collectors.toSet());
        if (!missingProvinceIds.isEmpty()) {
            throw new NotFoundException("Provinces not found: " + missingProvinceIds);
        }
        return new HashSet<>(provinces);
    }

    @Override
    public void deleteResume(Integer id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(() -> new NotFoundException("Resume not found with ID: " + id));
        // Remove associations from the job_jobfield join table
        resume.setJobFields(null);
        // Remove associations from the job_province join table
        resume.setProvinces(null);
        resumeRepository.delete(resume);
    }
}
