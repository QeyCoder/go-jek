package com.gojek;

import com.gojek.exception.InvalidColor;
import com.gojek.exception.InvalidRegistration;
import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import com.gojek.helper.Constant;
import com.gojek.model.Car;
import com.gojek.model.Command;
import com.gojek.service.ParkingService;

import java.io.*;
import java.text.MessageFormat;

/**
 * Created by Gaurav on 04/04/18.
 */


public class ApplicationEntryPoint {

    public static ParkingService parkingService;

    public static void main(String[] args) throws IOException {


        InputStream inputSource = null;
        int mode = 0;
        if (args.length == 0) {
            inputSource = System.in;
        } else {
            mode = 1;
            try {
                inputSource = new FileInputStream(args[0]);
            } catch (FileNotFoundException e) {
                System.out.println(Constant.INVALID_FILE_PATH);
                return;
            }
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputSource));
        if (mode == 0) {
            System.out.println(Constant.EXIT_COMMAND);
        }

        boolean running = true;

        //read command
        String query = bufferedReader.readLine();

        if (query == null) {
            System.out.println(Constant.INVALID_QUERY);
        }
        String[] input = query.split(Constant.SPACE);
        Command command;
        if (input.length != 2) {
            System.out.println(Constant.INVALID_QUERY);
            return;
        }
        try {
            command = Command.valueOf(input[0].toUpperCase());
        } catch (Exception e) {
            System.out.println(Constant.INVALID_QUERY);
            return;
        }
        int size = Integer.parseInt(input[1]);
        System.out.println(MessageFormat.format(Constant.PARKING_INTIALIZE, size));
        parkingService = new ParkingService(size);


        while (running) {

            query = bufferedReader.readLine();
            if (query == null && mode == 1) {

                //EOF file
                break;
            }
            String[] splittedQuery = query.split(Constant.SPACE);
            String type = splittedQuery[0];
            if (Constant.EXIT_KEY.equalsIgnoreCase(type) || Constant.BLANK.equals(type)) {
                break;
            }

            try {

                command = Command.valueOf(type.toUpperCase());
            } catch (Exception e) {
                System.out.println(Constant.INVALID_QUERY);
                return;
                // invalid Query
            }
            String response = null;
            String registrationNumber;
            String color;

            switch (command) {
                case PARK:
                    registrationNumber = splittedQuery[1];
                    color = splittedQuery[2];
                    Car car = new Car(registrationNumber, color);
                    try {
                        response = parkingService.park(car);
                    } catch (ParkingFullException e) {
                        response = e.getMessage();
                    }

                    break;
                case REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR:
                    color = splittedQuery[1];
                    try {
                        response = parkingService.getRegistrationByColor(color);
                    } catch (InvalidColor invalidColor) {
                        response = invalidColor.getMessage();
                    }
                    break;
                case SLOT_NUMBER_FOR_REGISTRATION_NUMBER:
                    registrationNumber = splittedQuery[1];
                    try {
                        response = parkingService.getSlotByRegistrationId(registrationNumber);
                    } catch (InvalidRegistration invalidRegistration) {
                        response = invalidRegistration.getMessage();
                    }
                    break;
                case STATUS:
                    response = parkingService.getStatus();
                    break;
                case SLOT_NUMBERS_FOR_CARS_WITH_COLOUR:
                    color = splittedQuery[1];
                    try {
                        response = parkingService.getSlotsByColor(color);
                    } catch (InvalidColor invalidColor) {
                        response = invalidColor.getMessage();
                    }
                    break;
                case LEAVE:

                    int slotId = Integer.parseInt(splittedQuery[1]);
                    try {
                        response = parkingService.removeCar(slotId);
                    } catch (InvalidSlot invalidSlot) {
                        response = invalidSlot.getMessage();
                    }
                    break;
                default:
                    break;
            }
            System.out.println(response);
        }

        inputSource.close();

    }


}
