package com.gojek.test;

import com.gojek.exception.*;
import com.gojek.service.ParkingService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gaurav on 05/04/18.
 */


public class Negative {


    private int DEFAULT_SIZE = 10;

    @Test(expected = InvalidParkingSize.class)
    public void invalidParkingSize() throws InvalidParkingSize {
        ParkingService parkingservice = new ParkingService(-8);
    }

    @Test(expected = ParkingFullException.class)
    public void parkWhenParkingFull() throws ParkingFullException, InvalidRegistration, InvalidParkingSize {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1001", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1002", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1003", "Green"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1004", "Red"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1005", "Red"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1006", "Blue"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1007", "Red"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1008", "Yellow"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1009", "Red"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1010", "Black"));
        String response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Green"));
        Assert.assertEquals("HR-32-9820,DL-32-9970", response);

    }


    @Test(expected = InvalidSlot.class)
    public void invalidSlot() throws ParkingFullException, InvalidSlot, InvalidRegistration, InvalidParkingSize {

        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        //1st slot for
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1001", "White"));
        //2nd slot for
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1002", "White"));
        //5 is not yet acquired
        parkingservice.removeCar(5);
    }


    @Test(expected = InvalidColor.class)
    public void queryForInvalidColor() throws ParkingFullException, InvalidColor, InvalidRegistration, InvalidParkingSize {

        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1001", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-1002", "Red"));
        //Green  car not yet parked
        parkingservice.getSlotsByColor("Green");
    }


}
