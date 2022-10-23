package az.et.unitech.common.security.util;

import az.et.unitech.common.model.constant.CommonConstant.HttpAttribute;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtil {

    public static String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpAttribute.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(HttpAttribute.BEARER))
            return authHeader.substring(HttpAttribute.BEARER.length());
        return null;
    }

}
