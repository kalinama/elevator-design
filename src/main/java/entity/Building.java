package entity;

import core.Generator;

import java.util.List;
import java.util.concurrent.CountDownLatch;


public class Building {
    private final List<Passenger> passengerList;
    private final Elevator elevator;
    private final CallsStorage callsStorage;
    private final Generator generator;
    private CountDownLatch leaveLatch;
    private CountDownLatch enterLatch;

    public Building(final Generator gnr){
        generator = gnr;
        passengerList = generator.generatePassengers();
        elevator = new Elevator(generator.getElevatorCapacity(), generator.getStoriesNumber());
        callsStorage = new CallsStorage(generator.generateDispatchContainers(passengerList),
                generator.generateArrivalContainers());
    }


    public Generator getGenerator() {
        return generator;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public Elevator getElevatorContainer() {
        return elevator;
    }

    public void setLeaveLatch(final int count) {
        leaveLatch = new CountDownLatch(count);
    }

    public CountDownLatch getLeaveLatch() {
        return leaveLatch;
    }

    public void setEnterLatch(final int count) {
        this.enterLatch = new CountDownLatch(count);
    }

    public CountDownLatch getEnterLatch() {
        return enterLatch;
    }


    public Story getDispatchStory(final int story) {
        return callsStorage.getDispatches().get(story);
    }

    public Story getArrivalStory(final int story) {
        return callsStorage.getArrivals().get(story);
    }
}
