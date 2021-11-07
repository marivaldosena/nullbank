package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    Account openAccount(NewAccountRequest request);

    List<Account> listAccounts();

}
