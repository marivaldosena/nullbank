package ibm.blueacademy.nullbank.responses;

import ibm.blueacademy.nullbank.models.Account;

public class AccountResponse {
    private String agencyNumber;
    private String agencyName;
    private String accountHolderName;
    private String accountHolderId;

    public AccountResponse(Account account) {
        this.agencyNumber = account.getAgency().getAgencyNumber();
        this.agencyName = account.getAgency().getAgencyName();
        this.accountHolderName = account.getAccountHolder().getName();
        this.accountHolderId = account.getAccountHolder().getId().toString();
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

    public String getAccountHolderId() {
        return accountHolderId;
    }
}
