package id.iztechnology.boilerplate.common.logging;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StreamUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.http.HttpServletRequest;

@Data
@Builder
public class LogContext {
    private String level;
    private String timestamp;
    private String traceId;
    private String transactionId;
    private Long pid;
    private String hostname;
    private String path;
    private String method;
    private String serviceName;
    private String message;
    private RequestInfo request;
    private ResponseInfo response;
    private ErrorInfo errorInfo;
    private Long startTime;
    private Long processTime;
    @Builder.Default
    private List<ApplicationLayerLog> applicationLayer = new ArrayList<>();
    @Builder.Default
    private List<ThirdPartyLog> thirdParty = new ArrayList<>();

    @Data
    @Builder
    public static class ErrorInfo {
        private String message;
        private String exceptionName;
        private String stackTrace;
        private String causedBy;
    }

    @Data
    @Builder
    public static class RequestInfo {
        private Map<String, String> headers;
        private String body;
        private Map<String, String[]> parameters;
        private String method;
        private String url;
        private String queryString;

        public static RequestInfo extract(HttpServletRequest request) {
            RequestInfoBuilder builder = RequestInfo.builder();
            
            // Extract headers
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.put(headerName, request.getHeader(headerName));
            }
            builder.headers(headers);

            // Extract URL information
            StringBuffer requestURL = request.getRequestURL();
            String queryString = request.getQueryString();
            String fullUrl = queryString == null ? requestURL.toString() 
                : requestURL.append('?').append(queryString).toString();
            
            builder.url(fullUrl)
                .queryString(queryString)
                .method(request.getMethod())
                .parameters(request.getParameterMap());

            // Extract body
            String body = extractBody(request);
            builder.body(body);

            return builder.build();
        }

        private static String extractBody(HttpServletRequest request) {
            try {
                // If using ContentCachingRequestWrapper
                if (request instanceof ContentCachingRequestWrapper) {
                    return new String(((ContentCachingRequestWrapper) request).getContentAsByteArray(), 
                        StandardCharsets.UTF_8);
                }
                
                // For regular requests
                return StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                return "Error reading body: " + e.getMessage();
            }
        }
    }

    @Data
    @Builder
    @Slf4j
    public static class ResponseInfo {
        private Map<String, String> headers;
        private Integer status;
        private Object body;

        public static ResponseInfo extract(ContentCachingResponseWrapper response) {
            ResponseInfoBuilder builder = ResponseInfo.builder();
            
            // Extract headers
            Map<String, String> headers = new HashMap<>();
            Collection<String> headerNames = response.getHeaderNames();
            for (String headerName : headerNames) {
                headers.put(headerName, response.getHeader(headerName));
            }

            // Extract body
            String body = getResponseBody(response);
            
            return builder
                .status(response.getStatus())
                .headers(headers)
                .body(body)
                .build();
        }

        private static String getResponseBody(ContentCachingResponseWrapper response) {
            byte[] content = response.getContentAsByteArray();

            log.info("test {}", content);

            if (content.length > 0) {
                try {
                    return new String(content, response.getCharacterEncoding());
                } catch (IOException e) {
                    log.warn("Failed to read response body", e);
                }
            }
            return null;
        }
    }

    @Data
    @Builder
    public static class ThirdPartyLog {
        private String servicename;
        private String url;
        private String method;
        private Object request;
        private Object response;
        private Long processTime;
        private ErrorInfo error;
    }

    @Data
    @Builder
    public static class ApplicationLayerLog {
        private String layer;
        private String methodName;
        private String message;
        private Object request;
        private Object response;
        private Long processTime;
    }
}