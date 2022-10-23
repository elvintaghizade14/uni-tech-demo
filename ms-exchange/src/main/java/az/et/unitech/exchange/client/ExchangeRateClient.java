package az.et.unitech.exchange.client;

import az.et.unitech.exchange.client.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "exchange-rates-api", url = "${application.client.exchange-rates-api.url}")
public interface ExchangeRateClient {

    @GetMapping("/latest")
    ExchangeRateResponse updateExchangeRates();

}
