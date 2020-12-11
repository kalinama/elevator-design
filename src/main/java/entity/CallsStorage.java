package entity;

import java.util.List;


public class CallsStorage {
    private final List<Story> dispatches;
    private final List<Story> arrivals;


    public CallsStorage(final List<Story> dispatches, final List<Story> arrivals) {
        this.dispatches = dispatches;
        this.arrivals = arrivals;
    }

    public List<Story> getDispatches() {
        return dispatches;
    }

    public List<Story> getArrivals() {
        return arrivals;
    }

}
