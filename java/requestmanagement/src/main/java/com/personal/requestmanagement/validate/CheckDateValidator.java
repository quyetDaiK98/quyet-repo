package com.personal.requestmanagement.validate;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import com.personal.requestmanagement.utils.DateUtil;
import com.personal.requestmanagement.utils.StringUtil;
import com.personal.requestmanagement.validate.annotation.DateConstraint;

public class CheckDateValidator implements ConstraintValidator<DateConstraint, Object> {
	private String fromDate;
	private String toDate;

	@Override
	public void initialize(DateConstraint constraintAnnotation) {
		this.fromDate = constraintAnnotation.fromDate();
		this.toDate = constraintAnnotation.toDate();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		String fromValue = (String) new BeanWrapperImpl(value).getPropertyValue(fromDate);
		
		String toValue = (String) new BeanWrapperImpl(value).getPropertyValue(toDate);
		
		if(!StringUtil.isEmpty(fromValue) && !StringUtil.isEmpty(toValue)) {
			Date from = DateUtil.stringToDate(fromValue, DateUtil.FORMAT_DDMMYYYY_HHMM);
			Date to = DateUtil.stringToDate(toValue, DateUtil.FORMAT_DDMMYYYY_HHMM);
			return to.after(from) || to.equals(from);
		}

		return true;
	}

}
