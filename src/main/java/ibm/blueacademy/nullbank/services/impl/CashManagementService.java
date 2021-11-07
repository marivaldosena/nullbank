package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.services.CashService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CashManagementService implements CashService {
    @Override
    public void deposit(Account account, BigDecimal amount) {
        checkIfValueIsValid(amount);
        account.deposit(amount);
    }

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        checkIfValueIsValid(amount);
        account.withdraw(amount);
    }

    @Override
    public void transferCash(Account origin, Account destination, BigDecimal amount) {
        checkIfValueIsValid(amount);
        origin.withdraw(amount);
        destination.deposit(amount);
    }

    private void checkIfValueIsValid(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount should be positive");
        }
    }
}
