package az.et.unitech.identity.security;

import az.et.unitech.identity.error.exception.InvalidAccessTokenException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static az.et.unitech.common.model.constant.CommonConstant.HttpAttribute.BEARER;
import static az.et.unitech.identity.common.TestConstants.INVALID_AUTHORIZATION_HEADER;
import static az.et.unitech.identity.common.TestConstants.VALID_AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenValidatorTest {

    @Test
    void validateAuthorizationHeader_Should_Return_Success() {
        TokenValidator.validateAuthorizationHeader(VALID_AUTHORIZATION_HEADER);
    }

    @ParameterizedTest
    @MethodSource("authHeaderParams")
    void validateAuthorizationHeader_Should_Throw_InvalidAccessTokenException(String authHeader) {
        assertThrows(InvalidAccessTokenException.class,
                () -> TokenValidator.validateAuthorizationHeader(authHeader));
    }

    static Stream<String> authHeaderParams() {
        return Stream.of(null, INVALID_AUTHORIZATION_HEADER, BEARER);
    }

}