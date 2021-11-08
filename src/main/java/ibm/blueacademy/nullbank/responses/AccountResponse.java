package ibm.blueacademy.nullbank.responses;

import ibm.blueacademy.nullbank.models.Account;

import java.math.BigDecimal;

public class AccountResponse {
    private String agencyNumber;
    private String agencyName;
    private String accountHolderName;
    private Long accountHolderId;
    private String accountNumber;
    private BigDecimal currentBalance;

    public AccountResponse(Account account) {
        this.agencyNumber = account.getAgency().getAgencyNumber();
        this.agencyName = account.getAgency().getAgencyName();
        this.accountHolderName = account.getAccountHolder().getName();
        this.accountHolderId = account.getAccountHolder().getId();
        this.accountNumber = account.getAccountNumber();
        this.currentBalance = account.getCurrentBalance();
    }

    public String getAgencyNumber() {
        return this.agencyNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public Long getAccountHolderId() {
        return accountHolderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
}
