package az.et.unitech.exchange.dao.repository;

import az.et.unitech.exchange.dao.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, Long> {

    Optional<ExchangeRateEntity> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);

}
