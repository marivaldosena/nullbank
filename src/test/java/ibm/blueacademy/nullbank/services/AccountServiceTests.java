package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.AccountType;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.AccountRepository;
import ibm.blueacademy.nullbank.repositories.AgencyRepository;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class AccountServiceTests {
    private DefaultAccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private AgencyRepository agencyRepository;

    @MockBean
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        accountService = new DefaultAccountService(accountRepository, clientRepository, agencyRepository);
    }

    @DisplayName("should open a client account given a valid request")
    @Test
    void openAccount() {
        // Arrange
        NewAccountRequest
        Client expectedClient = TestsHelper.mockClient();
        Account expectedAccount = TestsHelper.mockAccount();
        Mockito.when(clientService.registerNewClient(any())).thenReturn(expectedClient);
        Mockito.when(accountRepository.save(any())).thenReturn(expectedAccount);

        // Act
        Client client = clientService.registerNewClient(request);
        Account account = accountService.openAccount(
            client,
            AccountType.CURRENT_ACCOUNT,
            new Agency("Agência Central", "0001")
        );

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
}
