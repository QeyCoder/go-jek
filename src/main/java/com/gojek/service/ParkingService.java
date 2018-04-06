package com.gojek.service;

import com.gojek.exception.*;
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
    /**
     * Storing color as lowecase to avoid confusion between red & RED or Red Same for registrationId
     */
    private Map<String, List<Slot>> colorVsSlots;
    private Map<String, Integer> regVsSlot = new HashMap<>();

    private Comparator<Slot> slotComparator = new SlotComparator();

    /**
     * @param size
     */
    public ParkingService(int size) throws InvalidParkingSize {
        if (size < 0 || size > Integer.MAX_VALUE) {
            throw new InvalidParkingSize(Constant.INVALID_PARKING_SIZE);
        }
        this.size = size;
        slots = new Slot[size + 1];
        queue = new PriorityQueue<>();
        colorVsSlots = new HashMap<>();
        /**
         * slot will be always be unused using 1 extra space  to make logic simple
         */

        for (int i = 1; i <= size; i++) {
            queue.add(i);
        }
    }

    /**
     * @param car
     * @return
     * @throws ParkingFullException
     */
    public String park(Car car) throws ParkingFullException, InvalidRegistration {
        if (currentCapacity == size) {
            throw new ParkingFullException(Constant.PARKING_FULL);
        }
        if (regVsSlot.containsKey(car.getRegistrationId().toLowerCase())) {
            throw new InvalidRegistration(MessageFormat.format(Constant.CAR_ALREADY_EXIST, car.getRegistrationId()));
        }
        currentCapacity++;
        Integer slotId = queue.poll();
        Slot slot = new Slot(slotId, car);
        slots[slotId] = slot;
        String color = car.getColor().toLowerCase();
        List<Slot> carSlots = colorVsSlots.get(color);
        if (carSlots == null) {
            carSlots = new ArrayList<>();
        }
        carSlots.add(slot);
        colorVsSlots.put(color, carSlots);
        regVsSlot.put(car.getRegistrationId().toLowerCase(), slotId);
        return MessageFormat.format(Constant.ALLOCATED_SLOT_NUMBER, slotId);
    }

    /**
     * @param slotId
     * @return
     * @throws InvalidSlot
     */
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
        String color = car.getColor().toLowerCase();
        colorVsSlots.get(color).remove(slot);
        regVsSlot.remove(car.getRegistrationId().toLowerCase());
        queue.add(slotId);
        slots[slotId] = null;
        return MessageFormat.format(Constant.FREE_SLOT, slotId);
    }

    /**
     * @param color
     * @return
     * @throws InvalidColor
     */
    public String getSlotsByColor(String color) throws InvalidColor {
        List<Slot> slotList = colorVsSlots.get(color.toLowerCase());
        if (slotList == null || slotList.isEmpty()) {
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

    /**
     * @param color
     * @return
     * @throws InvalidColor
     */
    public String getRegistrationByColor(String color) throws InvalidColor {
        List<Slot> slotList = colorVsSlots.get(color.toLowerCase());
        if (slotList == null || slotList.isEmpty()) {
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

    /**
     * @param regId
     * @return
     * @throws InvalidRegistration
     */
    public String getSlotByRegistrationId(String regId) throws InvalidRegistration {
        Integer slotId = regVsSlot.get(regId.toLowerCase());
        if (slotId == null) {
            throw new InvalidRegistration(Constant.NOT_FOUND);
        }
        return String.valueOf(slotId);

    }

    /**
     * @param id
     * @return
     * @throws InvalidSlot
     */

    public String getColorBySlotId(int id) throws InvalidSlot {
        Slot slot = slots[id];
        if (slot == null) {
            throw new InvalidSlot(Constant.INVALID_SLOT);
        }
        return slot.getCar().getColor();
    }

    /**
     * @return
     */

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
