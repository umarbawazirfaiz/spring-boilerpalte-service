package id.iztechnology.boilerplate.infrastructure.config.logging;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.RequestInfo;
import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.ResponseInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {
    private final LogWrapper logger;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);

        RequestInfo requestInfo = RequestInfo.extract(wrappedRequest);

        LogContext context = LogContext.builder()
            .level("INFO")
            .traceId(UUID.randomUUID().toString())
            .path(request.getServletPath())
            .method(request.getMethod())
            .serviceName("boilerplate")
            .request(requestInfo)
            .startTime(startTime)
            .build();
        LogContextHolder.setContext(context);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        ContentCachingResponseWrapper wrappedResponse =  new ContentCachingResponseWrapper(response);

        try {
            LogContext context = LogContextHolder.getContext();
            
            ResponseInfo responseInfo = ResponseInfo.extract(wrappedResponse);
            
            // Copy content back to response
            wrappedResponse.copyBodyToResponse();

            if (context != null) {
                context.setLevel("INFO");
                context.setProcessTime(System.currentTimeMillis() - context.getStartTime());
                context.setResponse(responseInfo);
                logger.log(context, ex);
            }
        } finally {
            LogContextHolder.clear();
        }
    }
}

