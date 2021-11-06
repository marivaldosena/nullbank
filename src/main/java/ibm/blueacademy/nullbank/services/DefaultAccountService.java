package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.AccountRepository;
import ibm.blueacademy.nullbank.repositories.AgencyRepository;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAccountService implements AccountService {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private AgencyRepository agencyRepository;


    public DefaultAccountService(
        AccountRepository accountRepository,
        ClientRepository clientRepository,
        AgencyRepository agencyRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.agencyRepository = agencyRepository;
    }

    @Override
    public Account openAccount(NewAccountRequest request) {
        Optional<Client> accountHolder = clientRepository.findByCpf(request.getCpf());

        if (!accountHolder.isPresent()) {
            throw new RuntimeException("Client not found");
        }

        Optional<Agency> agency = agencyRepository.findByAgencyNumber(request.getAgencyNumber());

        if (!agency.isPresent()) {
            throw new java.lang.RuntimeException("Agency not found");
        }

        Account newAccount = new Account(accountHolder.get(), request.getAccountType(), agency.get());

        newAccount = accountRepository.save(newAccount);
        return newAccount;
    }
}
