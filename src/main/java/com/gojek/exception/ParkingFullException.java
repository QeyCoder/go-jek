package com.gojek.exception;

/**
 * Created by Gaurav on 04/04/18.
 */


public class ParkingFullException extends Exception {

    private final String message;

    public ParkingFullException(String message) {
        this.message = message;
    }


}
