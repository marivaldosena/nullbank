package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class DefaultClientService implements ClientService {
    private ClientRepository clientRepository;

    public DefaultClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client registerNewClient(NewClientRequest request) {
        Client newClient = new Client(
            request.getName(),
            request.getCpf(),
            request.getAddress(),
            request.getSalary()
        );
        clientRepository.save(newClient);

        return newClient;
    }
}
