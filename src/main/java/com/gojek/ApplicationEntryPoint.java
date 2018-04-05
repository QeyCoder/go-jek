package com.gojek;

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
        InputStream source = null;
        int mode = 0;
        if (args.length == 0) {
            source = System.in;
        } else {
            mode = 1;
            source = new FileInputStream(args[1]);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(source));
        if (mode == 1) {
            System.out.println("Type X to exit");
        }
        String[] input = bufferedReader.readLine().split(" ");
        int size = Integer.parseInt(input[1]);
        System.out.println("Created a parking lot with " + size + " slots");
        parkingService = new ParkingService(size);


        boolean running = true;
        while (running) {
            String query = bufferedReader.readLine();

            String[] splittedQuery = query.split(" ");
            String type = splittedQuery[0];
            if (type.equalsIgnoreCase("X")) {
                break;
            }
            Command command = Command.valueOf(type.toUpperCase());
            String response = null;
            switch (command) {
                case PARK:

                    String registrationNumber = splittedQuery[1];
                    String color = splittedQuery[2];
                    Car car = new Car(registrationNumber, color);
                    try {
                        response = parkingService.park(car);

                    } catch (ParkingFullException e) {
                        response = e.getMessage();
                    }

                    break;
                case REGISTRATION_NUMBERS_FOR_CARS_WITH_COLOUR:


                    break;
                case SLOT_NUMBER_FOR_REGISTRATION_NUMBER:
                    //TODO
                    break;
                case STATUS:
                    //TODO
                    break;
                case SLOT_NUMBERS_FOR_CARS_WITH_COLOUR:
                    //TODO
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
                    //TODO
                    break;


            }
            System.out.println(response);
        }
        System.out.println("Bye Bye !!");

        source.close();

    }


}
