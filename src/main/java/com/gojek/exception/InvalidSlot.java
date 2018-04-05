package com.gojek.exception;

/**
 * Created by Gaurav on 04/04/18.
 */


public class InvalidSlot extends Exception {

    private final String message;

    public InvalidSlot(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
