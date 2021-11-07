package ibm.blueacademy.nullbank.controllers;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Transaction;
import ibm.blueacademy.nullbank.requests.AmountRequest;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
import ibm.blueacademy.nullbank.responses.AccountListResponse;
import ibm.blueacademy.nullbank.responses.AccountResponse;
import ibm.blueacademy.nullbank.responses.TransactionHistoryResponse;
import ibm.blueacademy.nullbank.responses.TransferResponse;
import ibm.blueacademy.nullbank.services.AccountService;
import ibm.blueacademy.nullbank.services.CashService;
import ibm.blueacademy.nullbank.services.TransactionService;
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
    private CashService cashService;
    private TransactionService transactionService;

    public AccountController(
        AccountService accountService,
        CashService cashService,
        TransactionService transactionService
    ) {
        this.accountService = accountService;
        this.cashService = cashService;
        this.transactionService = transactionService;
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

    @GetMapping("/{accountNumber}")
    public ResponseEntity<AccountResponse> findAccount(
        @PathVariable String accountNumber
    ) {
        Account account = accountService.getAccountByNumber(accountNumber);
        AccountResponse response = new AccountResponse(account);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<AccountResponse> deposit(
        @PathVariable String accountNumber,
        @Valid @RequestBody AmountRequest request
    ) {
        Account account = accountService.getAccountByNumber(accountNumber);
        cashService.deposit(account, request.getAmount());

        AccountResponse response = new AccountResponse(account);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(
        @PathVariable String accountNumber,
        @Valid @RequestBody AmountRequest request
    ) {
        Account account = accountService.getAccountByNumber(accountNumber);
        cashService.withdraw(account, request.getAmount());

        AccountResponse response = new AccountResponse(account);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{originAccountNumber}/transfer-to/{destinationAccountNumber}")
    public ResponseEntity<TransferResponse> transferTo(
        @PathVariable String originAccountNumber,
        @PathVariable String destinationAccountNumber,
        @Valid @RequestBody AmountRequest request
    ) {
        Account origin = accountService.getAccountByNumber(originAccountNumber);
        Account destination = accountService.getAccountByNumber(destinationAccountNumber);
        cashService.transferCash(origin, destination, request.getAmount());

        TransferResponse response = new TransferResponse(origin, destination, request.getAmount());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}/history")
    public ResponseEntity<TransactionHistoryResponse> getHistory(
        @PathVariable String accountNumber
    ) {
        Account account = accountService.getAccountByNumber(accountNumber);
        List<Transaction> history = transactionService.getHistory(account);
        TransactionHistoryResponse response  = new TransactionHistoryResponse(history);

        return ResponseEntity.ok(response);
    }
}
