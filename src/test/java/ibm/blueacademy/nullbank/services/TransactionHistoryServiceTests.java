package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Transaction;
import ibm.blueacademy.nullbank.models.enums.AccountType;
import ibm.blueacademy.nullbank.repositories.TransactionRepository;
import ibm.blueacademy.nullbank.services.impl.TransactionHistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class TransactionHistoryServiceTests {

    private TransactionHistoryService transactionHistoryService;

    @MockBean
    private TransactionRepository transactionRepository;

    @Captor
    private ArgumentCaptor<Transaction> transactionCaptor;

    private Account account;

    @BeforeEach
    void setUp() {
        transactionHistoryService = new TransactionHistoryService(transactionRepository);
        account = TestsHelper.mockAccount();
    }

    @DisplayName("should register transaction history given a valid operation")
    @Test
    void registerTransaction() {
        // Arrange
        Account destination = new Account(TestsHelper.mockClient(), AccountType.SAVINGS_ACCOUNT, TestsHelper.mockAgency());
        BigDecimal amountToTransfer = BigDecimal.valueOf(2_000);
        Transaction expectedTransaction = new Transaction(account, amountToTransfer, "Trasfer to " + destination.getAccountNumber());

        Mockito.when(transactionRepository.save(any())).thenReturn(expectedTransaction);

        // Act
        transactionHistoryService.register(new Transaction(account, amountToTransfer, "Trasfer to account " + destination.getAccountNumber()));
        Mockito.verify(transactionRepository).save(transactionCaptor.capture());

        Transaction capturedTransaction = transactionCaptor.getValue();

        // Assert
        assertEquals(expectedTransaction.getAccount().getAccountNumber(), capturedTransaction.getAccount().getAccountNumber());
        assertEquals(amountToTransfer, capturedTransaction.getAmount());
        assertEquals("Trasfer to account " + destination.getAccountNumber(), capturedTransaction.getDescription());

        // Verify
        Mockito.verify(transactionRepository).save(any());
    }

    @DisplayName("should get transaction history given a valid account")
    @Test
    void getHistory() {
        // Arrange
        List<Transaction> expectedTransactionHistory = List.of(
            new Transaction(account, BigDecimal.valueOf(2_000), "Deposit"),
            new Transaction(account, BigDecimal.valueOf(2_500), "Deposit"),
            new Transaction(account, BigDecimal.valueOf(10_000), "Deposit")
        );

        Mockito.when(transactionRepository.findAllByAccountNumber(any())).thenReturn(expectedTransactionHistory);

        // Act
        List<Transaction> transactionHistory = transactionHistoryService.getHistory(account);

        // Assert
        assertEquals(expectedTransactionHistory.size(), transactionHistory.size());
        assertThat(
            expectedTransactionHistory.stream().map(it -> it.getAmount()).collect(Collectors.toList()),
            hasItems(BigDecimal.valueOf(2_000), BigDecimal.valueOf(2_500), BigDecimal.valueOf(10_000))
        );

        // Verify
        Mockito.verify(transactionRepository).findAllByAccountNumber(any());
    }
}
