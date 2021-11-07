package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Transaction;
import ibm.blueacademy.nullbank.repositories.TransactionRepository;
import ibm.blueacademy.nullbank.services.TransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryService implements TransactionService {

    private TransactionRepository transactionRepository;

    public TransactionHistoryService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void register(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getHistory(Account account) {
        return transactionRepository.findAllByAccountNumber(account.getAccountNumber());
    }
}
