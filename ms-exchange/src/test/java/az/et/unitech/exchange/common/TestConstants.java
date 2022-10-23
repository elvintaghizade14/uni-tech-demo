package az.et.unitech.exchange.common;

import az.et.unitech.exchange.client.model.ExchangeRateResponse;
import az.et.unitech.exchange.dao.entity.ExchangeRateEntity;
import az.et.unitech.exchange.model.dto.ExchangeRateDto;

import java.math.BigDecimal;
import java.util.Collections;

public final class TestConstants {

    //CHECKSTYLE:OFF
    public static final String EUR = "EUR";
    public static final String AZN = "AZN";
    public static final String USD = "USD";
    public static final BigDecimal EUR_AZN_RATE = BigDecimal.valueOf(1.6606D); // en azi men baxanda bele idi, umid edirem en qisa zamanda dirilecek 😭

    public static final ExchangeRateEntity EXCHANGE_RATE_ENTITY = ExchangeRateEntity.builder()
            .fromCurrency(EUR)
            .toCurrency(AZN)
            .rate(EUR_AZN_RATE)
            .build();
    public static final ExchangeRateDto EXCHANGE_RATE_DTO = ExchangeRateDto.builder()
            .rate(EUR_AZN_RATE)
            .toCurrency(AZN)
            .fromCurrency(EUR)
            .build();

    public static final ExchangeRateResponse EXCHANGE_RATE_RESPONSE = ExchangeRateResponse.builder()
            .base(EUR)
            .rates(Collections.singletonMap(AZN, EUR_AZN_RATE))
            .build();

    //CHECKSTYLE:ON

}