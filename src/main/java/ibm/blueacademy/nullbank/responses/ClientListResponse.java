package ibm.blueacademy.nullbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import ibm.blueacademy.nullbank.models.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientListResponse {
    @JsonProperty("data")
    private List<ClientResponse> listOfClients;

    public ClientListResponse(List<Client> listOfClients) {
        this.listOfClients = listOfClients.stream().map(ClientResponse::new).collect(Collectors.toList());
    }

    public List<ClientResponse> getListOfClients() {
        return listOfClients;
    }
}
