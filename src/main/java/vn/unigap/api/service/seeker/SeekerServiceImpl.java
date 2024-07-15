package vn.unigap.api.service.seeker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.unigap.api.dto.input.CreateSeekerRequest;
import vn.unigap.api.dto.input.UpdateSeekerRequest;
import vn.unigap.api.dto.output.SeekerDTO;
import vn.unigap.api.entity.Province;
import vn.unigap.api.entity.Seeker;
import vn.unigap.api.exceptions.BadRequestException;
import vn.unigap.api.exceptions.NotFoundException;
import vn.unigap.api.mapper.SeekerMapper;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.repository.SeekerRepository;

@Service
@AllArgsConstructor
public class SeekerServiceImpl implements SeekerService {
  private final SeekerRepository seekerRepository;
  private final ProvinceRepository provinceRepository;

  @Override
  public Page<SeekerDTO> getAllSeekerByProvince(int pageNumber, int pageSize, Integer provinceId) {
    Page<Seeker> seekerPage = null;
    PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
    if (provinceId == -1) {
      seekerPage = seekerRepository.findAll(pageRequest);
    } else {
      seekerPage = seekerRepository.findAllByProvinceId(provinceId, pageRequest);
    }

    return seekerPage.map(SeekerMapper::convertToDTO);
  }

  @Override
  public SeekerDTO findById(Integer id) {
    Seeker seeker =
        seekerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Seeker not found with ID: " + id));
    return SeekerMapper.convertToDTO(seeker);
  }

  @Override
  public void createSeeker(CreateSeekerRequest createSeekerRequest) {
    Seeker seeker = new Seeker();

    // Check if the province exists in the database
    Province province =
        provinceRepository
            .findById(createSeekerRequest.getProvinceId())
            .orElseThrow(
                () ->
                    new BadRequestException(
                        "Invalid province ID " + createSeekerRequest.getProvinceId()));

    LocalDate birthday = null;
    try {
      birthday = LocalDate.parse(createSeekerRequest.getBirthday());
    } catch (DateTimeParseException e) {
      throw new BadRequestException("Invalid date format for birthday");
    }

    LocalDateTime now = LocalDateTime.now();

    if (birthday.isBefore(now.toLocalDate())) {
      seeker.setBirthday(createSeekerRequest.getBirthday());
    } else {
      throw new BadRequestException("Birthday is on or after the current date.");
    }
    seeker.setName(createSeekerRequest.getName());
    if (createSeekerRequest.getAddress() != null) {
      seeker.setAddress(createSeekerRequest.getAddress());
    }
    seeker.setProvince(province);

    seekerRepository.save(seeker);
  }

  @Override
  public void updateSeeker(Integer id, UpdateSeekerRequest updateSeekerRequest) {
    Seeker seeker =
        seekerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Seeker not found with ID: " + id));
    // Check if the province ID has changed
    if (seeker.getProvince() == null
        || !Objects.equals(seeker.getProvince().getId(), updateSeekerRequest.getProvinceId())) {
      // Check if the new province exists in the database
      Province newProvince =
          provinceRepository
              .findById(updateSeekerRequest.getProvinceId())
              .orElseThrow(
                  () ->
                      new BadRequestException(
                          "Invalid province ID " + updateSeekerRequest.getProvinceId()));

      // Update the employer's province only if it has changed
      seeker.setProvince(newProvince);
    }

    LocalDate birthday = null;
    try {
      birthday = LocalDate.parse(updateSeekerRequest.getBirthday());
    } catch (DateTimeParseException e) {
      throw new BadRequestException("Invalid date format for birthday");
    }

    LocalDateTime now = LocalDateTime.now();

    if (birthday.isBefore(now.toLocalDate())) {
      seeker.setBirthday(updateSeekerRequest.getBirthday());
    } else {
      throw new BadRequestException("Birthday is on or after the current date.");
    }

    try {
      // Copy properties from updateSeekerRequest to seeker
      BeanUtils.copyProperties(seeker, updateSeekerRequest);
    } catch (Exception e) {
      throw new BadRequestException(e.getMessage());
    }

    seekerRepository.save(seeker);
  }

  @Override
  public void deleteSeeker(Integer id) {
    Seeker seeker =
        seekerRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Seeker not found with ID: " + id));
    seekerRepository.delete(seeker);
  }
}
