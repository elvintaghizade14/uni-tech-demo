package az.et.unitech.common.model.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonConstant {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class HttpAttribute {
        public static final String REQUEST_ID = "request_id";
        public static final String ELAPSED_TIME = "elapsed_time";
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";
        public static final String AUTHORIZATION = "Authorization";
        public static final String BEARER = "Bearer ";
    }

}
