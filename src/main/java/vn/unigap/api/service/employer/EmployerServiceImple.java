package vn.unigap.api.service.employer;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.input.CreateEmployerRequest;
import vn.unigap.api.dto.input.UpdateEmployerRequest;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.dto.output.EmployerDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.Province;
import vn.unigap.api.exceptions.BadRequestException;
import vn.unigap.api.exceptions.NotFoundException;
import vn.unigap.api.mapper.EmployerMapper;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.service.cache.CacheService;

@Slf4j
@Service
@AllArgsConstructor
public class EmployerServiceImple implements EmployerService {
  private final EmployerRepository employerRepository;
  private final ProvinceRepository provinceRepository;
  private final CacheService cacheService;

  public Page<EmployerDTO> getAllEmployersSortedByName(int pageNumber, int pageSize) {
    String cacheName = "employers";
    String cacheKey = pageNumber + "-" + pageSize;

    // Try to get the value from the cache
    Page<Employer> employerPage = (Page<Employer>) cacheService.get(cacheName, cacheKey);

    if (employerPage == null) {
      // If not in cache, query the database
      PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
      employerPage = employerRepository.findAll(pageRequest);

      // Put the result in the cache
      cacheService.put(cacheName, cacheKey, employerPage);
    }

    return employerPage.map(EmployerMapper::convertToDTO);
  }

  @Override
  public void createEmployer(CreateEmployerRequest createEmployerRequest) {
    Employer employer = new Employer();

    // Check if the email already exists in the database
    boolean emailExists =
        employerRepository.existsEmployerByEmail(createEmployerRequest.getEmail());
    if (emailExists) {
      throw new BadRequestException("Email already exists");
    }

    // Check if the province exists in the database
    Province province =
        provinceRepository
            .findById(createEmployerRequest.getProvinceId())
            .orElseThrow(
                () ->
                    new BadRequestException(
                        "Invalid province ID " + createEmployerRequest.getProvinceId()));
    //

    employer.setEmail(createEmployerRequest.getEmail());
    employer.setName(createEmployerRequest.getName());
    employer.setDescription(createEmployerRequest.getDescription());
    employer.setProvince(province);

    employerRepository.save(employer);
    cacheService.clear("employers");
  }

  @Override
  public void updateEmployer(Integer id, UpdateEmployerRequest updateEmployerRequest) {
    Employer employer =
        employerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employer not found with ID: " + id));
    // Check if the province exists in the database

    // Check if the province ID has changed
    if (!Objects.equals(employer.getProvince().getId(), updateEmployerRequest.getProvinceId())) {
      // Check if the new province exists in the database
      Province newProvince =
          provinceRepository
              .findById(updateEmployerRequest.getProvinceId())
              .orElseThrow(
                  () ->
                      new BadRequestException(
                          "Invalid province ID " + updateEmployerRequest.getProvinceId()));

      // Update the employer's province only if it has changed
      employer.setProvince(newProvince);
    }

    try {
      // Copy properties from updateSeekerRequest to seeker
      BeanUtils.copyProperties(employer, updateEmployerRequest);
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage());
    }

    employerRepository.save(employer);
    cacheService.clear("employers");
    cacheService.evict("employerById", id.toString());
  }

  @Override
  public EmployerByIdDto getEmployerById(Integer id) {
    String cacheName = "employerById";
    String cacheKey = id.toString();
    Employer employer = (Employer) cacheService.get(cacheName, cacheKey);

    if (employer != null) {
      return EmployerMapper.convertToEmployerByIdDto(employer);
    } else {
      // If not cached, fetch the result from the database
      Employer employerData =
          employerRepository
              .findById(id)
              .orElseThrow(() -> new NotFoundException("Employer not found with ID: " + id));
      EmployerByIdDto result = EmployerMapper.convertToEmployerByIdDto(employerData);

      // Cache the result
      cacheService.put(cacheName, cacheKey, employerData);
      return result;
    }
  }

  @Override
  public void deleteEmployer(Integer id) {
    Employer employer =
        employerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Employer not found with ID: " + id));
    employerRepository.delete(employer);
    cacheService.clear("employers");
    cacheService.evict("employerById", id.toString());
  }
}
