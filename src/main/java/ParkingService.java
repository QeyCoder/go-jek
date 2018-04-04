import com.gojek.model.Car;
import com.gojek.model.Slot;

import java.util.PriorityQueue;

/**
 * Created by Gaurav on 04/04/18.
 */


public class ParkingService {

    private int size;
    private int currentCapacity;

    private Slot[] slots;

    private PriorityQueue<Integer> cars;

    public ParkingService(int size) {
        this.size = size;
        slots = new Slot[size];
        cars = new PriorityQueue<Integer>();
        for (int i = 1; i <= size; i++) {
            cars.add(i);
        }
    }


    public void park(Car car){

    }


}
