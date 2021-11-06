package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.repositories.AgencyRepository;
import ibm.blueacademy.nullbank.services.AgencyService;
import org.springframework.stereotype.Service;

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
}
