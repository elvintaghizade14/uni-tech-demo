package az.et.unitech.exchange.service;

import az.et.unitech.common.error.exception.CommonBadRequestException;
import az.et.unitech.common.error.exception.CommonException;
import az.et.unitech.exchange.client.ExchangeRateClient;
import az.et.unitech.exchange.client.model.ExchangeRateResponse;
import az.et.unitech.exchange.dao.entity.ExchangeRateEntity;
import az.et.unitech.exchange.dao.repository.ExchangeRateRepository;
import az.et.unitech.exchange.mapper.ExchangeRateMapper;
import az.et.unitech.exchange.model.dto.ExchangeRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExchangeRateService {

    private final ExchangeRateMapper exchangeRateMapper;
    private final ExchangeRateClient exchangeRateClient;
    private final ExchangeRateRepository exchangeRateRepository;

    @Transactional
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void updateExchangeRates() {
        final ExchangeRateResponse response = exchangeRateClient.updateExchangeRates();
        List<ExchangeRateEntity> rates = response.getRates()
                .entrySet()
                .stream()
                .map(entry -> {
                    final String fromCurrency = response.getBase();
                    final String toCurrency = entry.getKey();
                    return exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                            .map(exchangeRateEntity -> {
                                exchangeRateEntity.setRate(entry.getValue());
                                return exchangeRateEntity;
                            })
                            .orElse(new ExchangeRateEntity(fromCurrency, toCurrency, entry.getValue()));
                })
                .collect(Collectors.toList());
        exchangeRateRepository.saveAll(rates);
    }

    public ExchangeRateDto getExchangeRate(String fromCurrency, String toCurrency) {
        if (!"EUR".equalsIgnoreCase(fromCurrency))
            throw new CommonBadRequestException("ONLY EUR supported as base currency for now");
        return exchangeRateRepository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency)
                .map(exchangeRateMapper::toDto)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND.value(), "No data found!"));
    }

}
