package ibm.blueacademy.nullbank.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateAgencyRequest {

    @NotBlank
    @Size(min = 3)
    private String agencyName;

    @NotBlank
    private String agencyNumber;

    public CreateAgencyRequest(String agencyName, String agencyNumber) {
        this.agencyName = agencyName;
        this.agencyNumber = agencyNumber;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getAgencyNumber() {
        return agencyNumber;
    }
}
