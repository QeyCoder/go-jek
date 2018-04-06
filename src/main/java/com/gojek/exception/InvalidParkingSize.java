package com.gojek.exception;

/**
 * Created by Gaurav on 06/04/18.
 */


public class InvalidParkingSize extends Exception {

    private final String message;

    public InvalidParkingSize(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
