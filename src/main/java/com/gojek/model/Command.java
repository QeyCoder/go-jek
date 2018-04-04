package com.gojek.model;

/**
 * Created by Gaurav on 04/04/18.
 */
public enum Command {


    CREATE("create_parking_lot"), PARK("park"), LEAVE("leave"), STATUS("status"), REG_COLOR("registration_numbers_for_cars_with_colour"), SLOT_COLOR("slot_numbers_for_cars_with_colour"), SLOT_REGISTRATION("slot_number_for_registration_number");

    private final String msg;

    Command(String msg) {
        this.msg = msg;
    }
}
