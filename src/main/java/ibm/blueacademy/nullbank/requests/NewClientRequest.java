package ibm.blueacademy.nullbank.requests;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class NewClientRequest {
    @NotBlank
    private String name;

    @CPF
    private String cpf;

    @NotBlank
    @Size(min = 3)
    private String address;

    @DecimalMin(value = "500")
    private BigDecimal salary;

    public NewClientRequest(String name, String cpf, String address, BigDecimal salary) {
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAddress() {
        return address;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
