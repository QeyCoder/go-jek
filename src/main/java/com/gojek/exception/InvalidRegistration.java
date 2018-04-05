package com.gojek.exception;

/**
 * Created by Gaurav on 05/04/18.
 */


public class InvalidRegistration extends Exception {


    private final String message;

    public InvalidRegistration(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
