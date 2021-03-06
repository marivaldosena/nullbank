package ibm.blueacademy.nullbank.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.helpers.providers.DepositArgumentsProvider;
import ibm.blueacademy.nullbank.helpers.providers.TransferArgumentsProvider;
import ibm.blueacademy.nullbank.helpers.providers.WithdrawalArgumentsProvider;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Transaction;
import ibm.blueacademy.nullbank.models.enums.AccountType;
import ibm.blueacademy.nullbank.requests.AmountRequest;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.services.AccountService;
import ibm.blueacademy.nullbank.services.CashService;
import ibm.blueacademy.nullbank.services.TransactionService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
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

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
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

    @MockBean
    private CashService cashService;

    @MockBean
    private TransactionService transactionService;

    NewAccountRequest request;

    Account expectedAccount;

    static String URL_PATH = "/api/v1/accounts";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        request = TestsHelper.mockNewAccountRequest();
        expectedAccount = TestsHelper.mockAccount();

        ReflectionTestUtils.setField(expectedAccount, "id", 1L);
        ReflectionTestUtils.setField(expectedAccount.getAccountHolder(), "id", 1L);
    }

    @DisplayName("should open an account given a valid request")
    @Test
    void openAccount() throws Exception {
        // Arrange
        NewAccountRequest request = TestsHelper.mockNewAccountRequest();
        Account expectedAccount = TestsHelper.mockAccount();

        Mockito.when(accountService.openAccount(any())).thenReturn(expectedAccount);
        ReflectionTestUtils.setField(expectedAccount, "id", 1L);

        // Act and Assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(URL_PATH)
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
        Mockito.when(accountService.listAccounts()).thenReturn(List.of(expectedAccount));

        // Act and Assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(URL_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.data.*.accountNumber").exists())
            .andExpect(jsonPath("$.data.*.agencyNumber", hasItems(expectedAccount.getAgency().getAgencyNumber())))
            .andExpect(jsonPath("$.data.*.agencyName", hasItems(expectedAccount.getAgency().getAgencyName())))
            .andExpect(jsonPath("$.data.*.accountHolderName", hasItems(expectedAccount.getAccountHolder().getName())))
            .andExpect(jsonPath("$.data.*.accountHolderId", hasItems(1)))
            .andExpect(jsonPath("$.data.*.currentBalance", hasItems(0)));

        // Verify
        Mockito.verify(accountService).listAccounts();
    }

    @DisplayName("should return account data given a valid account id")
    @Test
    void findAccount() throws Exception {
        // Arrange
        Mockito.when(accountService.getAccountByNumber(any())).thenReturn(expectedAccount);

        // Act and Assert
        mockMvc.perform(
                MockMvcRequestBuilders
                    .get(URL_PATH + "/" + expectedAccount.getAccountNumber())
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .characterEncoding("UTF-8")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accountHolderId", Matchers.is(1)))
            .andExpect(jsonPath("$.currentBalance").exists());

        // Verify
        Mockito.verify(accountService).getAccountByNumber(any());
    }

    @DisplayName("should deposit given a valid amount")
    @ArgumentsSource(DepositArgumentsProvider.class)
    @ParameterizedTest
    void deposit(double expectedValue, double... deposits) throws Exception {
        // Arrange
        BigDecimal totalToDeposit = BigDecimal.ZERO;

        for (var amountToDeposit : deposits) {
            totalToDeposit = totalToDeposit.add(BigDecimal.valueOf(amountToDeposit));
        }

        AmountRequest request = new AmountRequest(totalToDeposit);
        Mockito.when(accountService.getAccountByNumber(any())).thenReturn(expectedAccount);

        // Act and Assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(URL_PATH + "/" + expectedAccount.getAccountNumber() + "/deposit")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accountHolderId", Matchers.is(1)))
            .andExpect(jsonPath("$.currentBalance").exists());
        
        // Verify
        Mockito.verify(accountService).getAccountByNumber(any());
        Mockito.verify(cashService).deposit(any(), any());
    }

    @DisplayName("should withdraw given a valid amount")
    @ArgumentsSource(WithdrawalArgumentsProvider.class)
    @ParameterizedTest
    void withdrawal(double initialBalance, double expectedBalance, double... withdrawals) throws Exception {
        // Arrange
        BigDecimal totalToWithdraw = BigDecimal.ZERO;

        for (var amountToWithdraw : withdrawals) {
            totalToWithdraw = totalToWithdraw.add(BigDecimal.valueOf(amountToWithdraw));
        }

        AmountRequest request = new AmountRequest(totalToWithdraw);
        Mockito.when(accountService.getAccountByNumber(any())).thenReturn(expectedAccount);

        // Act and Assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(URL_PATH + "/" + expectedAccount.getAccountNumber() + "/withdraw")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accountHolderId", Matchers.is(1)))
            .andExpect(jsonPath("$.currentBalance").exists());

        // Verify
        Mockito.verify(accountService).getAccountByNumber(any());
        Mockito.verify(cashService).withdraw(any(), any());
    }

    @DisplayName("should transfer from an account to another given a valid amount")
    @ArgumentsSource(TransferArgumentsProvider.class)
    @ParameterizedTest
    void transferMoney(
        double originInitialBalance,
        double originExpectedBalance,
        double destinationExpectedBalance,
        double... transfers
    ) throws Exception {
        // Arrange
        Account destination = new Account(TestsHelper.mockClient(), AccountType.SAVINGS_ACCOUNT, TestsHelper.mockAgency());
        BigDecimal totalToTransfer = BigDecimal.ZERO;

        for (var amountToTransfer : transfers) {
            totalToTransfer = totalToTransfer.add(BigDecimal.valueOf(amountToTransfer));
        }

        AmountRequest request = new AmountRequest(totalToTransfer);
        Mockito.when(accountService.getAccountByNumber(expectedAccount.getAccountNumber())).thenReturn(expectedAccount);
        Mockito.when(accountService.getAccountByNumber(destination.getAccountNumber())).thenReturn(destination);

        // Act and Assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .post(
                    URL_PATH + "/" + expectedAccount.getAccountNumber() +
                    "/transfer-to/" + destination.getAccountNumber()
                )
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(request))
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.originAccountNumber").exists())
            .andExpect(jsonPath("$.destinationAccountNumber").exists())
            .andExpect(jsonPath("$.transferredAmount").exists())
            .andExpect(jsonPath("$.currentBalance").exists());

        // Verify
        Mockito.verify(accountService, atLeastOnce()).getAccountByNumber(any());
        Mockito.verify(cashService).transferCash(any(), any(), any());
    }

    @DisplayName("should get transaction history given a valid request")
    @Test
    void getHistory() throws Exception {
        // Arrange
        List<Transaction> expectedTransactionHistory = List.of(
            new Transaction(expectedAccount, BigDecimal.valueOf(2_000), "Deposit"),
            new Transaction(expectedAccount, BigDecimal.valueOf(2_500), "Deposit"),
            new Transaction(expectedAccount, BigDecimal.valueOf(10_000), "Deposit")
        );

        Mockito.when(transactionService.getHistory(any())).thenReturn(expectedTransactionHistory);

        // Act and Assert
        mockMvc.perform(
            MockMvcRequestBuilders
                .get(URL_PATH + "/" + expectedAccount.getAccountNumber() + "/history")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
            ).andDo(print())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.accountNumber", Matchers.is(expectedAccount.getAccountNumber())))
            .andExpect(jsonPath("$.history.*.amount").exists())
            .andExpect(jsonPath("$.history.*.description", hasItem("Deposit")));

        // Verify
        Mockito.verify(transactionService).getHistory(any());
    }
}
