package ibm.blueacademy.nullbank.controllers;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import ibm.blueacademy.nullbank.responses.ClientListResponse;
import ibm.blueacademy.nullbank.responses.ClientResponse;
import ibm.blueacademy.nullbank.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientResponse> registerNewClient(
        @RequestBody @Valid NewClientRequest request,
        UriComponentsBuilder builder
    ) {
        Client newClient = clientService.registerNewClient(request);
        ClientResponse response = new ClientResponse(newClient);

        URI uri = builder.path("/api/v1/clients/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<ClientListResponse> listOfClients() {
        List<Client> listOfClients = clientService.listAllClients();
        ClientListResponse response = new ClientListResponse(listOfClients);

        return ResponseEntity.ok(response);
    }
}
