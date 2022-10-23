package az.et.unitech.identity.error.exception;

import az.et.unitech.common.error.exception.CommonAuthException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends CommonAuthException {

    private static InvalidRefreshTokenException instance;

    private InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED.value(), "Invalid refresh token!");
    }

    public static InvalidRefreshTokenException getInstance() {
        if (instance == null)
            synchronized (InvalidRefreshTokenException.class) {
                instance = new InvalidRefreshTokenException();
            }
        return instance;
    }

}
