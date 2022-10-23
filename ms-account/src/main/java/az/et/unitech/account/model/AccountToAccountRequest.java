package az.et.unitech.account.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountToAccountRequest {

    @NotBlank
    private String senderAccountIban;

    @NotBlank
    private String receiverAccountIban;

    @NotNull
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountToAccountRequest that = (AccountToAccountRequest) o;
        return Objects.equals(senderAccountIban, that.senderAccountIban) && Objects.equals(receiverAccountIban, that.receiverAccountIban) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderAccountIban, receiverAccountIban, amount);
    }

}
