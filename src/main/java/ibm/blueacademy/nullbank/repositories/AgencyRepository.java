package ibm.blueacademy.nullbank.repositories;

import ibm.blueacademy.nullbank.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgencyRepository extends JpaRepository<Agency, Long> {

    Optional<Agency> findByAgencyNumber(String agencyNumber);

}
