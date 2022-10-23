package az.et.unitech.identity.error.exception;

import az.et.unitech.common.error.exception.CommonAuthException;
import org.springframework.http.HttpStatus;

public class UserNotEnabledException extends CommonAuthException {

    public UserNotEnabledException() {
        super(HttpStatus.UNAUTHORIZED.value(), "User not enabled!");
    }

}
