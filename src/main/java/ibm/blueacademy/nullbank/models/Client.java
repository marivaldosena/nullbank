package ibm.blueacademy.nullbank.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpf;
    private String address;
    private BigDecimal salary;

    /**
     * @deprecated Hibernate only.
     */
    public Client() {
    }

    public Client(String name, String cpf, String address, BigDecimal salary) {
        this.name = name;
        this.cpf = cpf;
        this.address = address;
        this.salary = salary;
    }

    public Long getId() {
        return id;
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
