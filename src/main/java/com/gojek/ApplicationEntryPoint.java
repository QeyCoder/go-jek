package com.gojek;

import com.gojek.exception.*;
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

        if (query == null || query.isEmpty()) {
            System.out.println(Constant.EXIT);
            return;
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
        try {
            parkingService = new ParkingService(size);
        } catch (InvalidParkingSize invalidParkingSize) {
            System.out.println(invalidParkingSize.getMessage());
            return;
        }


        while (running) {

            query = bufferedReader.readLine();
            if (query == null && mode == 1) {

                //EOF file
                break;
            }
            if (query == null || query.isEmpty()) {
                break;
            }
            String[] splittedQuery = query.split(Constant.SPACE);
            String type = splittedQuery[0];

            String response = null;
            String registrationNumber;
            String color;
            try {

                command = Command.valueOf(type.toUpperCase());
            } catch (Exception e) {
                response = Constant.INVALID_QUERY;
                return;
            }

            switch (command) {
                case PARK:
                    if (splittedQuery.length != 3) {
                        response = Constant.INVALID_QUERY;
                    } else {
                        response = park(splittedQuery);
                    }
                    break;
                case REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR:
                    if (splittedQuery.length != 2) {
                        response = Constant.INVALID_QUERY;
                    } else {
                        response = getRegistrationNumberByColor(splittedQuery);
                    }
                    break;
                case SLOT_NUMBER_FOR_REGISTRATION_NUMBER:
                    if (splittedQuery.length != 2) {
                        response = Constant.INVALID_QUERY;
                    } else {
                        response = getSlotByRegistrationNumber(splittedQuery);
                    }
                    break;
                case STATUS:
                    response = parkingService.getStatus();
                    break;
                case SLOT_NUMBERS_FOR_CARS_WITH_COLOUR:
                    if (splittedQuery.length != 2) {
                        response = Constant.INVALID_QUERY;
                    } else {
                        response = getSlotByColor(splittedQuery);
                    }
                    break;
                case LEAVE:
                    if (splittedQuery.length != 2) {
                        response = Constant.INVALID_QUERY;
                    } else {
                        response = removeCar(splittedQuery);
                    }
                    break;
                default:
                    break;
            }
            System.out.println(response);
        }
        System.out.println(Constant.EXIT);
        inputSource.close();

    }

    private static String getSlotByRegistrationNumber(String[] splittedQuery) {
        String registrationNumber;
        String response;
        registrationNumber = splittedQuery[1];
        try {
            response = parkingService.getSlotByRegistrationId(registrationNumber);
        } catch (InvalidRegistration invalidRegistration) {
            response = invalidRegistration.getMessage();
        }
        return response;
    }

    private static String getSlotByColor(String[] splittedQuery) {
        String color;
        String response;
        color = splittedQuery[1];
        if (color == null) {
            return Constant.INVALID_QUERY;
        }
        try {
            response = parkingService.getSlotsByColor(color);
        } catch (InvalidColor invalidColor) {
            response = invalidColor.getMessage();
        }
        return response;
    }

    private static String removeCar(String[] s) {
        String response;
        int slotId;
        try {

            slotId = Integer.parseInt(s[1]);
        } catch (Exception e) {
            return Constant.INVALID_QUERY;
        }
        try {
            response = parkingService.removeCar(slotId);
        } catch (InvalidSlot invalidSlot) {
            response = invalidSlot.getMessage();
        }
        return response;
    }

    private static String getRegistrationNumberByColor(String[] splittedQuery) {
        String color;
        String response;
        color = splittedQuery[1];
        if (color == null) {

            return Constant.INVALID_QUERY;
        }
        try {
            response = parkingService.getRegistrationByColor(color);
        } catch (InvalidColor invalidColor) {
            response = invalidColor.getMessage();
        }
        return response;
    }

    private static String park(String[] splittedQuery) {
        String registrationNumber;
        String color;
        String response;
        registrationNumber = splittedQuery[1];
        if (registrationNumber == null) {
            return Constant.INVALID_QUERY;
        }

        color = splittedQuery[2];
        if (color == null) {
            return Constant.INVALID_QUERY;

        }
        Car car = new Car(registrationNumber, color);
        try {
            response = parkingService.park(car);
        } catch (ParkingFullException e) {
            response = e.getMessage();
        } catch (InvalidRegistration invalidRegistration) {
            response = invalidRegistration.getMessage();
        }
        return response;
    }


}
