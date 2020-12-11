package core;

import entity.Building;
import entity.Passenger;
import entity.enums.CartingState;

public class CartingTask implements Runnable {
    private final Passenger passenger;
    private final Building building;
    private final Controller controller;

    public CartingTask(final Passenger passenger, final Building building, final Controller controller) {
        this.passenger = passenger;
        this.building = building;
        this.controller = controller;
    }

    @Override
    public void run() {
        do {
            building.getDispatchStory(passenger.getInitialStory() - 1).waitOnStory();
        } while (!controller.iCanEnter(passenger));
        passenger.setState(CartingState.IN_PROGRESS);

        do {
            building.getElevatorContainer().waitInElevator();
        } while (!controller.iCanLeave(passenger));
        passenger.setState(CartingState.COMPLETED);
    }

}


