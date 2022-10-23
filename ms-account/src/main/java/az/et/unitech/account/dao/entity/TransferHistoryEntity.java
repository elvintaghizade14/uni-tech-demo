package az.et.unitech.account.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name = "transfers")
public class TransferHistoryEntity extends BaseEntity {

    @NotBlank
    @Size(min = 20, max = 34)
    @Column(name = "sender_iban")
    private String senderIban;

    @NotBlank
    @Size(min = 20, max = 34)
    @Column(name = "receiver_iban")
    private String receiverIban;

    @NotNull
    @Column(name = "amount")
    private BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TransferHistoryEntity that = (TransferHistoryEntity) o;
        return Objects.equals(senderIban, that.senderIban) && Objects.equals(receiverIban, that.receiverIban) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), senderIban, receiverIban, amount);
    }
}