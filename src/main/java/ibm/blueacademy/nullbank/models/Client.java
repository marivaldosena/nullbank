package ibm.blueacademy.nullbank.models;

import java.math.BigDecimal;

public class Client {
    private String name;
    private String cpf;
    private String address;
    private BigDecimal salary;

    public Client(String name, String cpf, String address, BigDecimal salary) {
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
