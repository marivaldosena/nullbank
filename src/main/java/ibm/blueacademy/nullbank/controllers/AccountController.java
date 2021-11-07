package ibm.blueacademy.nullbank.controllers;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.responses.AccountListResponse;
import ibm.blueacademy.nullbank.responses.AccountResponse;
import ibm.blueacademy.nullbank.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> openAccount(
        @Valid @RequestBody NewAccountRequest request,
        UriComponentsBuilder builder
    ) {
        Account newAccount = accountService.openAccount(request);
        AccountResponse response = new AccountResponse(newAccount);

        URI uri = builder.path("/api/v1/accounts/{id}").buildAndExpand(newAccount.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<AccountListResponse> listAccounts() {
        List<Account> listOfAccounts = accountService.listAccounts();
        AccountListResponse response = new AccountListResponse(listOfAccounts);

        return ResponseEntity.ok(response);
    }
}
