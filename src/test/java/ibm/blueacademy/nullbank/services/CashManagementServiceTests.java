package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.helpers.providers.DepositArgumentsProvider;
import ibm.blueacademy.nullbank.helpers.providers.TransferArgumentsProvider;
import ibm.blueacademy.nullbank.helpers.providers.WithdrawalArgumentsProvider;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.enums.AccountType;
import ibm.blueacademy.nullbank.services.impl.CashManagementService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
public class CashManagementServiceTests {

    private CashManagementService cashManagementService;

    @MockBean
    private TransactionService transactionService;

    private Account account;

    @BeforeEach
    void setUp() {
        cashManagementService = new CashManagementService(transactionService);
        account = TestsHelper.mockAccount();
    }

    @DisplayName("should deposit to an account given a valid cash amount")
    @ArgumentsSource(DepositArgumentsProvider.class)
    @ParameterizedTest
    void deposit(double expectedValue, double... deposits) {
        // Arrange
        BigDecimal initialBalance = account.getCurrentBalance();

        Mockito.doNothing().when(transactionService).register(any());

        // Act
        for (var amountToDeposit : deposits) {
            cashManagementService.deposit(account, BigDecimal.valueOf(amountToDeposit));
        }
        BigDecimal balanceAfterDeposits = account.getCurrentBalance();

        // Assert
        assertEquals(BigDecimal.ZERO, initialBalance);
        assertEquals(BigDecimal.valueOf(expectedValue), balanceAfterDeposits);

        // Verify
        Mockito.verify(transactionService, times(deposits.length)).register(any());
    }

    @DisplayName("should withdraw given a valid cash amount")
    @ArgumentsSource(WithdrawalArgumentsProvider.class)
    @ParameterizedTest
    void withdraw(double initialBalance, double expectedBalance, double... withdrawals) {
        // Arrange
        cashManagementService.deposit(account, BigDecimal.valueOf(initialBalance));

        Mockito.doNothing().when(transactionService).register(any());

        // Act
        for (var amountToWithdraw : withdrawals) {
            cashManagementService.withdraw(account, BigDecimal.valueOf(amountToWithdraw));
        }

        BigDecimal balanceAfterWithdrawals = account.getCurrentBalance();

        // Assert
        assertEquals(BigDecimal.valueOf(expectedBalance), balanceAfterWithdrawals);

        // Verify
        Mockito.verify(transactionService, times(withdrawals.length + 1)).register(any());
    }

    @DisplayName("should transfer from an account to another given a valid cash amount")
    @ArgumentsSource(TransferArgumentsProvider.class)
    @ParameterizedTest
    void transfer(
        double originInitialBalance,
        double originExpectedBalance,
        double destinationExpectedBalance,
        double... transfers
    ) {
        // Arrange
        cashManagementService.deposit(account, BigDecimal.valueOf(originInitialBalance));

        Mockito.doNothing().when(transactionService).register(any());

        Account destination = new Account(TestsHelper.mockClient(), AccountType.SAVINGS_ACCOUNT, TestsHelper.mockAgency());
        BigDecimal destinationInitialBalance = destination.getCurrentBalance();

        // Act
        for (var moneyToTransfer : transfers) {
            cashManagementService.transferCash(account, destination, BigDecimal.valueOf(moneyToTransfer));
        }
        BigDecimal destinationBalanceAfterTransfers = destination.getCurrentBalance();
        BigDecimal originBalanceAfterTransfers = account.getCurrentBalance();

        // Assert
        assertEquals(BigDecimal.ZERO, destinationInitialBalance);
        assertEquals(BigDecimal.valueOf(destinationExpectedBalance), destinationBalanceAfterTransfers);
        assertEquals(BigDecimal.valueOf(originExpectedBalance), originBalanceAfterTransfers);

        // Verify
        Mockito.verify(transactionService, times(transfers.length + 1)).register(any());
    }
}
