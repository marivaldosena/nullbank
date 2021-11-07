package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Transaction;

import java.util.List;

public interface TransactionService {

    void register(Transaction transaction);

    List<Transaction> getHistory(Account account);

}
