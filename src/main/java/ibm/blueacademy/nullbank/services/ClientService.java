package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Client;

public interface ClientService {

    Client registerNewClient(String name, String cpf, String address, String salary);

}
