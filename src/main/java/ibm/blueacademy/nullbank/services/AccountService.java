package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.AccountType;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;

public interface AccountService {
    Account openAccount(Client client, AccountType accountType, Agency agency);
}
