package ibm.blueacademy.nullbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ibm.blueacademy.nullbank.models.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountListResponse {
    @JsonProperty("data")
    private List<AccountResponse> listOfAccounts;

    public AccountListResponse(List<Account> listOfAccounts) {
        this.listOfAccounts = listOfAccounts.stream().map(AccountResponse::new).collect(Collectors.toList());
    }

    public List<AccountResponse> getListOfAccounts() {
        return listOfAccounts;
    }
}
