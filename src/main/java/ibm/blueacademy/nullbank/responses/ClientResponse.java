package ibm.blueacademy.nullbank.responses;

import ibm.blueacademy.nullbank.models.Client;

public class ClientResponse {
    private Long id;
    private String name;

    public ClientResponse(Client client) {
        id = client.getId();
        name = client.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
