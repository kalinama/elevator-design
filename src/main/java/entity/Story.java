package entity;

import java.util.ArrayList;
import java.util.List;

public class Story {
    private final List<Passenger> story;

    public Story() {
        List<Passenger> list = new ArrayList<>();
        this.story = list;
    }

    public Story(final List<Passenger> passengerList) {
        this.story = passengerList;
    }


    public List<Passenger> getStoryPassengers() {
        return story;
    }


    public void addPassenger(final Passenger pas) {
        story.add(pas);
    }


    public void removePassenger(final Passenger pas) {
        story.remove(pas);
    }

    public int size() {
        return story.size();
    }


    public synchronized void waitOnStory() {
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException("Thread has been interrupted..." + e);
        }
    }

    public synchronized void notifyStoryPassengers() {
        notifyAll();
    }

}
