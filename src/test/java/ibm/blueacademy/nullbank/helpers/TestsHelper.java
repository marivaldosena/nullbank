package ibm.blueacademy.nullbank.helpers;

import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.requests.NewClientRequest;

import java.math.BigDecimal;

public class TestsHelper {
    public static Client mockClient() {
        return new Client(
            "Nome do cliente",
            "606.344.610-95",
            "Endereço ",
            new BigDecimal("5000.00")
        );
    }

    public static NewClientRequest mockNewClientRequest() {
        return new NewClientRequest(
            "Nome do cliente",
            "606.344.610-95",
            "Endereço ",
            new BigDecimal("5000.00")
        );
    }
}
