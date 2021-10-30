package ibm.blueacademy.nullbank.services;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.GeneratedValue;

import java.math.BigDecimal;

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

    @Test
    void shouldRegisterNewClientGivenANonAccountHolder() {
        // Arrange
        Client expectedClient = new Client(
            "Nome do cliente",
            "123.456.789-00",
            "Endereço ",
            new BigDecimal("5000.00")
        );
        Mockito.when(clientRepository.save(any())).thenReturn(expectedClient);

        // Act
        Client newClient = clientService.registerNewClient(
            "Nome do cliente",
            "123.456.789-00",
            "Endereço ",
            "5000.00"
        );

        // Assert
        assertEquals(expectedClient.getName(), newClient.getName());
        assertEquals(expectedClient.getCpf(), newClient.getCpf());
        assertEquals(expectedClient.getAddress(), newClient.getAddress());
        assertEquals(expectedClient.getSalary(), newClient.getSalary());

        // Verify
        Mockito.verify(clientRepository).save(any());
    }
}
