package com.gojek.exception;

/**
 * Created by Gaurav on 05/04/18.
 */


public class InvalidColor extends Exception {


    private final String message;

    public InvalidColor(String message) {
        this.message  =  message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
