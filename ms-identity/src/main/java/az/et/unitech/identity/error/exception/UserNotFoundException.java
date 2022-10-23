package az.et.unitech.identity.error.exception;

import az.et.unitech.common.error.exception.CommonAuthException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CommonAuthException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "User not found!");
    }

}
