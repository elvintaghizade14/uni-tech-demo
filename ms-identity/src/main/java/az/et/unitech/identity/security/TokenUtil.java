package az.et.unitech.identity.security;

import az.et.unitech.common.model.constant.CommonConstant.HttpAttribute;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenUtil {

    public static String extractToken(final String authorizationHeader) {
        TokenValidator.validateAuthorizationHeader(authorizationHeader);
        return authorizationHeader.replace(HttpAttribute.BEARER, "").trim();
    }

}
