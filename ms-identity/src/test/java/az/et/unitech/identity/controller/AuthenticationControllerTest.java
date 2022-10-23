package az.et.unitech.identity.controller;

import az.et.unitech.identity.annotations.EnabledIfRedisReachable;
import az.et.unitech.identity.model.request.RefreshTokenRequest;
import az.et.unitech.identity.model.request.SigninRequest;
import az.et.unitech.identity.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;

import static az.et.unitech.identity.common.TestConstants.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnabledIfRedisReachable(host = "localhost", port = 6379)
class AuthenticationControllerTest {

    private static final String BASE_PATH = "/api/v1/auth";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthenticationService authenticationService;

    /*** AuthenticationController -> signin() ***/
    @Test
    void signin_Should_Return_Success() throws Exception {
        given(authenticationService.signin(VALID_USER_LOGIN)).willReturn(TOKEN_PAIR);

        final String expectedResult = mapper.writeValueAsString(TOKEN_PAIR);
        mockMvc.perform(post(BASE_PATH + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(VALID_USER_LOGIN)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        then(authenticationService).should(times(1)).signin(VALID_USER_LOGIN);
    }

    @Test
    void signin_Should_Throw_BadCredentialsException() throws Exception {
        given(authenticationService.signin(INVALID_USER_LOGIN)).willThrow(BadCredentialsException.class);

        mockMvc.perform(post(BASE_PATH + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(INVALID_USER_LOGIN)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("Username or password is wrong")));

        then(authenticationService).should(times(1)).signin(INVALID_USER_LOGIN);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "user,null,'password' must not be blank",
            "null,pass,'username' must not be blank",
            "null,null,'username' must not be blank"}, nullValues = {"null"})
    void signin_Should_Throw_MethodArgumentNotValidException(String user, String pass, String msg) throws Exception {
        SigninRequest loginRequest = new SigninRequest(user, pass);

        mockMvc.perform(post(BASE_PATH + "/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", containsString(msg)));
    }

    /*** AuthenticationController -> signout() ***/
    @Test
    void signout_Should_Return_Success() throws Exception {
        willDoNothing().given(authenticationService).signout(VALID_AUTHORIZATION_HEADER);

        mockMvc.perform(get(BASE_PATH + "/signout")
                        .header("Authorization", VALID_AUTHORIZATION_HEADER))
                .andExpect(status().isOk());

        then(authenticationService).should(times(1)).signout(VALID_AUTHORIZATION_HEADER);
    }

    @Test
    void signout_Should_Throw_AuthenticationException() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/signout")
                        .header("Authorization", INVALID_AUTHORIZATION_HEADER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void signout_WhenAuthorizationHeaderNotExists_Should_Throw_AuthenticationException() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/signout"))
                .andExpect(status().isUnauthorized());
    }

    /*** AuthenticationController -> verify() ***/
    @Test
    void verify_Should_Return_Success() throws Exception {
        willDoNothing().given(authenticationService).verify(VALID_AUTHORIZATION_HEADER);

        mockMvc.perform(get(BASE_PATH + "/verify")
                        .header("Authorization", VALID_AUTHORIZATION_HEADER))
                .andExpect(status().isOk());

        then(authenticationService).should(times(1)).verify(VALID_AUTHORIZATION_HEADER);
    }

    @Test
    void verify_Should_Throw_AuthenticationException() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/verify")
                        .header("Authorization", INVALID_AUTHORIZATION_HEADER))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void verify_WhenAuthorizationHeaderNotExists_Should_Throw_AuthenticationException() throws Exception {
        mockMvc.perform(get(BASE_PATH + "/verify"))
                .andExpect(status().isUnauthorized());
    }

    /*** AuthenticationController -> refresh() ***/
    @Test
    void refresh_Should_Return_Success() throws Exception {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken(VALID_REFRESH_TOKEN);

        given(authenticationService.refreshToken(refreshTokenRequest.getRefreshToken())).willReturn(TOKEN_PAIR);

        final String expectedResult = mapper.writeValueAsString(TOKEN_PAIR);
        mockMvc.perform(post(BASE_PATH + "/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(refreshTokenRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        then(authenticationService).should(times(1)).refreshToken(refreshTokenRequest.getRefreshToken());
    }

    @Test
    void refresh_Should_Throw_MethodArgumentNotValidException() throws Exception {
        mockMvc.perform(post(BASE_PATH + "/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(new RefreshTokenRequest())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code", is(400)))
                .andExpect(jsonPath("$.message", is("'refreshToken' must not be blank")));
    }

}