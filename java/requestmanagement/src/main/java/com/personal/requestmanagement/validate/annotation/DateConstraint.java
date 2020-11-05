package com.personal.requestmanagement.validate.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.personal.requestmanagement.validate.CheckDateValidator;

@Documented
@Constraint(validatedBy = {CheckDateValidator.class})
@Target( { TYPE })
@Retention(RUNTIME)
public @interface DateConstraint {
	String message() default "Thời gian từ đang lớn hơn thời gian đến !";
	 
    String fromDate();
 
    String toDate();
    
    Class<?>[] groups() default {};
    
    Class<? extends Payload>[] payload() default { };
}
