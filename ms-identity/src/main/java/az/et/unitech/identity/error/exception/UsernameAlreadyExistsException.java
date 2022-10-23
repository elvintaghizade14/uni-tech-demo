package az.et.unitech.identity.error.exception;

import az.et.unitech.common.error.exception.CommonBadRequestException;

public class UsernameAlreadyExistsException extends CommonBadRequestException {

    public UsernameAlreadyExistsException() {
        super("Username already exists!");
    }

}
