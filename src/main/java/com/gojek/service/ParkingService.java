package com.gojek.service;

import com.gojek.exception.InvalidRegistration;
import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import com.gojek.model.Car;
import com.gojek.model.Slot;

import java.util.*;

/**
 * Created by Gaurav on 04/04/18.
 */


public class ParkingService {

    public static final String INVALID_SLOT = "Invalid Slot";
    private int size;
    private int currentCapacity;

    private Slot[] slots;

    private PriorityQueue<Integer> queue;
    private Map<String, List<Slot>> colorVsSlots;
    private Map<String, Integer> regVsSlot = new HashMap<>();
    private final String COMMA = ",";
    private final String NEW_LINE = "\n";

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
            throw new ParkingFullException("Sorry, parking lot is full");
        }
        currentCapacity++;
        Integer slotId = queue.poll() - 1;
        Slot slot = new Slot(slotId, car);
        slots[slotId] = slot;
        String color = car.getColor();
        List<Slot> carSlots = colorVsSlots.get(color);
        if (carSlots == null) {
            carSlots = new ArrayList<>();
        }
        carSlots.add(slot);
        colorVsSlots.put(color, carSlots);
        regVsSlot.put(car.getRegistrationId(), slotId + 1);
        return "Allocated slot number: " + (slotId + 1);
    }

    public String removeCar(int slotId) throws InvalidSlot {
        if (slotId < 0 || slotId > size) {
            throw new InvalidSlot(INVALID_SLOT);
        }
        currentCapacity--;
        Slot slot = slots[slotId];
        Car car = slot.getCar();
        String color = car.getColor();
        colorVsSlots.get(color).remove(slot);
        regVsSlot.remove(car.getRegistrationId());
        queue.add(slotId);
        return "Slot number " + slotId + " is free";
    }


    public String getSlotsByColor(String color) {
        List<Slot> slotList = colorVsSlots.get(color);
        StringBuilder slotIds = new StringBuilder();

        slotList.forEach(slot -> {
            slotIds.append(slot.getId() + 1).append(COMMA);
        });
        slotIds.deleteCharAt(slotIds.length() - 1);
        return slotIds.toString();
    }

    public String getRegistrationByColor(String color) {
        List<Slot> slotList = colorVsSlots.get(color);
        StringBuilder registrationIds = new StringBuilder();
        slotList.forEach(slot -> {
            registrationIds.append(slot.getCar().getRegistrationId()).append(COMMA);
        });
        registrationIds.deleteCharAt(registrationIds.length() - 1);

        return registrationIds.toString();
    }

    public String getSlotByRegistrationId(String regId) throws InvalidRegistration {
        Integer slotId = regVsSlot.get(regId);
        if (slotId == null) {
            throw new InvalidRegistration("Not found");
        }
        return String.valueOf(slotId);

    }


    public String getRegistrationBySlotId(int id) throws Exception {
        Slot slot = slots[id];
        if (slot == null) {
            throw new InvalidSlot(INVALID_SLOT);
        }
        return slot.getCar().getRegistrationId();
    }

    public String getColorBySlotId(int id) throws Exception {
        Slot slot = slots[id];
        if (slot == null) {
            throw new InvalidSlot(INVALID_SLOT);
        }
        return slot.getCar().getColor();
    }


    public String getStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%1s%26s%12s", "Slot No", "Registration No.", "Colour")).append(NEW_LINE);
        for (int i = 0; i < slots.length; i++) {
            Slot slot = slots[i];
            if (slot != null) {
                Car car = slot.getCar();
                stringBuilder.append(String.format("%5s%25s%15s", slot.getId() + 1, car.getRegistrationId(), car.getColor())).append(NEW_LINE);
            }
        }
        return stringBuilder.toString();


    }
}
