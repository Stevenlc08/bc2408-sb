package com.bootcamp.demo.bc_forum2.exception;

public class RestTemplateException extends RuntimeException {

    public RestTemplateException(String message) {
        super(message);
    }


    public RestTemplateException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestTemplateException(Throwable cause) {
        super("RestTemplate Error - JsonPlaceHolder", cause);
    }
}