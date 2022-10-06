package com.aeturnum.employee.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.ALREADY_REPORTED)
public class ResourceAlreadyFoundException extends RuntimeException {
	
	public ResourceAlreadyFoundException(String message) {
        super(message);
    }

}
