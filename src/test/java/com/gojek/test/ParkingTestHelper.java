package com.gojek.test;

import com.gojek.model.Car;

/**
 * Created by Gaurav on 05/04/18.
 */


public class ParkingTestHelper {







    public static Car getCar(String registration, String color) {

        Car car = new Car(registration, color);
        return car;
    }
}
