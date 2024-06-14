package vn.unigap.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class EmployerControllerTest {

    @Autowired
    MockMvc api;


    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetEmployerById() throws Exception {
        api.perform(get("/employers/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testGetEmployerByIdWithUser() throws Exception {
        api.perform(get("/employers/1"))
                .andExpect(status().isForbidden());
    }


    @Test
    void testGetEmployerByIdWithoutAuthen() throws Exception {
        api.perform(get("/employers/1"))
                .andExpect(status().isUnauthorized());
    }



}