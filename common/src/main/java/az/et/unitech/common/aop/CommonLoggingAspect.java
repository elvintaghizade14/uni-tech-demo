package az.et.unitech.common.aop;

import az.et.unitech.common.util.LogUtil;
import az.et.unitech.common.util.WebUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.v;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommonLoggingAspect {

    private final WebUtil webUtil;

    @Around("execution(* az.et.unitech.*.controller..*(..)))")
    public Object logEndpoints(ProceedingJoinPoint joinPoint) throws Throwable {
        String uri = webUtil.getRequestUri();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        Map<String, Object> params = LogUtil.getParamsAsMap(methodSignature.getParameterNames(), joinPoint.getArgs());

        log.debug("[Request]  | Uri: {} [{}.{}] | Params: {}",
                v("uri", uri), className, methodName, v("params", params));

        final long start = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        final long elapsedTime = System.currentTimeMillis() - start;

        log.debug("[Response] | Uri: {} [{}.{}] | Elapsed time: {} ms | Result: {}",
                uri, className, methodName, v("elapsed_time", elapsedTime), result);

        return result;
    }

}


