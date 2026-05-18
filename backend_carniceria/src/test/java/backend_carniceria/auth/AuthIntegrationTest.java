package backend_carniceria.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.jayway.jsonpath.JsonPath;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ActiveProfiles("test")
class AuthIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void loginReturnsJwtAndCurrentUserCanBeFetched() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  \"email\": \"admin@test.local\",
                                  \"password\": \"Admin12345!\"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isString())
                .andExpect(jsonPath("$.user.email").value("admin@test.local"))
                .andExpect(jsonPath("$.user.roles[0]").value("ROLE_ADMIN"))
                .andReturn();

        String token = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(get("/api/users/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("admin@test.local"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_ADMIN"));
    }

    @Test
    void adminCanCreateAndListMultiRoleUser() throws Exception {
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  \"email\": \"admin@test.local\",
                                  \"password\": \"Admin12345!\"
                                }
                                """))
                .andExpect(status().isOk())
                .andReturn();

        String token = JsonPath.read(loginResult.getResponse().getContentAsString(), "$.token");

        mockMvc.perform(post("/api/users")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  \"email\": \"empleado.cliente@test.local\",
                                  \"firstName\": \"Ana\",
                                  \"lastName\": \"Perez\",
                                  \"password\": \"Password123!\",
                                  \"roles\": [\"ROLE_EMPLEADO\", \"ROLE_CLIENTE\"]
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("empleado.cliente@test.local"))
                .andExpect(jsonPath("$.roles.length()").value(2))
                .andExpect(jsonPath("$.password").doesNotExist());

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email").value("admin@test.local"))
                .andExpect(jsonPath("$[1].email").value("empleado.cliente@test.local"));
    }
}
