package az.et.unitech.identity.security;

import az.et.unitech.common.model.constant.CommonConstant.HttpAttribute;
import az.et.unitech.identity.error.exception.InvalidAccessTokenException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenValidator {

    public static void validateAuthorizationHeader(String authorizationHeader) {
        if (StringUtils.isBlank(authorizationHeader) || !authorizationHeader.startsWith(HttpAttribute.BEARER) ||
                StringUtils.isBlank(authorizationHeader.replace(HttpAttribute.BEARER, "")))
            throw new InvalidAccessTokenException();
    }

}
