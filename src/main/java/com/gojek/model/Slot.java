package com.gojek.model;

/**
 * Created by Gaurav on 04/04/18.
 */


public class Slot {

    int id;
    private final Car car;

    public Slot(int id, Car car) {
        this.car = car;
        this.id = id;
    }

    public Car getCar() {
        return car;
    }
}
