package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.AccountRepository;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.services.AccountService;
import ibm.blueacademy.nullbank.services.AgencyService;
import ibm.blueacademy.nullbank.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAccountService implements AccountService {
    private AccountRepository accountRepository;
    private ClientService clientService;
    private AgencyService agencyService;

    public DefaultAccountService(
        AccountRepository accountRepository,
        ClientService clientService,
        AgencyService agencyService
    ) {
        this.accountRepository = accountRepository;
        this.clientService = clientService;
        this.agencyService = agencyService;
    }

    @Override
    public Account openAccount(NewAccountRequest request) {
        Client accountHolder = clientService.findClientByCpf(request.getCpf());
        Agency agency = agencyService.findAgencyByNumber(request.getAgencyNumber());

        Account newAccount = new Account(accountHolder, request.getAccountType(), agency);
        newAccount = accountRepository.save(newAccount);

        return newAccount;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }
}
