package vn.unigap.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import vn.unigap.api.dto.input.CreateEmployerRequest;
import vn.unigap.api.dto.input.UpdateEmployerRequest;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.dto.output.EmployerDTO;
import vn.unigap.api.entity.Employer;
import vn.unigap.api.entity.Province;
import vn.unigap.api.exceptions.BadRequestException;
import vn.unigap.api.exceptions.NotFoundException;
import vn.unigap.api.repository.EmployerRepository;
import vn.unigap.api.repository.ProvinceRepository;
import vn.unigap.api.service.employer.EmployerServiceImple;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    // @Mock
    // private EmployerRepository employerRepository;

    // @Mock
    // private ProvinceRepository provinceRepository;

    // @InjectMocks
    // private EmployerServiceImple employerService; // Assuming this is the class containing createEmployer method

    // @Test
    // public void testGetAllEmployersSortedByName() {
    //     // Mock employers
    //     List<Employer> employers = List.of(
    //             new Employer(),
    //             new Employer(),
    //             new Employer()
    //     );

    //     // Mock repository behavior
    //     PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name").ascending());
    //     Page<Employer> employerPage = new PageImpl<>(employers, pageRequest, employers.size());
    //     when(employerRepository.findAll(pageRequest)).thenReturn(employerPage);

    //     // Call the service method
    //     Page<EmployerDTO> resultPage = employerService.getAllEmployersSortedByName(0, 10);

    //     // Assert the result
    //     assertEquals(employers.size(), resultPage.getTotalElements());
    //     assertEquals(0, resultPage.getNumber());
    //     assertEquals(10, resultPage.getSize());
    // }

    // @Test
    // public void testCreateEmployerSuccess() {
    //     CreateEmployerRequest request = new CreateEmployerRequest("newemail@example.com", "Employer Name", 1, "Description");
    //     when(employerRepository.existsEmployerByEmail(request.getEmail())).thenReturn(false);
    //     when(provinceRepository.findById(request.getProvinceId())).thenReturn(Optional.of(new Province()));

    //     assertDoesNotThrow(() -> employerService.createEmployer(request));
    // }

    // @Test
    // public void testCreateEmployerEmailExists() {
    //     // Mocking
    //     CreateEmployerRequest request = new CreateEmployerRequest("existingemail@example.com", "Employer Name", 1, "Description");
    //     when(employerRepository.existsEmployerByEmail(request.getEmail())).thenReturn(true);

    //     // Execution & Assertion
    //     assertThrows(BadRequestException.class, () -> employerService.createEmployer(request));
    // }

    // @Test
    // public void testCreateEmployerInvalidProvinceId() {
    //     CreateEmployerRequest request = new CreateEmployerRequest("newemail@example.com", "Employer Name", 999, "Description");
    //     when(employerRepository.existsEmployerByEmail(request.getEmail())).thenReturn(false);
    //     when(provinceRepository.findById(request.getProvinceId())).thenReturn(Optional.empty());

    //     BadRequestException exception = assertThrows(BadRequestException.class, () -> employerService.createEmployer(request));
    //     assertEquals("Invalid province ID 999", exception.getMessage());
    // }


    // @Test
    // public void testUpdateEmployerSuccess() {
    //     // Create a request
    //     UpdateEmployerRequest request = new UpdateEmployerRequest("duc", 2, "description");

    //     // Create a mock employer
    //     Employer employer = new Employer();

    //     // Mock repository behavior to return the employer when findById is called
    //     when(employerRepository.findById(anyInt())).thenReturn(Optional.of(employer));

    //     // Create a mock province with ID 2
    //     Province province = new Province();
    //     province.setId(2);
    //     province.setName("example");
    //     employer.setProvince(province);

    //     // Call the service method
    //     employerService.updateEmployer(1, request);

    //     // Verify that save method is called once
    //     verify(employerRepository, times(1)).save(employer);
    // }

    // @Test
    // public void testUpdateEmployerNotFound() {
    //     UpdateEmployerRequest request = new UpdateEmployerRequest(/* provide update values */);

    //     // Mock repository behavior
    //     when(employerRepository.findById(anyInt())).thenReturn(Optional.empty());

    //     // Call the service method and assert that NotFoundException is thrown
    //     assertThrows(NotFoundException.class, () -> employerService.updateEmployer(1, request));
    // }

    // @Test
    // public void testGetEmployerById() {
    //     // Create a mock employer
    //     Employer employer = new Employer();
    //     employer.setId(1);
    //     employer.setName("Test Employer");
    //     employer.setDescription("Test Description");

    //     // Mock repository behavior to return the employer when findById is called
    //     when(employerRepository.findById(1)).thenReturn(Optional.of(employer));

    //     // Call the service method
    //     EmployerByIdDto employerByIdDto = employerService.getEmployerById(1);

    //     // Verify that the correct employer information is mapped to the DTO
    //     assertEquals(1, employerByIdDto.getId());
    //     assertEquals("Test Employer", employerByIdDto.getName());
    //     assertEquals("Test Description", employerByIdDto.getDescription());
    // }

    // @Test
    // public void testDeleteEmployer() {
    //     // Create a mock employer
    //     Employer employer = new Employer();
    //     employer.setId(1);

    //     // Mock repository behavior to return the employer when findById is called
    //     when(employerRepository.findById(1)).thenReturn(Optional.of(employer));

    //     // Call the service method
    //     employerService.deleteEmployer(1);

    //     // Verify that delete method is called once with the mock employer
    //     verify(employerRepository, times(1)).delete(employer);
    // }
}
