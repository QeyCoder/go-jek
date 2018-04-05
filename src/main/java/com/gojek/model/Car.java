package com.gojek.model;

/**
 * Created by Gaurav on 04/04/18.
 */


public class Car {

    private String registrationId;
    private String color;

    public Car(String registrationNumber, String color) {
        this.registrationId = registrationNumber;
        this.color = color;
    }


    public String getRegistrationId() {
        return registrationId;
    }

    public String getColor() {
        return color;
    }

}
