package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultClientService implements ClientService {
    private ClientRepository clientRepository;

    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client registerNewClient(String name, String cpf, String address, String salary) {
        Client newClient = new Client(name, cpf, address, new BigDecimal(salary));
        clientRepository.save(newClient);

        return newClient;
    }
}
