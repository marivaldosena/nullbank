package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;

import java.math.BigDecimal;

public interface CashService {

    void deposit(Account account, BigDecimal amount);

    void withdraw(Account account, BigDecimal amount);

    void transferCash(Account origin, Account destination, BigDecimal amount);

}
