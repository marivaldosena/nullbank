package ibm.blueacademy.nullbank.repositories;

import ibm.blueacademy.nullbank.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByAccountId(Long accountId);

    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber LIKE :accountNumber")
    List<Transaction> findAllByAccountNumber(String accountNumber);

}
