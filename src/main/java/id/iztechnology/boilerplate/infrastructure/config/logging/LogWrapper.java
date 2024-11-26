package id.iztechnology.boilerplate.infrastructure.config.logging;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.ErrorInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.ThreadContext;

@Component
public class LogWrapper {
    private final Logger logger;
    private final ObjectMapper objectMapper;

    @Autowired
    public LogWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.logger = LoggerFactory.getLogger(getClass());
        
        // Configure ObjectMapper for consistent date format
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    

    // Alternative constructor for specific class logging
    public LogWrapper(Class<?> clazz, ObjectMapper objectMapper) {
        this.logger = LoggerFactory.getLogger(clazz);
        this.objectMapper = objectMapper;
    }
    
    public void log(LogContext context, Throwable throwable) {
        try {
            if (throwable != null) {
                context.setLevel("ERROR");
                context.setErrorInfo(buildErrorInfo(throwable));
            }

            context.setTimestamp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()));
            context.setPid(ProcessHandle.current().pid());
            context.setHostname(InetAddress.getLocalHost().getHostName());
            
            String jsonLog = objectMapper.writeValueAsString(context);
            switch(context.getLevel().toUpperCase()) {
                case "ERROR": logger.error(jsonLog); break;
                case "WARN": logger.warn(jsonLog); break;
                case "DEBUG": logger.debug(jsonLog); break;
                case "TRACE": logger.trace(jsonLog); break;
                default: logger.info(jsonLog);
            }
        } catch (Exception e) {
            logger.error("Error logging message", e);
        }
    }

    private ErrorInfo buildErrorInfo(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        Throwable rootCause = getRootCause(throwable);
        
        return ErrorInfo.builder()
            .message(throwable.getMessage())
            .exceptionName(throwable.getClass().getName())
            .stackTrace(sw.toString())
            .causedBy(rootCause != throwable ? rootCause.getMessage() : null)
            .build();
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }
        return rootCause;
    }

    public void info(LogContext context) {
        try {
            setContextValues(context);
            logger.info(objectMapper.writeValueAsString(context));
        } catch (Exception e) {
            logger.error("Error logging info message", e);
        } finally {
            ThreadContext.clearAll();
        }
    }

    public void error(LogContext context, Throwable throwable) {
        try {
            setContextValues(context);
            logger.error(objectMapper.writeValueAsString(context), throwable);
        } catch (Exception e) {
            logger.error("Error logging error message", e);
        } finally {
            ThreadContext.clearAll();
        }
    }

    private void setContextValues(LogContext context) {
        ThreadContext.put("traceId", context.getTraceId());
        ThreadContext.put("serviceName", context.getServiceName());
    }
}