package ibm.blueacademy.nullbank.models;

import javax.persistence.*;
import java.util.Random;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client accountHolder;

    private AccountType accountType;

    private String accountNumber;

    @ManyToOne
    private Agency agency;

    public Account(Client accountHolder, AccountType accountType, Agency agency) {
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.accountNumber = formatAccountNumber(generateRandomNumber(), 10);
        this.agency = agency;
    }

    public Long getId() {
        return id;
    }

    public Client getAccountHolder() {
        return accountHolder;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Agency getAgency() {
        return agency;
    }

    private String formatAccountNumber(Integer inputString, int length) {
        return String.format("%1$" + length + "s", inputString).replace(' ', '0');
    }

    /* If this logic is used for many classes and we might change the maximum number,
        it's interesting to extract this piece of code to another class.
     */
    private Integer generateRandomNumber() {
        return new Random().nextInt(2_000_000);
    }
}