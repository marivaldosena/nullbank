package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.helpers.TestsHelper;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import ibm.blueacademy.nullbank.requests.NewClientRequest;
import ibm.blueacademy.nullbank.services.impl.DefaultClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class ClientServiceTests {

    @MockBean
    private ClientRepository clientRepository;

    private DefaultClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new DefaultClientService(clientRepository);
    }

    @DisplayName("should register new client given a non-account holder")
    @Test
    void registerClient() {
        // Arrange
        Client expectedClient = TestsHelper.mockClient();
        NewClientRequest request = TestsHelper.mockNewClientRequest();
        Mockito.when(clientRepository.save(any())).thenReturn(expectedClient);

        // Act
        Client newClient = clientService.registerNewClient(request);

        // Assert
        assertEquals(expectedClient.getName(), newClient.getName());
        assertEquals(expectedClient.getCpf(), newClient.getCpf());
        assertEquals(expectedClient.getAddress(), newClient.getAddress());
        assertEquals(expectedClient.getSalary(), newClient.getSalary());

        // Verify
        Mockito.verify(clientRepository).save(any());
    }

    @Test
    void shouldListAllClientWhenSolicited() {
        // Arrange
        List<Client> expectedListOfClients = List.of(TestsHelper.mockClient());
        Mockito.when(clientRepository.findAll()).thenReturn(expectedListOfClients);

        // Act
        List<Client> allClients = clientService.listAllClients();

        // Assert
        assertEquals(expectedListOfClients.get(0).getCpf(), allClients.get(0).getCpf());
        assertEquals(expectedListOfClients.get(0).getName(), allClients.get(0).getName());
        assertEquals(expectedListOfClients.get(0).getAddress(), allClients.get(0).getAddress());
        assertEquals(expectedListOfClients.get(0).getSalary(), allClients.get(0).getSalary());

        // Verify
        Mockito.verify(clientRepository).findAll();
    }

    @Test
    void shouldReturnAnEmptyListWhenThereIsNoClient() {
        // Arrange
        List<Client> expectedListOfCustomers = Collections.emptyList();
        Mockito.when(clientRepository.findAll()).thenReturn(expectedListOfCustomers);

        // Act
        List<Client> allClients = clientService.listAllClients();

        // Assert
        assertEquals(expectedListOfCustomers.size(), allClients.size());

        // Verify
        Mockito.verify(clientRepository).findAll();
    }
}
