package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.helpers.providers.DepositArgumentsProvider;
import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.helpers.providers.TransferArgumentsProvider;
import ibm.blueacademy.nullbank.helpers.providers.WithdrawalArgumentsProvider;
import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.AccountType;
import ibm.blueacademy.nullbank.services.impl.CashManagementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class CashManagementServiceTests {

    private CashManagementService cashManagementService;

    private Account account;

    @BeforeEach
    void setUp() {
        cashManagementService = new CashManagementService();
        account = TestsHelper.mockAccount();
    }

    @DisplayName("should deposit to an account given a valid cash amount")
    @ArgumentsSource(DepositArgumentsProvider.class)
    @ParameterizedTest
    void deposit(double expectedValue, double... deposits) {
        // Arrange
        BigDecimal initialBalance = account.getCurrentBalance();

        // Act
        for (var amountToDeposit : deposits) {
            cashManagementService.deposit(account, BigDecimal.valueOf(amountToDeposit));
        }
        BigDecimal balanceAfterDeposits = account.getCurrentBalance();

        // Assert
        assertEquals(BigDecimal.ZERO, initialBalance);
        assertEquals(BigDecimal.valueOf(expectedValue), balanceAfterDeposits);
    }

    @DisplayName("should withdraw given a valid cash amount")
    @ArgumentsSource(WithdrawalArgumentsProvider.class)
    @ParameterizedTest
    void withdraw(double initialBalance, double expectedBalance, double... withdrawals) {
        // Arrange
        cashManagementService.deposit(account, BigDecimal.valueOf(initialBalance));

        // Act
        for (var amountToWithdraw : withdrawals) {
            cashManagementService.withdraw(account, BigDecimal.valueOf(amountToWithdraw));
        }

        BigDecimal balanceAfterWithdrawals = account.getCurrentBalance();

        // Assert
        assertEquals(BigDecimal.valueOf(expectedBalance), balanceAfterWithdrawals);
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
    }
}
