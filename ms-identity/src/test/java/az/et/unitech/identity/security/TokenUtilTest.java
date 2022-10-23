package az.et.unitech.identity.security;

import org.junit.jupiter.api.Test;

import static az.et.unitech.identity.common.TestConstants.VALID_AUTHORIZATION_HEADER;
import static org.assertj.core.api.Assertions.assertThat;

class TokenUtilTest {

    @Test
    void extractToken_Should_Return_Success() {
        final String accessToken = TokenUtil.extractToken(VALID_AUTHORIZATION_HEADER);
        assertThat(accessToken)
                .isNotBlank()
                .doesNotContainAnyWhitespaces()
                .contains(".");
    }

}