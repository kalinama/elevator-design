package core;

import entity.Building;
import entity.Passenger;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CartingTaskTest {
	@Test
	public void testSimpleThreads() throws Exception {
		final String file = "src/test/resources/test-correct.property";
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