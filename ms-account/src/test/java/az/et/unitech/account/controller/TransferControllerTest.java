package az.et.unitech.account.controller;

import az.et.unitech.account.config.CommonLibComponents;
import az.et.unitech.account.service.TransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static az.et.unitech.account.common.TestConstants.ACCOUNT_TO_ACCOUNT_REQUEST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransferController.class)
@Import({CommonLibComponents.class})
class TransferControllerTest {

    private static final String BASE_PATH = "/api/v1/transfers";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransferService transferService;

    @Test
    @WithMockUser(username = "ABC1234")
    void transferToAccount_Should_ReturnSuccess() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/a2a")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(ACCOUNT_TO_ACCOUNT_REQUEST)))
                .andExpect(status().isAccepted());
    }

    @Test
    void transferToAccount_Should_ReturnUnauthorized() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/a2a")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
