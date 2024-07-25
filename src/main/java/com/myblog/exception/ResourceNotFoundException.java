package com.myblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private long fieldValue;


    public ResourceNotFoundException(String resourceName,String fieldName,long fieldValue ) {
        super(String.format("%s not found with %s : '%s'",resourceName,fieldName,fieldValue));// we can substitute field,%s get substitute with these three field resourceName,fieldName,fieldValue here we supply value dynamically
        //super call stmt supply value to the Parent class Runtime Exception whtevr message it goes to postman from RunTime Exception
        this.resourceName = resourceName;
        this.fieldName=fieldName;
        this.fieldValue=fieldValue;
    }
    // bcz of constructor no need to generate setters

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public long getFieldValue() {
        return fieldValue;
    }
}
