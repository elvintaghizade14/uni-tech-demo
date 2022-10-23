package az.et.unitech.exchange.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDto {

    private BigDecimal rate;
    private String toCurrency;
    private String fromCurrency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRateDto that = (ExchangeRateDto) o;
        return Objects.equals(rate, that.rate) && Objects.equals(toCurrency, that.toCurrency) && Objects.equals(fromCurrency, that.fromCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, toCurrency, fromCurrency);
    }
    
}
