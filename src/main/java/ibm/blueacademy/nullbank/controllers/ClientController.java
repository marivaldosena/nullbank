package ibm.blueacademy.nullbank.controllers;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import ibm.blueacademy.nullbank.responses.ClientResponse;
import ibm.blueacademy.nullbank.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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
}
