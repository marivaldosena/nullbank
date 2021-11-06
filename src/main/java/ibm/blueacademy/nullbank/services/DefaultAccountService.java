package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.AccountType;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class DefaultAccountService implements AccountService {
    private AccountRepository accountRepository;

    public DefaultAccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account openAccount(Client accountHolder, AccountType accountType, Agency agency) {
        Account newAccount = new Account(accountHolder, accountType, agency);
        newAccount = accountRepository.save(newAccount);
        return newAccount;
    }
}
