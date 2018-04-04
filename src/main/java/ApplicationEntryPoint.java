import java.io.*;

/**
 * Created by Gaurav on 04/04/18.
 */


public class ApplicationEntryPoint {

    public static ParkingService parkingService;

    public static void main(String[] args) throws IOException {
        InputStream source = null;
        int type = Integer.parseInt(args[0]);
        if (type == 0) {
            source = System.in;
        } else if (type == 1) {
            String file = args[1];
            source = new FileInputStream(file);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(source));

        String[] input = bufferedReader.readLine().split(" ");
        int size = Integer.parseInt(input[1]);
        parkingService = new ParkingService(size);

        source.close();

    }


}
