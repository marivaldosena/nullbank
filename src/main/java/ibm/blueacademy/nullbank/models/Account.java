package ibm.blueacademy.nullbank.models;

import javax.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client accountHolder;
    private String accountNumber;
    private AccountType accountType;

    @ManyToOne
    private Agency agency;

    public Account() {
    }

    public Account(String accountNumber, AccountType accountType, Agency agency) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.agency = agency;
    }

    public Client getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(Client accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Agency getAgency() {
        return agency;
    }
}