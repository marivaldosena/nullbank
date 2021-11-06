package ibm.blueacademy.nullbank.helpers;

import ibm.blueacademy.nullbank.models.Account;
import ibm.blueacademy.nullbank.models.AccountType;
import ibm.blueacademy.nullbank.models.Agency;
import ibm.blueacademy.nullbank.models.Client;
import ibm.blueacademy.nullbank.requests.NewAccountRequest;
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

    public static Agency mockAgency() {
        return new Agency(
            "Agencia Central",
            "0001"
        );
    }

    public static Account mockAccount() {
        return new Account(
            mockClient(),
            AccountType.CURRENT_ACCOUNT,
            mockAgency()
        );
    }

    public static NewAccountRequest mockNewAccountRequest() {
        return new NewAccountRequest(
            "606.344.610-95",
            "0001",
            AccountType.CURRENT_ACCOUNT
        );
    }
}
