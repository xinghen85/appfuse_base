package com.btxy.basis.morphia.core;

import org.springframework.validation.Errors;
import org.springmodules.validation.commons.DefaultValidatorFactory;

public class BtxyValidatorFactory extends DefaultValidatorFactory{
	public BtxyValidatorFactory(){
		super();
		//this.getValidator(beanName, bean, errors)
	}
	public org.apache.commons.validator.Validator  getValidator(String beanName, Object bean, Errors errors){
		org.apache.commons.validator.Validator v= super.getValidator(beanName, bean, errors);
		return v;
	}

}
