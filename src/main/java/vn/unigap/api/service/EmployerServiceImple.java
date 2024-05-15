package vn.unigap.api.service;

import lombok.AllArgsConstructor;
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

import java.lang.reflect.Field;
import java.util.Objects;


@Service
@AllArgsConstructor
public class EmployerServiceImple implements EmployerService {
    private final EmployerRepository employerRepository;
    private final ProvinceRepository provinceRepository;


    public Page<EmployerDTO> getAllEmployersSortedByName(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending());
        Page<Employer> employerPage = employerRepository.findAll(pageRequest);

        return employerPage.map(EmployerMapper::convertToDTO);
    }

    @Override
    public void createEmployer(CreateEmployerRequest createEmployerRequest) {
        Employer employer = new Employer();

        // Check if the email already exists in the database
        boolean emailExists = employerRepository.existsEmployerByEmail(createEmployerRequest.getEmail());
        if (emailExists) {
            throw new BadRequestException("Email already exists");
        }

        // Check if the province exists in the database
        Province province = provinceRepository.findById(createEmployerRequest.getProvinceId())
                .orElseThrow(() -> new BadRequestException("Invalid province ID " + createEmployerRequest.getProvinceId()));

        employer.setEmail(createEmployerRequest.getEmail());
        employer.setName(createEmployerRequest.getName());
        employer.setDescription(createEmployerRequest.getDescription());
        employer.setProvince(province);

        employerRepository.save(employer);
    }

    @Override
    public void updateEmployer(Integer id, UpdateEmployerRequest updateEmployerRequest) {
        Employer employer = employerRepository.findById(id).orElseThrow(() -> new NotFoundException("Employer not found with ID: " + id));
        // Check if the province exists in the database

        // Check if the province ID has changed
        if (!Objects.equals(employer.getProvince().getId(), updateEmployerRequest.getProvinceId())) {
            // Check if the new province exists in the database
            Province newProvince = provinceRepository.findById(updateEmployerRequest.getProvinceId())
                    .orElseThrow(() -> new BadRequestException("Invalid province ID " + updateEmployerRequest.getProvinceId()));

            // Update the employer's province only if it has changed
            employer.setProvince(newProvince);
        }

        Field[] fields = UpdateEmployerRequest.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object updatedValue = field.get(updateEmployerRequest);
                if (updatedValue != null) {
                    Field existingField = Employer.class.getDeclaredField(field.getName());
                    existingField.setAccessible(true);
                    existingField.set(employer, updatedValue);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
                // Handle exception if needed
            }
        }
        employerRepository.save(employer);
    }

    @Override
    public EmployerByIdDto getEmployerById(Integer id) {
        Employer employer = employerRepository.findById(id).orElseThrow(() -> new NotFoundException("Employer not found with ID: " + id));
        return EmployerMapper.convertToEmployerByIdDto(employer);
    }

    @Override
    public void deleteEmployer(Integer id) {
        Employer employer = employerRepository.findById(id).orElseThrow(() -> new NotFoundException("Employer not found with ID: " + id));
        employerRepository.delete(employer);
    }


}
