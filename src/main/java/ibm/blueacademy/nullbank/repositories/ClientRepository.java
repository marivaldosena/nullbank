package ibm.blueacademy.nullbank.repositories;

import ibm.blueacademy.nullbank.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
