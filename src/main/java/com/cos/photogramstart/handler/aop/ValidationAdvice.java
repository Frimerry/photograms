package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component
@Aspect
public class ValidationAdvice {
	
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")	// 컨트롤러 내의 메소드 파라미터에 관계없이 모두 적용
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

		Object[] args = proceedingJoinPoint.getArgs();
		
		for(Object arg:args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error:bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationApiException("Validation Error!!!", errorMap);
				}
			}
		}
		return proceedingJoinPoint.proceed();
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

		Object[] args = proceedingJoinPoint.getArgs();
		
		for(Object arg:args) {
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for(FieldError error:bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					throw new CustomValidationException("Validation Error!!!", errorMap);
				}
			}
		}
		return proceedingJoinPoint.proceed();
	}
}
