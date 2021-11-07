package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.repositories.AgencyRepository;
import ibm.blueacademy.nullbank.requests.CreateAgencyRequest;
import ibm.blueacademy.nullbank.services.AgencyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DefaultAgencyService implements AgencyService {
    private AgencyRepository agencyRepository;

    public DefaultAgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    @Override
    public Agency findAgencyByNumber(String agencyNumber) {
        Optional<Agency> agency = agencyRepository.findByAgencyNumber(agencyNumber);

        if (!agency.isPresent()) {
            throw new RuntimeException("Agency not found");
        }

        return agency.get();
    }

    @Override
    public Agency createAgency(CreateAgencyRequest request) {
        Agency agency = new Agency(request.getAgencyName(), request.getAgencyNumber());
        agency = agencyRepository.save(agency);

        return agency;
    }

    @Override
    public List<Agency> listAgencies() {
        return agencyRepository.findAll();
    }
}
