package javaspring.javaspringpractice.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* javaspring.javaspringpractice..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
            long start = System.currentTimeMillis();
            System.out.println("start : "+joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally{
            long end = System.currentTimeMillis();
            long timeMs = start - end;
            System.out.println("end : "+joinPoint.toString()+ " "+timeMs+"ms");
        }

    }
}
