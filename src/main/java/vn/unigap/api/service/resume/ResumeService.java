package vn.unigap.api.service.resume;

import org.springframework.data.domain.Page;
import vn.unigap.api.dto.input.CreateResumeRequest;
import vn.unigap.api.dto.input.UpdateResumeRequest;
import vn.unigap.api.dto.output.ResumeByIdDTO;
import vn.unigap.api.dto.output.ResumeDTO;

public interface ResumeService {
  Page<ResumeDTO> getAllResumeBySeekerId(int pageNumber, int pageSize, Integer seekerId);

  ResumeByIdDTO getResumeById(Integer id);

  void createResume(CreateResumeRequest createResumeRequest);

  void updateResume(Integer resumeId, UpdateResumeRequest updateResumeRequest);

  void deleteResume(Integer id);
}
