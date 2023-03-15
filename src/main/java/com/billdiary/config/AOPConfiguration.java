package com.billdiary.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AOPConfiguration {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Before("execution(* com.billdiary.*.*(..))")
    public void before(JoinPoint joinPoint) {
        //Advice
        logger.info("AOPConfiguration Entering in the method {}", joinPoint);
    }
	
	@After(value = "execution(* com.billdiary.*.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info("AOPConfiguration Exiting fro the method {}", joinPoint);
    }

}
