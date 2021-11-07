package ibm.blueacademy.nullbank.responses;

import ibm.blueacademy.nullbank.models.Account;

import java.math.BigDecimal;

public class TransferResponse {
    private String originAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal transferredAmount;
    private BigDecimal currentBalance;

    public TransferResponse(Account origin, Account destination, BigDecimal amount) {
        this.originAccountNumber = origin.getAccountNumber();
        this.destinationAccountNumber = destination.getAccountNumber();
        this.transferredAmount = amount;
        this.currentBalance = origin.getCurrentBalance();
    }

    public String getOriginAccountNumber() {
        return originAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public BigDecimal getTransferredAmount() {
        return transferredAmount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
}
