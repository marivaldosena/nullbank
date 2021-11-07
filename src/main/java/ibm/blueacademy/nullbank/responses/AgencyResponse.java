package ibm.blueacademy.nullbank.responses;

import ibm.blueacademy.nullbank.models.Agency;

public class AgencyResponse {
    private String agencyName;
    private String agencyNumber;

    public AgencyResponse(Agency agency) {
        this.agencyName = agency.getAgencyName();
        this.agencyNumber = agency.getAgencyNumber();
    }

    public String getAgencyName() {
        return agencyName;
    }

    public String getAgencyNumber() {
        return agencyNumber;
    }
}
