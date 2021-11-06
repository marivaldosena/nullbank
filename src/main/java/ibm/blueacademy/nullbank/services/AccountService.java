package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;

public interface AccountService {
    Account openAccount(NewAccountRequest request);
}
