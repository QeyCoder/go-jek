package com.gojek;

import com.gojek.exception.InvalidRegistration;
import com.gojek.exception.InvalidSlot;
import com.gojek.exception.ParkingFullException;
import com.gojek.model.Car;
import com.gojek.model.Command;
import com.gojek.service.ParkingService;

import java.io.*;

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
            inputSource = new FileInputStream(args[0]);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputSource));
        if (mode == 0) {
            System.out.println("Type X and press Enter to exit");
        }
        String[] input = bufferedReader.readLine().split(" ");
        int size = Integer.parseInt(input[1]);
        System.out.println("Created a parking lot with " + size + " slots");
        parkingService = new ParkingService(size);


        boolean running = true;
        while (running) {

            String query = bufferedReader.readLine();
            if (query == null && mode == 1) {

                //EOF file
                break;
            }
            String[] splittedQuery = query.split(" ");
            String type = splittedQuery[0];
            if ("X".equalsIgnoreCase(type) || "".equals(type)) {
                break;
            }


            Command command = Command.valueOf(type.toUpperCase());
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
                    response = parkingService.getRegistrationByColor(color);
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
                    response = parkingService.getSlotsByColor(color);
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
                    //TODO NOTHING
                    break;
            }
            System.out.println(response);
        }

        inputSource.close();

    }


}
