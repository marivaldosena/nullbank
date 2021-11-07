package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.AccountRepository;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.services.impl.DefaultAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class AccountServiceTests {
    private DefaultAccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AgencyService agencyService;

    @MockBean
    private ClientService clientService;

    NewAccountRequest newAccount;
    Client expectedClient;
    Account expectedAccount;
    Agency expectedAgency;

    @BeforeEach
    void setUp() {
        accountService = new DefaultAccountService(accountRepository, clientService, agencyService);

        newAccount = TestsHelper.mockNewAccountRequest();
        expectedClient = TestsHelper.mockClient();
        expectedAccount = TestsHelper.mockAccount();
        expectedAgency = TestsHelper.mockAgency();

        ReflectionTestUtils.setField(expectedClient, "id", 1L);
        ReflectionTestUtils.setField(expectedAccount, "id", 1L);
        ReflectionTestUtils.setField(expectedAgency, "id", 1L);
    }

    @DisplayName("should open a client account given a valid request")
    @Test
    void openAccount() {
        // Arrange
        Mockito.when(clientService.registerNewClient(any())).thenReturn(expectedClient);
        Mockito.when(agencyService.findAgencyByNumber(any())).thenReturn(expectedAgency);
        Mockito.when(accountRepository.save(any())).thenReturn(expectedAccount);

        // Act
        Account account = accountService.openAccount(newAccount);

        // Assert
        assertEquals(expectedAccount.getAccountHolder().getName(), account.getAccountHolder().getName());
        assertEquals(expectedAccount.getAccountHolder().getAddress(), account.getAccountHolder().getAddress());
        assertEquals(expectedAccount.getAccountHolder().getSalary(), account.getAccountHolder().getSalary());
        assertEquals(expectedAccount.getAccountHolder().getId(), account.getAccountHolder().getId());
        assertEquals(expectedAccount.getAccountType(), account.getAccountType());
        assertEquals(expectedAccount.getAccountNumber(), account.getAccountNumber());
        assertEquals(expectedAccount.getAgency().getAgencyName(), account.getAgency().getAgencyName());
        assertEquals(expectedAccount.getAgency().getAgencyNumber(), account.getAgency().getAgencyNumber());

        // Verify
        Mockito.verify(accountRepository).save(any());
    }

    @DisplayName("should get an account given a valid id")
    @Test
    void getAccount() {
        // Arrange
        Mockito.when(accountRepository.findById(any())).thenReturn(Optional.ofNullable(expectedAccount));

        // Act
        Account account = accountService.getAccountById(1L);

        // Assert
        assertEquals(expectedAccount.getAccountType(), account.getAccountType());
        assertEquals(expectedAccount.getAccountNumber(), account.getAccountNumber());

        // Verify
        Mockito.verify(accountRepository).findById(any());
    }
}
