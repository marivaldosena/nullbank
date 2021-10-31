package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.requests.NewClientRequest;

import java.util.List;

public interface ClientService {

    Client registerNewClient(NewClientRequest request);

    List<Client> listAllClients();

}
