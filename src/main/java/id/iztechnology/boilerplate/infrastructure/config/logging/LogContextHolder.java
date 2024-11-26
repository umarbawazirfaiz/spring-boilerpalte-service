package id.iztechnology.boilerplate.infrastructure.config.logging;

import org.springframework.stereotype.Component;

@Component
public class LogContextHolder {
    private static final ThreadLocal<LogContext> contextHolder = new ThreadLocal<>();

    public static void setContext(LogContext context) {
        contextHolder.set(context);
    }

    public static LogContext getContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }
}