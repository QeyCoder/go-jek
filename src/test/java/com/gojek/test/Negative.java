package com.gojek.test;

import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import org.junit.Test;

/**
 * Created by Gaurav on 05/04/18.
 */


public class Negative {

    @Test(expected = ParkingFullException.class)
    public void parkWhenParkingFull() {


    }


    @Test(expected = InvalidSlot.class)
    public void queryForInvalidSlot() {


    }


    @Test(expected = InvalidSlot.class)
    public void queryForInvalidColor() {


    }


}
