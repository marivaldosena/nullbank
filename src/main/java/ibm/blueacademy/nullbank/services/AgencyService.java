package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.requests.CreateAgencyRequest;

import java.util.List;

public interface AgencyService {

    Agency findAgencyByNumber(String agencyNumber);

    Agency createAgency(CreateAgencyRequest request);

    List<Agency> listAgencies();

}
