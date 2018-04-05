package com.gojek.service;

import com.gojek.exception.InvalidColor;
import com.gojek.exception.InvalidRegistration;
import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import com.gojek.helper.Constant;
import com.gojek.helper.SlotComparator;
import com.gojek.model.Car;
import com.gojek.model.Slot;

import java.text.MessageFormat;
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
    private Map<String, Integer> regVsSlot = new HashMap<>();

    private Comparator<Slot> slotComparator = new SlotComparator();

    public ParkingService(int size) {
        this.size = size;
        slots = new Slot[size + 1];
        queue = new PriorityQueue<Integer>();
        colorVsSlots = new HashMap<String, List<Slot>>();
        for (int i = 1; i <= size; i++) {
            queue.add(i);
        }
    }


    public String park(Car car) throws ParkingFullException {
        if (currentCapacity == size) {
            throw new ParkingFullException(Constant.PARKING_FULL);
        }
        currentCapacity++;
        Integer slotId = queue.poll();
        Slot slot = new Slot(slotId, car);
        slots[slotId] = slot;
        String color = car.getColor();
        List<Slot> carSlots = colorVsSlots.get(color);
        if (carSlots == null) {
            carSlots = new ArrayList<>();
        }
        carSlots.add(slot);
        colorVsSlots.put(color, carSlots);
        regVsSlot.put(car.getRegistrationId(), slotId);
        return MessageFormat.format(Constant.ALLOCATED_SLOT_NUMBER, slotId);
    }

    public String removeCar(int slotId) throws InvalidSlot {
        if (slotId < 0 || slotId > size) {
            throw new InvalidSlot(Constant.INVALID_SLOT);
        }
        currentCapacity--;
        Slot slot = slots[slotId];
        if (slot == null) {
            throw new InvalidSlot(Constant.INVALID_SLOT);
        }
        Car car = slot.getCar();
        String color = car.getColor();
        colorVsSlots.get(color).remove(slot);
        regVsSlot.remove(car.getRegistrationId());
        queue.add(slotId);
        return MessageFormat.format(Constant.FREE_SLOT, slotId);
    }


    public String getSlotsByColor(String color) throws InvalidColor {
        List<Slot> slotList = colorVsSlots.get(color);
        if (slotList == null) {
            throw new InvalidColor(Constant.NO_CAR_WITH_COLOR);
        }
        Collections.sort(slotList, slotComparator);
        StringBuilder slotIds = new StringBuilder();

        slotList.forEach(slot -> {
            slotIds.append(slot.getId()).append(Constant.COMMA);
        });
        slotIds.deleteCharAt(slotIds.length() - 1);
        return slotIds.toString();
    }

    public String getRegistrationByColor(String color) throws InvalidColor {
        List<Slot> slotList = colorVsSlots.get(color);
        if (slotList == null) {
            throw new InvalidColor(Constant.NO_CAR_WITH_COLOR);
        }
        Collections.sort(slotList, slotComparator);
        StringBuilder registrationIds = new StringBuilder();
        slotList.forEach(slot -> {
            registrationIds.append(slot.getCar().getRegistrationId()).append(Constant.COMMA);
        });
        registrationIds.deleteCharAt(registrationIds.length() - 1);

        return registrationIds.toString();
    }

    public String getSlotByRegistrationId(String regId) throws InvalidRegistration {
        Integer slotId = regVsSlot.get(regId);
        if (slotId == null) {
            throw new InvalidRegistration(Constant.NOT_FOUND);
        }
        return String.valueOf(slotId);

    }


    public String getColorBySlotId(int id) throws InvalidSlot {
        Slot slot = slots[id];
        if (slot == null) {
            throw new InvalidSlot(Constant.INVALID_SLOT);
        }
        return slot.getCar().getColor();
    }


    public String getStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format(Constant.HEADER_FORMATTER, Constant.SLOT_NO, Constant.REGISTRATION_NO, Constant.COLOUR)).append(Constant.NEW_LINE);
        for (int i = 0; i < slots.length; i++) {
            Slot slot = slots[i];
            if (slot != null) {
                Car car = slot.getCar();
                stringBuilder.append(String.format(Constant.VALUE_FORMATTER, slot.getId(), car.getRegistrationId(), car.getColor())).append(Constant.NEW_LINE);
            }
        }
        return stringBuilder.toString();


    }
}
