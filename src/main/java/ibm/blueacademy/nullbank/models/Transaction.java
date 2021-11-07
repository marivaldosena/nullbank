package ibm.blueacademy.nullbank.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private BigDecimal amount;

    private String description;

    /**
     * @deprecated Hibernate only
     */
    public Transaction() {
    }

    public Transaction(Account account, BigDecimal amount, String description) {
        this.account = account;
        this.amount = amount;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }
}
