package ibm.blueacademy.nullbank.services.impl;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import ibm.blueacademy.nullbank.services.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Client> listAllClients() {
        return clientRepository.findAll();
    }

    public Client findClientByCpf(String cpf) {
        Optional<Client> client = clientRepository.findByCpf(cpf);

        if (!client.isPresent()) {
            throw new RuntimeException("Client not found");
        }

        return client.get();
    }
}
