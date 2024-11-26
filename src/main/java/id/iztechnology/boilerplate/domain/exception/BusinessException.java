package id.iztechnology.boilerplate.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class BusinessException extends RuntimeException {
    private final String statusCode;
    private final HttpStatus httpStatus;

    public BusinessException(String statusCode, String message, HttpStatus httpStatus) {
        super(message);
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
    }
}
