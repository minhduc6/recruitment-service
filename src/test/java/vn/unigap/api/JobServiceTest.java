package vn.unigap.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import vn.unigap.api.dto.output.JobInformation;
import vn.unigap.api.entity.Job;
import vn.unigap.api.entity.Resume;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.repository.JobRepository;
import vn.unigap.api.repository.ResumeRepository;
import vn.unigap.api.service.job.JobServiceImple;

@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
public class JobServiceTest {
  @Mock private JobRepository jobRepository;

  @Mock private ResumeRepository resumeRepository;

  @Mock private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @InjectMocks private JobServiceImple jobService;

  @BeforeEach
  void setUp() {
    Job job = new Job();
    job.setId(1);
    job.setSalary(50000);

    Resume resume = new Resume();
    resume.setId(1);
    Seeker seeker = new Seeker();
    resume.setSeeker(seeker);

    when(jobRepository.findByIdCheck(eq(1))).thenReturn(Optional.of(job));
    when(namedParameterJdbcTemplate.queryForList(anyString(), anyMap()))
        .thenReturn(List.of(Map.of("id", 1)));
    when(resumeRepository.findById(1)).thenReturn(Optional.of(resume));
  }

  @Test
  void testGetJobInformationAndSeekerRightFit() {
    Integer jobId = 1;
    JobInformation jobInformation = jobService.getJobInformationAndSeekerRightFit(jobId);

    assertNotNull(jobInformation);

    verify(jobRepository, times(1)).findByIdCheck(eq(jobId));
    verify(namedParameterJdbcTemplate, times(1)).queryForList(anyString(), anyMap());
    verify(resumeRepository, times(1)).findById(1);
  }
}
