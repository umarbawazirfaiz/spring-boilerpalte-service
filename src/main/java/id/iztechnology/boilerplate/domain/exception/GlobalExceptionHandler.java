package id.iztechnology.boilerplate.domain.exception;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import id.iztechnology.boilerplate.domain.model.ApiResponse;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.ErrorInfo;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.ResponseInfo;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.ThirdPartyLog;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogContextHolder;
import lombok.RequiredArgsConstructor;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogWrapper;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final LogWrapper logger;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException ex) {
        ApiResponse<?> response = ApiResponse.builder().isSuccess(false).statusCode(ex.getStatusCode()).statusMessage(ex.getMessage()).build();

        LogContext context = LogContextHolder.getContext();

        context.setLevel("ERROR");

        logger.log(context, ex);
        
        LogContextHolder.clear();

        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        LogContext context = LogContext.builder()
            .level("ERROR")
            .response(ResponseInfo.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(ex.getMessage())
                .build())
            .build();

        logger.log(context, ex);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ex.getMessage());
    }

    @ExceptionHandler(ThirdPartyException.class)
    public ResponseEntity<Object> handleThirdPartyException(ThirdPartyException ex, WebRequest request) {
        LogContext context = LogContext.builder()
            .level("ERROR")
            .thirdParty(Collections.singletonList(
                ThirdPartyLog.builder()
                    .servicename(ex.getServiceName())
                    .url(ex.getUrl())
                    .method(ex.getMethod())
                    .request(ex.getRequest())
                    .response(ex.getResponse())
                    .processTime(ex.getProcessTime())
                    .error(ErrorInfo.builder()
                        .message(ex.getMessage())
                        .exceptionName(ex.getClass().getName())
                        .build())
                    .build()
            ))
            .build();

        logger.log(context, ex);

        return ResponseEntity
            .status(HttpStatus.BAD_GATEWAY)
            .body(ex.getMessage());
    }
}