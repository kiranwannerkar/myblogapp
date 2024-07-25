package com.myblog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public BlogAPIException( HttpStatus status,String message) {
        super(message); //this mean we r calling the constructor of parents class
        this.message = message;  // here we r initialising status and message both
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
