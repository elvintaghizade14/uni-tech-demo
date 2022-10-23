package az.et.unitech.common.error.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonErrorResponse {

    private final String id;
    private final Integer code;
    private final String message;
    
    public CommonErrorResponse(String id, HttpStatus status, String message) {
        this.id = id;
        this.code = status.value();
        this.message = message;
    }

}
