package ibm.blueacademy.nullbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ibm.blueacademy.nullbank.models.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionHistoryResponse {
    private String accountNumber;

    @JsonProperty("history")
    private List<TransactionResponse> history;

    public TransactionHistoryResponse(List<Transaction> history) {
        this.accountNumber = history.get(0).getAccount().getAccountNumber();
        this.history = history.stream().map(TransactionResponse::new).collect(Collectors.toList());
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public List<TransactionResponse> getHistory() {
        return history;
    }
}
