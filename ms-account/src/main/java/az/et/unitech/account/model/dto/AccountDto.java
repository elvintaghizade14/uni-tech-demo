package az.et.unitech.account.model.dto;

import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.account.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long id;
    private String iban;
    private String username;
    private Currency currency;
    private String customerId;
    private BigDecimal balance;
    private AccountStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(iban, that.iban) && Objects.equals(customerId, that.customerId) && Objects.equals(balance, that.balance) && status == that.status && currency == that.currency && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, iban, customerId, balance, status, currency, createdAt, updatedAt);
    }

}
