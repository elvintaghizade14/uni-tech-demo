package az.et.unitech.common.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CommonBadRequestException extends RuntimeException {

    private final Integer code;
    private final String message;

    public CommonBadRequestException(String message) {
        super(message);
        this.code = HttpStatus.BAD_REQUEST.value();
        this.message = message;
    }

    public CommonBadRequestException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
