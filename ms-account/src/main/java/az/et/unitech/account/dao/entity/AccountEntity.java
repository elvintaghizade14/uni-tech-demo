package az.et.unitech.account.dao.entity;

import az.et.unitech.account.model.enums.AccountStatus;
import az.et.unitech.account.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class AccountEntity extends BaseEntity {

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(min = 20, max = 34)
    @Column(name = "iban", unique = true)
    private String iban;

    @NotBlank
    @Size(min = 7, max = 7)
    @Column(name = "customer_id")
    private String customerId;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AccountStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountEntity that = (AccountEntity) o;
        return Objects.equals(username, that.username) && Objects.equals(iban, that.iban) && Objects.equals(customerId, that.customerId) && Objects.equals(balance, that.balance) && status == that.status && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username, iban, customerId, balance, status, currency);
    }
}