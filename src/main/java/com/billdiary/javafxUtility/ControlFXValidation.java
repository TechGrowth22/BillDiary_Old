package com.billdiary.javafxUtility;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;
import org.springframework.stereotype.Component;

import javafx.scene.control.Control;

@Component
public class ControlFXValidation {
	public Validator<String> getStringValidator() {
    	Validator<String> validator = new Validator<String>()
	    {
	      @Override
	      public ValidationResult apply( Control control, String value )
	      {
	        boolean condition =
		            (value != null)?(!value.matches("^(\\w+)$")):false;             
	        return ValidationResult.fromMessageIf( control, "not a valid String", Severity.ERROR, condition );
	      }
	    };
    	return validator;
    }
}
