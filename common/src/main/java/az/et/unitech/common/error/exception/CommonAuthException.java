package az.et.unitech.common.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonAuthException extends RuntimeException {

    private final Integer code;
    private final String message;

}
