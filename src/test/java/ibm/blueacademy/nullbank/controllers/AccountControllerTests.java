package ibm.blueacademy.nullbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.services.AccountService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @DisplayName("should open an account given a valid request")
    @Test
    void openAccount() throws Exception {
        // Arrange
        NewAccountRequest request = TestsHelper.mockNewAccountRequest();
        Account expectedAccount = TestsHelper.mockAccount();

        Mockito.when(accountService.openAccount(any())).thenReturn(expectedAccount);
        ReflectionTestUtils.setField(expectedAccount, "id", 1L);

        // Act
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", Matchers.containsString("/api/v1/accounts/1")))
            .andExpect(jsonPath("$.accountNumber").exists())
            .andExpect(jsonPath("$.agencyNumber", Matchers.is(expectedAccount.getAgency().getAgencyNumber())))
            .andExpect(jsonPath("$.agencyName", Matchers.is(expectedAccount.getAgency().getAgencyName())))
            .andExpect(jsonPath("$.accountHolderName", Matchers.is(expectedAccount.getAccountHolder().getName())))
            .andExpect(jsonPath("$.accountHolderId", Matchers.is(expectedAccount.getAccountHolder().getId())));

        // Verify
        Mockito.verify(accountService).openAccount(any());
    }

    @DisplayName("should list all accounts given a valid request")
    @Test
    void listAccounts() throws Exception {
        // Arrange
        NewAccountRequest request = TestsHelper.mockNewAccountRequest();
        Account expectedAccount = TestsHelper.mockAccount();

        Mockito.when(accountService.listAccounts()).thenReturn(List.of(expectedAccount));
        ReflectionTestUtils.setField(expectedAccount, "id", 1L);

        // Act
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/api/v1/accounts")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.data.*.accountNumber").exists())
            .andExpect(jsonPath("$.data.*.agencyNumber", hasItems(expectedAccount.getAgency().getAgencyNumber())))
            .andExpect(jsonPath("$.data.*.agencyName", hasItems(expectedAccount.getAgency().getAgencyName())))
            .andExpect(jsonPath("$.data.*.accountHolderName", hasItems(expectedAccount.getAccountHolder().getName())))
            .andExpect(jsonPath("$.data.*.accountHolderId", hasItems(expectedAccount.getAccountHolder().getId())));

        // Verify
        Mockito.verify(accountService).listAccounts();
    }
}
