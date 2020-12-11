package core;

import entity.Building;
import entity.Passenger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import entity.enums.Direction;
import entity.enums.CartingState;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Controller.class);
    private final Building building;


    public Controller(final Building building) {
        this.building = building;
        log.info("TRANSPORTATION WILL START SOON");
    }

    public void moveElevator(){
        if (Direction.UP.equals(building.getElevatorContainer().getDirection())) {
            if (building.getElevatorContainer().getCurrentStory() < building.getElevatorContainer().getStoryNumbers()) {
                building.getElevatorContainer().up();
            } else {
                building.getElevatorContainer().setDirection(Direction.DOWN);
            }
        } else {
            if (building.getElevatorContainer().getCurrentStory() > 1) {
                building.getElevatorContainer().down();
            } else {
                building.getElevatorContainer().setDirection(Direction.UP);
            }
        }
    }

    private boolean correctDirection(final Passenger pas) {
        return (Direction.UP.equals(building.getElevatorContainer().getDirection()) && pas.getInitialStory() < pas.getDestinationStory())
                || (Direction.DOWN.equals(building.getElevatorContainer().getDirection()) && pas.getInitialStory() > pas.getDestinationStory());
    }


    public synchronized boolean iCanEnter(final Passenger passenger) {
        boolean canEnter = false;
        if (building.getElevatorContainer().letMeEnter() && correctDirection(passenger) && building.getElevatorContainer().getCurrentStory() == passenger.getInitialStory()) {
            canEnter = true;
            building.getDispatchStory(building.getElevatorContainer().getCurrentStory() - 1).removePassenger(passenger);
            building.getElevatorContainer().enterElevator(passenger);
            building.getEnterLatch().countDown();
        }
        return canEnter;
    }


    public synchronized boolean iCanLeave(final Passenger passenger) {
        boolean canLeave = false;
        if (passenger.getDestinationStory() == building.getElevatorContainer().getCurrentStory()) {
            canLeave = true;
            building.getElevatorContainer().leaveElevator(passenger);
            building.getArrivalStory(building.getElevatorContainer().getCurrentStory() - 1).addPassenger(passenger);
            building.getLeaveLatch().countDown();
        }
        return canLeave;
    }


    private int countCanLeave() {
        int canLeave = 0;
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.addAll(building.getElevatorContainer().getElevatorPassengers());
        for (Passenger pas : passengerList) {
            if (pas.getDestinationStory() == building.getElevatorContainer().getCurrentStory()) {
                canLeave++;
            }
        }
        return canLeave;
    }


    private int countCanEnter() {
        int canEnterInElevator = 0;
        int elevatorFreePlaces = building.getElevatorContainer().getStorage() - building.getElevatorContainer().getElevatorPassengers().size();
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.addAll(building.getDispatchStory(building.getElevatorContainer().getCurrentStory() - 1).getStoryPassengers());
        for (Passenger pas : passengerList) {
            if (pas.getInitialStory() == building.getElevatorContainer().getCurrentStory() && correctDirection(pas)) {
                canEnterInElevator++;
            }
        }
        if (canEnterInElevator < elevatorFreePlaces) {
            return canEnterInElevator;
        } else {
            return elevatorFreePlaces;
        }
    }


    private boolean isFinished() {
        boolean isFinished = false;
        int arrivedNumber = 0;
        int dispatchNumber = 0;
        int elevatorNumber = building.getElevatorContainer().getElevatorPassengers().size();
        int totalNumber = building.getGenerator().getPassengersNumber();
        for (int i = 0; i < building.getGenerator().getStoriesNumber(); i++) {
            dispatchNumber += building.getDispatchStory(i).size();
        }
        for (int i = 0; i < building.getGenerator().getStoriesNumber(); i++) {
            for (int j = 0; j < building.getArrivalStory(i).size(); j++) {
                Passenger pas = building.getArrivalStory(i).getStoryPassengers().get(j);
                if (pas.getDestinationStory() == i + 1 && CartingState.COMPLETED.equals(pas.getState())) {
                    arrivedNumber++;
                }
            }
        }

        if (arrivedNumber == totalNumber && dispatchNumber == 0 && elevatorNumber == 0) {
            isFinished = true;
            log.info("TRANSPORTATION COMPLETE");
        }
        return isFinished;
    }


    @Override
    public void run() {
        try {
            while (!isFinished()) {
                building.setLeaveLatch(countCanLeave());
                building.getElevatorContainer().notifyElevatorPassengers();
                building.getLeaveLatch().await();

                building.setEnterLatch(countCanEnter());
                building.getDispatchStory(building.getElevatorContainer().getCurrentStory() - 1).notifyStoryPassengers();
                building.getEnterLatch().await();

                moveElevator();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread has been interrupted..." + e);
        }
    }

}

