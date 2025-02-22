package id.iztechnology.boilerplate.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ThirdPartyException extends RuntimeException {
    private final String code;
    private final String category;
    private final HttpStatus status;

    private String serviceName;
    private String url;
    private String method;
    private Object request;
    private Object response;
    private Long processTime;

    public ThirdPartyException(String code, String message, String category, HttpStatus status) {
        super(message);
        this.code = code;
        this.category = category;
        this.status = status;
    }
}
