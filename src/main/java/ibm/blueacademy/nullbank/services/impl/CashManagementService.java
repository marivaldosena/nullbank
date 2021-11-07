package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Transaction;
import ibm.blueacademy.nullbank.services.CashService;
import ibm.blueacademy.nullbank.services.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CashManagementService implements CashService {
    private TransactionService transactionService;

    public CashManagementService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void deposit(Account account, BigDecimal amount) {
        checkIfValueIsValid(amount);
        account.deposit(amount);
        Transaction transaction = new Transaction(
            account,
            amount,
            "Deposit to account " + account.getAccountNumber()
        );
        transactionService.register(transaction);
    }

    @Override
    public void withdraw(Account account, BigDecimal amount) {
        checkIfValueIsValid(amount);
        account.withdraw(amount);
        Transaction transaction = new Transaction(
            account,
            amount,
            "Withdrawal from account " + account.getAccountNumber()
        );
        transactionService.register(transaction);
    }

    @Override
    public void transferCash(Account origin, Account destination, BigDecimal amount) {
        checkIfValueIsValid(amount);
        origin.withdraw(amount);
        destination.deposit(amount);
        Transaction transaction = new Transaction(
            origin,
            amount,
            "Transfer to account " + destination.getAccountNumber()
        );
        transactionService.register(transaction);
    }

    private void checkIfValueIsValid(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount should be positive");
        }
    }
}
