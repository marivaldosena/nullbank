package ibm.blueacademy.nullbank.controllers;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.responses.AccountResponse;
import ibm.blueacademy.nullbank.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
}
