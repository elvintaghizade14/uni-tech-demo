package az.et.unitech.exchange.service;

import az.et.unitech.common.error.exception.CommonBadRequestException;
import az.et.unitech.common.error.exception.CommonException;
import az.et.unitech.exchange.client.ExchangeRateClient;
import az.et.unitech.exchange.dao.repository.ExchangeRateRepository;
import az.et.unitech.exchange.mapper.ExchangeRateMapper;
import az.et.unitech.exchange.model.dto.ExchangeRateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static az.et.unitech.exchange.common.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.boot.actuate.trace.http.Include.PRINCIPAL;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateClient exchangeRateClient;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUpSecurityContext() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(PRINCIPAL, null));
        exchangeRateService = new ExchangeRateService(ExchangeRateMapper.INSTANCE, exchangeRateClient, exchangeRateRepository);
    }

    @Test
    void getExchangeRate_Should_ReturnSuccess() {
        given(exchangeRateRepository.findByFromCurrencyAndToCurrency(EUR, AZN))
                .willReturn(Optional.of(EXCHANGE_RATE_ENTITY));

        final ExchangeRateDto response = exchangeRateService.getExchangeRate(EUR, AZN);
        assertNotNull(response);
        assertThat(response).isEqualTo(EXCHANGE_RATE_DTO);

        then(exchangeRateRepository).should(times(1)).findByFromCurrencyAndToCurrency(EUR, AZN);
    }

    @Test
    void getExchangeRate_Should_ThrowCommonException_When_RateNotFound() {
        given(exchangeRateRepository.findByFromCurrencyAndToCurrency(EUR, USD)).willReturn(Optional.empty());

        CommonException ex = assertThrows(CommonException.class, () -> exchangeRateService.getExchangeRate(EUR, USD));
        assertThat(ex.getCode()).isEqualTo(404);
        assertThat(ex.getMessage()).isEqualTo("No data found!");

        then(exchangeRateRepository).should(times(1)).findByFromCurrencyAndToCurrency(EUR, USD);
    }

    @Test
    void getExchangeRate_Should_ThrowCommonBadRequestException_When_BaseCurrencyIsNotEur() {
        CommonBadRequestException ex = assertThrows(CommonBadRequestException.class, () -> exchangeRateService.getExchangeRate(AZN, USD));
        assertThat(ex.getCode()).isEqualTo(400);
        assertThat(ex.getMessage()).isEqualTo("ONLY EUR supported as base currency for now");
    }

    @Test
    void updateExchangeRates_Should_ReturnSuccess() {
        given(exchangeRateClient.updateExchangeRates()).willReturn(EXCHANGE_RATE_RESPONSE);
        given(exchangeRateRepository.saveAll(List.of(EXCHANGE_RATE_ENTITY)))
                .willReturn(List.of(EXCHANGE_RATE_ENTITY));

        exchangeRateService.updateExchangeRates();

        then(exchangeRateClient).should(times(1)).updateExchangeRates();
        then(exchangeRateRepository).should(times(1)).saveAll(any());
    }

}
