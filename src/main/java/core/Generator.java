package core;

import entity.Passenger;
import entity.Story;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.enums.CartingState;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;


public class Generator {
    private static final Logger log = LoggerFactory.getLogger(Generator.class);
    private int storiesNumber;
    private int passengersNumber;
    private int elevatorCapacity;


    public Generator(final String dataFile) throws IOException {
        readData(dataFile);
    }

    public int getStoriesNumber() {
        return storiesNumber;
    }

    public int getPassengersNumber() {
        return passengersNumber;
    }

    public int getElevatorCapacity() {
        return elevatorCapacity;
    }


    public List<Passenger> generatePassengers() {
        List<Passenger> container = new ArrayList<>();
        Random random = new Random();
        int id = 0;
        try {
            log.info("GENERATING NEW PASSENGERS:");
            while (id < passengersNumber) {
                id++;
                Constructor<Passenger> constructor = Passenger.class.getDeclaredConstructor(Integer.class, CartingState.class, Integer.class, Integer.class);
                constructor.setAccessible(true);
                int initialStory = random.nextInt(storiesNumber) + 1;
                int destinationStory = initialStory;
                while (initialStory == destinationStory) {
                    destinationStory = random.nextInt(storiesNumber) + 1;
                }
                Passenger pas = constructor.newInstance(id, CartingState.NOT_STARTED, initialStory, destinationStory);
                log.info(pas.toString());
                container.add(pas);
            }
        } catch (NoSuchMethodException | InstantiationException
                | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return container;
    }


    public List<Story> generateDispatchContainers(final List<Passenger> passengers) {
        List<Story> dispatchContainers = new ArrayList<>(storiesNumber);
        for (int i = 0; i < storiesNumber; i++) {
            Story story = new Story();
            dispatchContainers.add(story);
        }
        log.info("GENERATING NEW DISPATCH CONTAINERS...");
        for (Passenger pas : passengers) {
            dispatchContainers.get(pas.getInitialStory() - 1).addPassenger(pas);
        }
        return dispatchContainers;
    }

    public List<Story> generateArrivalContainers() {
        List<Story> arrivalContainers = new ArrayList<>(storiesNumber);
        log.info("GENERATING NEW ARRIVAL CONTAINERS...");
        for (int i = 0; i < storiesNumber; i++) {
            Story story = new Story();
            arrivalContainers.add(story);
        }
        return arrivalContainers;
    }


    private void readData(final String dataFile) throws IOException {
        try (Reader fr = new FileReader(dataFile)) {
            Properties properties = new Properties();
            properties.load(fr);
            storiesNumber = Integer.parseInt(properties.getProperty("storiesNumber"));
            if (storiesNumber < 2) {
                throw new IOException("elevatorCapacity must be greater or equal to 2");
            }
            passengersNumber = Integer.parseInt(properties.getProperty("passengersNumber"));
            if (passengersNumber < 1) {
                throw new IOException("passengersNumber must be greater or equal to 1");
            }
            elevatorCapacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));
            if (elevatorCapacity < 1) {
                throw new IOException("elevatorCapacity must be greater or equal to 1");
            }
        }
    }
}
