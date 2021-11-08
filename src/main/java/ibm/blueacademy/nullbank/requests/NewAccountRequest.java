package ibm.blueacademy.nullbank.requests;

import ibm.blueacademy.nullbank.models.enums.AccountType;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class NewAccountRequest {

    @CPF
    @NotBlank
    private String cpf;

    @NotEmpty
    private String agencyNumber;

    private AccountType accountType;

    public NewAccountRequest(String cpf, String agencyNumber, AccountType accountType) {
        this.cpf = cpf;
        this.agencyNumber = agencyNumber;
        this.accountType = accountType;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAgencyNumber() {
        return agencyNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
