package ibm.blueacademy.nullbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import ibm.blueacademy.nullbank.services.ClientService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldRegisterNewClientGivenAValidRequest() throws Exception {
        // Arrange
        NewClientRequest request = TestsHelper.mockNewClientRequest();
        Client expectedClient = TestsHelper.mockClient();
        ReflectionTestUtils.setField(expectedClient, "id", 1L);

        Mockito.when(clientService.registerNewClient(any())).thenReturn(expectedClient);

        // Act
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/v1/clients")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", Matchers.containsString("/api/v1/clients/1")))
            .andExpect(jsonPath("$.name", Matchers.is("Nome do cliente")));

        // Verify
        Mockito.verify(clientService).registerNewClient(any());
    }
}