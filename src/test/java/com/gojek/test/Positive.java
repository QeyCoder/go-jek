package com.gojek.test;

import com.gojek.exception.InvalidColor;
import com.gojek.exception.InvalidRegistration;
import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import com.gojek.service.ParkingService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gaurav on 05/04/18.
 */


public class Positive {
    private static final int DEFAULT_SIZE = 10;

    @Test
    public void parkLeave() throws ParkingFullException, InvalidSlot {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        String response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9898", "White"));
        Assert.assertEquals("Allocated slot number: 1", response);
        response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9899", "White"));
        Assert.assertEquals("Allocated slot number: 2", response);
        response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Red"));
        Assert.assertEquals("Allocated slot number: 3", response);
        response = parkingservice.removeCar(2);
        Assert.assertEquals("Slot number 2 is free", response);
        response = parkingservice.park(ParkingTestHelper.getCar("DL-32-9970", "Red"));
        Assert.assertEquals("Allocated slot number: 2", response);
    }


    @Test
    public void removeCar() throws ParkingFullException, InvalidSlot {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9898", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9899", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Red"));
        String response = parkingservice.removeCar(3);
        Assert.assertEquals("Slot number 3 is free", response);

    }


    @Test
    public void registrationByColor() throws ParkingFullException, InvalidSlot, InvalidColor {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        String response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9898", "White"));
        Assert.assertEquals("Allocated slot number: 1", response);
        response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9899", "White"));
        Assert.assertEquals("Allocated slot number: 2", response);
        response = parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Red"));
        Assert.assertEquals("Allocated slot number: 3", response);
        response = parkingservice.removeCar(2);
        Assert.assertEquals("Slot number 2 is free", response);
        response = parkingservice.park(ParkingTestHelper.getCar("DL-32-9970", "Red"));
        Assert.assertEquals("Allocated slot number: 2", response);
        response = parkingservice.getRegistrationByColor("Red");
        Assert.assertEquals("DL-32-9970,HR-32-9820", response);
    }


    @Test
    public void slotByColor() throws ParkingFullException, InvalidSlot, InvalidColor {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9898", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9899", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Red"));
        parkingservice.removeCar(2);
        parkingservice.park(ParkingTestHelper.getCar("DL-32-9970", "Red"));
        String response = parkingservice.getSlotsByColor("Red");
        Assert.assertEquals("2,3", response);

    }


    @Test
    public void shouldReturnMinimumAvailbleSlot() throws ParkingFullException, InvalidSlot {
        parkLeave();
        //same test case will cover min slot

    }

    @Test
    public void getColorBySlotId() throws Exception {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9898", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9899", "White"));
        String response = parkingservice.getColorBySlotId(1);
        Assert.assertEquals("White", response);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Red"));
        parkingservice.removeCar(2);
        parkingservice.park(ParkingTestHelper.getCar("DL-32-9970", "Green"));
        response = parkingservice.getColorBySlotId(2);
        Assert.assertEquals("Green", response);

    }

    @Test
    public void getSlotByRegistrationId() throws ParkingFullException, InvalidSlot, InvalidColor, InvalidRegistration {
        ParkingService parkingservice = new ParkingService(DEFAULT_SIZE);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9898", "White"));
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9899", "White"));
        String response = parkingservice.getSlotByRegistrationId("HR-32-9898");
        Assert.assertEquals("1", response);
        parkingservice.park(ParkingTestHelper.getCar("HR-32-9820", "Red"));
        parkingservice.removeCar(2);
        parkingservice.park(ParkingTestHelper.getCar("DL-32-9970", "Green"));
        response = parkingservice.getSlotByRegistrationId("DL-32-9970");
        Assert.assertEquals("2", response);
    }




}
