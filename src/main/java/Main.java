import core.Controller;
import core.Generator;
import core.CartingTask;
import entity.Building;
import entity.Passenger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class Main {

    public static void main(final String[] args) throws IOException {
        final String file = "src/main/resources/config.property";
        Generator gnr = new Generator(file);
        Building building = new Building(gnr);
        Controller controller = new Controller(building);
        ExecutorService pool = Executors.newFixedThreadPool(gnr.getPassengersNumber());
        for (Passenger pas : building.getPassengerList()) {
            CartingTask task = new CartingTask(pas, building, controller);
            pool.execute(task);
        }
        pool.shutdown();
        Thread controllerThread = new Thread(controller);
        controllerThread.start();
    }

}
