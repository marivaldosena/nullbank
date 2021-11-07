package ibm.blueacademy.nullbank.responses;

import ibm.blueacademy.nullbank.models.Transaction;

import java.math.BigDecimal;

public class TransactionResponse {
    private BigDecimal amount;
    private String description;

    public TransactionResponse(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
