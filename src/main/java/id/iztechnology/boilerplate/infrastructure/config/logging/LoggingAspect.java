package id.iztechnology.boilerplate.infrastructure.config.logging;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import id.iztechnology.boilerplate.infrastructure.config.logging.LogContext.ApplicationLayerLog;
import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
@Component
@Order(1)
@Slf4j
public class LoggingAspect {

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return logLayer(joinPoint, "CONTROLLER");
    }

    @Around("@within(org.springframework.stereotype.Service)")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        return logLayer(joinPoint, "SERVICE");
    }

    @Around("@within(org.springframework.stereotype.Repository)")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        return logLayer(joinPoint, "REPOSITORY");
    }

    private Object logLayer(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        LogContext context = LogContextHolder.getContext();

        try {
            Object result = joinPoint.proceed();
            
            if (context != null) {
                addLayerLog(context, layer, methodName, args.length > 0 ? args[0] : null, 
                    result, startTime);
            }
            
            return result;

        } catch (Exception e) {
            if (context != null) {
                addLayerLog(context, layer, methodName, args.length > 0 ? args[0] : null, 
                    e.getMessage(), startTime);
            }
            throw e;
        }
    }

    private void addLayerLog(LogContext context, String layer, String methodName, 
        Object request, Object response, long startTime) {
        
        context.getApplicationLayer().add(ApplicationLayerLog.builder()
            .layer(layer)
            .methodName(methodName)
            .request(request)
            .response(response)
            .processTime(System.currentTimeMillis() - startTime)
            .build());
    }
}