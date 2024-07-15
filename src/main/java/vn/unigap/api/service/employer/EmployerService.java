package vn.unigap.api.service.employer;

import org.springframework.data.domain.Page;
import vn.unigap.api.dto.input.CreateEmployerRequest;
import vn.unigap.api.dto.input.UpdateEmployerRequest;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.dto.output.EmployerDTO;

public interface EmployerService {
  Page<EmployerDTO> getAllEmployersSortedByName(int pageNumber, int pageSize);

  void createEmployer(CreateEmployerRequest createEmployerRequest);

  void updateEmployer(Integer id, UpdateEmployerRequest updateEmployerRequest);

  EmployerByIdDto getEmployerById(Integer id);

  void deleteEmployer(Integer id);
}
