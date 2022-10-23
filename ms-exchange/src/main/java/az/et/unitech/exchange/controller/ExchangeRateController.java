package az.et.unitech.exchange.controller;


import az.et.unitech.exchange.model.dto.ExchangeRateDto;
import az.et.unitech.exchange.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exchange-rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/rate")
    public ResponseEntity<ExchangeRateDto> getExchangeRate(@Valid @RequestParam @NotBlank String fromCurrency,
                                                           @Valid @RequestParam @NotBlank String toCurrency) {
        ExchangeRateDto exchangeRate = exchangeRateService.getExchangeRate(fromCurrency, toCurrency);
        return ResponseEntity.ok(exchangeRate);
    }

}
