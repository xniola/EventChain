package it.univpm.eventchain.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import it.univpm.eventchain.controller.GlobalDefaultExceptionHandler;
import it.univpm.eventchain.controller.GlobalDefaultExceptionHandler.ExceptionResponse;

@Aspect
@Component
public class LoggingAspect {
	
	private final Log log = LogFactory.getLog(getClass());
	
	@Around("execution(* it.univpm.eventchain.controller.*.*(..))")
    public Object logAroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable 
    {
        String preText = " Controller: " +joinPoint.getTarget() + " Method: " + joinPoint.getSignature().getName()+
        		         " logged ";
        
        String textBefore = preText + "before";
        
        String textAfter = preText + "after";
                       
        log.info(textBefore);
        
        Object retVal = joinPoint.proceed();
        
        if (joinPoint.getTarget() instanceof GlobalDefaultExceptionHandler) {
        	ExceptionResponse exResp = (ExceptionResponse) retVal;            
            log.error("Exception: " + exResp.getErrorMessage());        	
        }else {        	
            log.info(textAfter);        	        	
        }        
        
        return retVal;
    }
	
}
