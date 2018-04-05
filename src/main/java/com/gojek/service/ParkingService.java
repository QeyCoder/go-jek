package com.gojek.service;
import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import com.gojek.model.Car;
import com.gojek.model.Slot;

import java.util.*;

/**
 * Created by Gaurav on 04/04/18.
 */


public class ParkingService {

    private int size;
    private int currentCapacity;

    private Slot[] slots;

    private PriorityQueue<Integer> queue;
    private Map<String, List<Slot>> colorVsSlots;

    public ParkingService(int size) {
        this.size = size;
        slots = new Slot[size];
        queue = new PriorityQueue<Integer>();
        colorVsSlots = new HashMap<String, List<Slot>>();
        for (int i = 1; i <= size; i++) {
            queue.add(i);
        }
    }


    public String park(Car car) throws ParkingFullException {
        if (currentCapacity == size) {
            throw new ParkingFullException("Sorry,   parking   lot   is   full");
        }
        Integer slotId = queue.poll();
        Slot slot = new Slot(slotId, car);
        slots[slotId] = slot;
        String color = car.getColor();
        List<Slot> carSlots = colorVsSlots.get(color);
        if (carSlots == null) {
            carSlots = new ArrayList<Slot>();
        }
        carSlots.add(slot);
        colorVsSlots.put(color, carSlots);
        return "Allocated slot number: " + slotId;
    }

    public String removeCar(int slotId) throws InvalidSlot {
        if (slotId < 0 || slotId > size) {
            throw new InvalidSlot("Invalid Slot");
        }

        Slot slot = slots[slotId];
        Car car = slot.getCar();
        String color = car.getColor();
        colorVsSlots.get(color).remove(slot);
        queue.add(slotId);
        return "Slot number " + slotId + " is free";
    }


    public Collection<Slot> getSlotsByColor(String color) {
        List<Slot> slotList = colorVsSlots.get(color);
        if (slotList == null) {

            slotList = new ArrayList<Slot>();
        }

        return slotList;
    }

    public Collection<String> getRegistrationBySlot(String color) {
        Collection<Slot> slotList = getSlotsByColor(color);
        Collection<String> strings = new ArrayList<>();
        slotList.forEach(slot -> {
            strings.add(slot.getCar().getRegistrationId());
        });
        return strings;
    }

    public void getSlotByRegistrationId(String regId) {
        //TODO

    }


    public String getRegistrationBySlotId(int id) throws Exception {
        Slot slot = slots[id];
        if (slot == null) {
            throw new InvalidSlot("Invalid Slot");
        }
        return slot.getCar().getRegistrationId();
    }

    public String getColorBySlotId(int id) throws Exception {
        Slot slot = slots[id];
        if (slot == null) {
            throw new InvalidSlot("Invalid Slot");
        }
        return slot.getCar().getColor();
    }


}
