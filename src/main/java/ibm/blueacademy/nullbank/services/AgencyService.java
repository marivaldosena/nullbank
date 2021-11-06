package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Agency;

public interface AgencyService {

    Agency findAgencyByNumber(String agencyNumber);

}
