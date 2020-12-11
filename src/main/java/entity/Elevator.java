package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.enums.Direction;

import java.util.ArrayList;
import java.util.List;

public class Elevator {
    private static final Logger log = LoggerFactory.getLogger(Elevator.class);

    private final List<Passenger> elevatorPassengers;
    private int currentStory;
    private final int storage;
    private Direction direction;
    private final int storyNumbers;


    public Elevator(final int storage, final int storyNumbers) {
        if (storage < 1 || storyNumbers < 2) {
            throw new IllegalArgumentException("Elevator storage must be greater than 0 and storyNumbers must be great than 1");
        }
        this.currentStory = 1;
        this.storyNumbers = storyNumbers;
        this.storage = storage;
        this.elevatorPassengers = new ArrayList<>(storage);
        this.direction = Direction.UP;
    }

    public List<Passenger> getElevatorPassengers() {
        return elevatorPassengers;
    }

    public int getCurrentStory() {
        return currentStory;
    }

    public int getStorage() {
        return storage;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(final Direction direction) {
        this.direction = direction;
    }

    public boolean letMeEnter() {
        return elevatorPassengers.size() < storage;
    }


    public void enterElevator(final Passenger pas) {
        log.info("BOARDING OF PASSENGER ( ID = " + pas.getPassengerID() + " on story-" + currentStory + ")");
        if (elevatorPassengers.size() < storage) {
            elevatorPassengers.add(pas);
        }
    }

    public void leaveElevator(final Passenger pas) {
        log.info("DEBOARDING OF PASSENGER ( ID = " + pas.getPassengerID() + " on story-" + currentStory + ")");
        elevatorPassengers.remove(pas);
    }


    public void up() {
        if (storyNumbers > currentStory) {
            log.info("MOVING ELEVATOR (from story-" + currentStory + " to story-" + (currentStory + 1) + ")");
            currentStory++;
        } else {
            throw new IllegalStateException("Elevator can't move");
        }
    }


    public void down() {
        if (currentStory > 1) {
            log.info("MOVING ELEVATOR (from story-" + currentStory + " to story-" + (currentStory - 1) + ")");
            currentStory--;
        } else {
            throw new IllegalStateException("Elevator can't move");
        }
    }

    public synchronized void waitInElevator() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread has been interrupted..." + e);
        }
    }

    public synchronized void notifyElevatorPassengers() {
        notifyAll();
    }

    public int getStoryNumbers() {
        return storyNumbers;
    }
}
