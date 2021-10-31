package ibm.blueacademy.nullbank.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String agencyName;
    private String agencyNumber;

    /**
     * @deprecated Hibernate only.
     */
    public Agency() {
    }

    public Agency(String agencyName, String agencyNumber) {
        this.agencyName = agencyName;
        this.agencyNumber = agencyNumber;
    }

    public Long getId() {
        return id;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getAgencyNumber() {
        return agencyNumber;
    }
}
