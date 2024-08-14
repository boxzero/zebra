package com.houseclay.zebra.exceptionHandling;

import lombok.Data;

import java.util.UUID;

@Data
public class IdNotFoundException extends Exception{

    private UUID id;
    private String message;
    public IdNotFoundException(UUID id, String message){
        this.id=id;
        this.message=message;
    }
    public IdNotFoundException() {
        super();
    }

    public IdNotFoundException(String message) {
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNotFoundException(Throwable cause) {
        super(cause);
    }

    protected IdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
