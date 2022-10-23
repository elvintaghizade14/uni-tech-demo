package az.et.unitech.exchange.controller;

import az.et.unitech.exchange.service.ExchangeRateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import az.et.unitech.exchange.config.CommonLibComponents;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExchangeRateController.class)
@Import({CommonLibComponents.class})
class ExchangeRateControllerTest {

    private static final String BASE_PATH = "/api/v1/exchange-rates";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    @WithMockUser(username = "ABC1234")
    void getExchangeRate_Should_ReturnSuccess() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/rate")
                        .param("fromCurrency", "EUR")
                        .param("toCurrency", "AZN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getExchangeRate_Should_ReturnUnauthorized() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/rate")
                        .param("fromCurrency", "EUR")
                        .param("toCurrency", "AZN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
