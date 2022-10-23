package az.et.unitech.identity.error.exception;

import az.et.unitech.common.error.exception.CommonAuthException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends CommonAuthException {

    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED.value(), "Invalid access token!");
    }

}
