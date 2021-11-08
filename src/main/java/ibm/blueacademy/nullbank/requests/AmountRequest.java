package ibm.blueacademy.nullbank.requests;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

public class AmountRequest {
    @DecimalMin("0.01")
    private BigDecimal amount;

    @JsonCreator
    public AmountRequest(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
