package vn.unigap.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import vn.unigap.api.controller.EmployerController;
import vn.unigap.api.dto.input.CreateEmployerRequest;
import vn.unigap.api.dto.input.UpdateEmployerRequest;
import vn.unigap.api.dto.output.EmployerByIdDto;
import vn.unigap.api.service.employer.EmployerService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(EmployerController.class)
class EmployerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployerService employerService;


    @Test
    void testGetEmployerById() throws Exception {
        // Mock data
        Integer employerId = 1;
        EmployerByIdDto employerDto = EmployerByIdDto.builder()
                .id(employerId)
                .email("test@example.com")
                .name("Test Employer")
                .description("Test description")
                .provinceId(1)
                .provinceName("Test Province")
                .build();

        // Mock service method call
        when(employerService.getEmployerById(anyInt())).thenReturn(employerDto);

        // Perform the HTTP GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/employers/{id}", employerId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Get Employer successfully " + employerId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.id").value(employerDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.email").value(employerDto.getEmail()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.name").value(employerDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.provinceId").value(employerDto.getProvinceId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object.provinceName").value(employerDto.getProvinceName()));
    }

    @Test
    void testCreateEmployer() throws Exception {
        // Mock data
        CreateEmployerRequest createRequest = new CreateEmployerRequest(
                "test@example.com",
                "Test Employer",
                1,
                "Test description"
        );

        // Mock service method call
        doNothing().when(employerService).createEmployer(any(CreateEmployerRequest.class));

        // Perform the HTTP POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/employers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(createRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Employer created successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").doesNotExist());
    }


    @Test
    void testUpdateEmployer() throws Exception {
        // Mock data
        int employerId = 1;
        UpdateEmployerRequest updateRequest = new UpdateEmployerRequest(
                "Updated Test Employer",
                1,
                "Updated Test description"
        );

        // Mock service method call
        doNothing().when(employerService).updateEmployer(employerId, updateRequest);

        // Perform the HTTP PUT request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/employers/{id}", employerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Employer updated successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").isEmpty());
    }

    @Test
    void testDeleteEmployer() throws Exception {
        // Mock data
        int employerId = 1;

        // Mock service method call
        doNothing().when(employerService).deleteEmployer(employerId);

        // Perform the HTTP DELETE request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/employers/{id}", employerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Employer deleted successfully"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.object").isEmpty());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





}