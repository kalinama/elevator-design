package entity;

import entity.enums.CartingState;

public class Passenger {
    private final int passengerID;
    private CartingState state;
    private final int initialStory;
    private final int destinationStory;

    public Passenger(final Integer passengerID, final CartingState state, final Integer initialStory, final Integer destinationStory) {
        if (passengerID < 1) {
            throw new IllegalArgumentException("Wrong passenger ID");
        }
        if (initialStory < 1) {
            throw new IllegalArgumentException("Wrong passenger initialStory");
        }
        if (destinationStory < 1) {
            throw new IllegalArgumentException("Wrong passenger destinationStory");
        }
        this.passengerID = passengerID;
        this.state = state;
        this.initialStory = initialStory;
        this.destinationStory = destinationStory;
    }

    public int getInitialStory() {
        return initialStory;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public CartingState getState() {
        return state;
    }

    public void setState(final CartingState state) {
        this.state = state;
    }

    public int getDestinationStory() {
        return destinationStory;
    }

    @Override
    public String toString() {
        return "ID = " + passengerID + ". Initial = " + initialStory + ". Destination = "
                + destinationStory + ". State = " + state + ";";
    }

}
